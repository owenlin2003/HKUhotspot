package com.hku_hotspot.frontend

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class RestaurantListActivity : AppCompatActivity() {

    private lateinit var restaurantRecyclerView: RecyclerView
    private lateinit var profileButton: ImageButton
    private lateinit var mapButton: ImageButton
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)

        // Set up buttons
        profileButton = findViewById(R.id.profile_button)
        mapButton = findViewById(R.id.map_button)

        // Profile button listener
        profileButton.setOnClickListener {
            try {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Map button listener
        mapButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        // Initialize RecyclerView
        restaurantRecyclerView = findViewById(R.id.restaurant_list)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter and set it to the RecyclerView For a potential description
        restaurantAdapter = RestaurantAdapter(restaurantList) { restaurant ->
            // Handle item clicks

            Toast.makeText(
                this,
                "Opening ${restaurant.name}",
                Toast.LENGTH_SHORT
            ).show()

            // Open the restaurant's link in a browser
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(restaurant.link)
            }
            startActivity(intent)

        }
        restaurantRecyclerView.adapter = restaurantAdapter

        simulateDataRefresh()

    }

    private fun simulateDataRefresh() {
        // Create a dynamically updated list (for example, update `botCount`)
        val updatedRestaurantList = restaurantList.map { restaurant ->
            restaurant.copy(botCount = updatedBotCount(restaurant.id)) // Update dynamically
        }
    }

    private fun updatedBotCount(restaurantId: Int): Int {
        // Simulate bot count update logic
        return when (restaurantId) {
            15 -> (10..50).random() // Random bot count for example
            else -> 0
        }
    }

    // Adapter class for restaurant items
    private inner class RestaurantAdapter(
        private var restaurants: List<Restaurant>,
        private val onItemClick: (Restaurant) -> Unit
    ) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

        // ViewHolder class for restaurant items
        inner class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val restaurantImage: ImageView = view.findViewById(R.id.restaurant_image)
            private val restaurantName: TextView = view.findViewById(R.id.restaurant_name)
            private val restaurantDescription: TextView = view.findViewById(R.id.restaurant_description)

            fun bind(restaurant: Restaurant) {
                restaurantName.text = restaurant.name
                restaurantDescription.text = restaurant.description

                // Load the image using the resource ID
                restaurantImage.setImageResource(restaurant.imageResId)

                // Handle click listener
                itemView.setOnClickListener { onItemClick(restaurant) }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurant_item, parent, false)
            return RestaurantViewHolder(view)
        }

        override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
            holder.bind(restaurants[position])
        }


        override fun getItemCount(): Int = restaurants.size

    }
}
