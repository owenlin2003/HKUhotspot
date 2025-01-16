package com.hku_hotspot.frontend

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var unifiedAdapter: UnifiedAdapter
    private val locationPermissionRequestCode = 1001
    private val handler = Handler(Looper.getMainLooper())
    private val refreshInterval: Long = 30000 // 30 sec
    private var botsVisible = false // Track bot visibility

    private lateinit var profileButton: ImageButton


    private val refreshRunnable = object : Runnable {
        override fun run() {
            refreshBotPositions() // Refresh bot positions and update statuses
            updateUnifiedList() // Update unified list items
            handler.postDelayed(this, refreshInterval) // Schedule next refresh
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Set up RecyclerView for the unified list (buildings and queues)
        val unifiedList = findViewById<RecyclerView>(R.id.unified_list)
        unifiedList.layoutManager = LinearLayoutManager(this)
        unifiedAdapter = UnifiedAdapter(getUnifiedItems()) { latLng ->
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        }
        unifiedList.adapter = unifiedAdapter
        unifiedList.visibility = View.GONE

        // Toggle the unified list visibility on list button click
        findViewById<ImageButton>(R.id.menu_button).setOnClickListener {
            unifiedList.visibility = if (unifiedList.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        // Set up toggle bots button
        findViewById<ImageButton>(R.id.toggle_bots_button).setOnClickListener {
            botsVisible = !botsVisible // Toggle bot visibility
            refreshBotPositions() // Refresh to show/hide bots
        }

        //Set up the restaurant button
        findViewById<ImageButton>(R.id.restaurant_button).setOnClickListener {
            val intent = Intent(this, RestaurantListActivity::class.java)
            startActivity(intent)
        }


        // Set up the map fragment and request the map asynchronously
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize FusedLocationProviderClient for accessing the user’s location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener {
            try{
                val intent = Intent(this,SettingsActivity::class.java)
                startActivity(intent)
            }
            catch (e: Exception){
                println(e)
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        Log.d("MapTest", "Google Map is ready")

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionRequestCode)
        }

        // Dark mode setup
        val isDarkMode = getSharedPreferences("SettingsPrefs", MODE_PRIVATE).getBoolean("dark_mode", false)
        if (isDarkMode) {
            try {
                val success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
                if (!success) {
                    Log.e("MapsActivity", "Style parsing failed.")
                }
            } catch (e: Resources.NotFoundException) {
                Log.e("MapsActivity", "Can't find style. Error: ", e)
            }
        } else {
            map.setMapStyle(null)
        }

        handler.post(refreshRunnable)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.2830, 114.1370), 15f))
        map.isBuildingsEnabled = false

        drawBuildingsOnMap()
        drawQueuesOnMap()
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun drawBuildingsOnMap() {
        map.clear()

        val emptyColor = Color.argb(100, 0, 255, 0)
        val occupiedColor = Color.argb(150, 255, 255, 0)
        val fullColor = Color.argb(200, 255, 0, 0)

        buildingsList.forEach { building ->
            val color = when (building.status) {
                "Empty" -> emptyColor
                "Occupied" -> occupiedColor
                "Full" -> fullColor
                else -> Color.GRAY
            }

            val polygonOptions = PolygonOptions()
                .addAll(building.polygon)
                .strokeColor(Color.BLACK)
                .fillColor(color)
                .zIndex(2.0f)
                .clickable(true)

            val polygon = map.addPolygon(polygonOptions)
            polygon.tag = building.id
        }

        map.setOnPolygonClickListener{ polygon ->


            val intent = Intent(this, FilterActivity::class.java)
            val buildingClicked = buildingsList.find { it.id == polygon.tag };
            val currOccupancy = (buildingClicked!!.botCount.toFloat() / buildingClicked.capacity.toFloat()) * 400
            Log.d("BuildingClicked", "ID: ${buildingClicked.id}")
            Log.d("BuildingClicked", "Name: ${buildingClicked.name}")
            Log.d("BuildingClicked", "Description: ${buildingClicked.description}")
            Log.d("BuildingClicked", "Occupancy: $currOccupancy")
            intent.putExtra("Id", buildingClicked.id)
            intent.putExtra("BuildingName", buildingClicked.name)
            intent.putExtra("OccupancyLevel", currOccupancy)
            intent.putExtra("Description", buildingClicked.description)
            startActivity(intent)
        }

        unifiedAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun drawQueuesOnMap() {
        val emptyColor = Color.argb(100, 0, 255, 0) // Light green for empty
        val occupiedColor = Color.argb(150, 255, 255, 0) // Yellow for occupied
        val fullColor = Color.argb(200, 255, 0, 0) // Red for full

        queuesList.forEach { queue ->
            val color = when {
                queue.botCount < queue.capacity / 3 -> emptyColor
                queue.botCount < 2 * queue.capacity / 3 -> occupiedColor
                else -> fullColor
            }

            val polylineOptions = PolylineOptions()
                .addAll(queue.points)
                .color(color)
                .width(20f)
                .zIndex(1.9f)
                .clickable(true)

            val polyline = map.addPolyline(polylineOptions)
            polyline.tag = queue.id
        }

        map.setOnPolylineClickListener { polyline ->
            val queueClicked = queuesList.find { it.id == polyline.tag }
            if (queueClicked != null) {
                val currOccupancy =
                    (queueClicked.botCount.toFloat() / queueClicked.capacity.toFloat()) * 400

                // Log queue details
                Log.d("QueueClicked", "ID: ${queueClicked.id}")
                Log.d("QueueClicked", "Name: ${queueClicked.name}")
                Log.d("QueueClicked", "Description: ${queueClicked.description}")
                Log.d("QueueClicked", "Occupancy: $currOccupancy")

                // Pass queue details to the FilterActivity
                val intent = Intent(this, FilterActivity::class.java)
                intent.putExtra("QueueName", queueClicked.name)
                intent.putExtra("OccupancyLevel", currOccupancy)
                intent.putExtra("Description", queueClicked.description)
                startActivity(intent)
            }
        }

        unifiedAdapter.notifyDataSetChanged()
    }


    private fun enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    Log.d("Location", "User location: $userLatLng")
                } ?: Log.d("Location", "Last known location is null")
            }
        } else {
            Log.w("Location", "Location permission not granted")
        }
    }

    private fun addBotMarkers() {
        if (!botsVisible) return // Skip adding bot markers if bots are hidden

        buildingsList.forEach { building ->
            building.botPositions.forEach { botPosition ->
                map.addMarker(
                    MarkerOptions()
                        .position(botPosition)
                        .title("Bot")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .anchor(0.3f, 0.3f) // Smaller icon size
                )
            }
        }

        queuesList.forEach { queue ->
            queue.botPositions.forEach { botPosition ->
                map.addMarker(
                    MarkerOptions()
                        .position(botPosition)
                        .title("Bot in Queue")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .anchor(0.3f, 0.3f) // Smaller icon size
                )
            }
        }
    }

    private fun refreshBotPositions() {
        updateBuildingsWithBots(buildingsList)
        updateQueuesWithBots(queuesList)

        map.clear()
        drawBuildingsOnMap()
        drawQueuesOnMap()
        addBotMarkers() // Show or hide bots based on the `botsVisible` flag
    }

    // Function to retrieve unified items (buildings and queues) for the unified adapter
    private fun getUnifiedItems(): List<UnifiedItem> {
        val unifiedItems = mutableListOf<UnifiedItem>()

        unifiedItems.addAll(
            buildingsList.map {
                UnifiedItem(
                    name = it.name,
                    status = getStatusDescription(it.getOccupancyLevel()), // Only words
                    position = it.centerPosition
                )
            }
        )

        unifiedItems.addAll(
            queuesList.map {
                UnifiedItem(
                    name = it.name,
                    status = getStatusDescription(it.getOccupancyLevel()), // Only words
                    position = it.points.first() // Use the first point as a reference
                )
            }
        )

        return unifiedItems
    }

    private fun getStatusDescription(occupancyLevel: Int): String {
        return when (occupancyLevel) {
            1 -> "Empty"
            2 -> "Low"
            3 -> "Moderate"
            4 -> "High"
            5 -> "Full"
            else -> "Unknown"
        }
    }



    // Function to update the unified list
    private fun updateUnifiedList() {
        val updatedItems = getUnifiedItems()
        unifiedAdapter.updateItems(updatedItems)
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(refreshRunnable)
    }
}