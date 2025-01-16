package com.hku_hotspot.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.hku_hotspot.frontend.LoginActivity.User
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody.Companion.toRequestBody

class FilterActivity : AppCompatActivity() {
    private lateinit var mapButton: ImageButton
    private lateinit var profileButton: ImageButton

    private var buildingId : Int = 0

    private val client = OkHttpClient()

    class Comment (val id : Int, val stationID : Int, val commentAuthor : String,
                   val commentDescription : String)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        //
        // A1 -- Info Description for Music Library
        //
        val descriptionTextView = findViewById<TextView>(R.id.txt_description)
        descriptionTextView.text = intent.getStringExtra("Description")

        val titleTextView = findViewById<TextView>(R.id.HKU_station)
        // Retrieve the names from the intent
        val buildingName = intent.getStringExtra("BuildingName")
        val queueName = intent.getStringExtra("QueueName")

        // Set the title based on which name is present
        titleTextView.text = buildingName ?: queueName ?: "Unknown Location"
        //
        // A2 -- Comments for Music Library
        //

        val recyclerView = findViewById<RecyclerView>(R.id.list_comments)
        buildingId = intent.getIntExtra("Id", 0)


        // set up the comemnts
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            GetComments(buildingId)
        }
        //
        // A3 - Current occupancy
        //
        val currentOccuBar = findViewById<SeekBar>(R.id.current_occu_bar)
        val currentOccupancyLevelFloat = intent.getFloatExtra("OccupancyLevel", 0.0f)
        val currentOccupancyLevel = currentOccupancyLevelFloat.toInt()
        // Set initial progress
        currentOccuBar.max = 400 // 1.0 to 5.0 scaled by 100
        CoroutineScope(Dispatchers.IO).launch {
            GetCurrentOccupancy(buildingId)
        }

        currentOccuBar.isClickable = false;
        currentOccuBar.isFocusable = false;
        currentOccuBar.setEnabled(false);

        //
        // A4 -- Report Occupancy SeekBar
        //
        val reportOccuBar = findViewById<SeekBar>(R.id.report_occu_bar)
        val submitButton = findViewById<Button>(R.id.submit_but)

        var reportValue = 0 // default value

        // Listener for SeekBar changes
        reportOccuBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                reportValue = progress // Update the report value as the user drags the SeekBar
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Optional: Handle when the user starts touching the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Optional: Handle when the user stops dragging the SeekBar
            }
        })

        // submit it to the Backend
        submitButton.setOnClickListener {
            // Submit the selected occupancy value
            val actualVote = reportOccuBar.progress + 1
            CoroutineScope(Dispatchers.IO).launch {
                SubmitVote(buildingId, actualVote)
            }

            reportOccuBar.progress = 0
        }

        //
        // A5 - Submit Feedback
        //
        val feedbackEditText = findViewById<EditText>(R.id.input_feed)
        val enterButton = findViewById<Button>(R.id.enter_but)

        enterButton.setOnClickListener {
            val feedback = feedbackEditText.text.toString().trim()

            // Check if the feedback exceeds the character limit
            if (feedback.length > 100) {
                Toast.makeText(this, "Please limit under 100 characters", Toast.LENGTH_SHORT).show()
            } else if (feedback.isEmpty()) {
                Toast.makeText(this, "Please enter some feedback", Toast.LENGTH_SHORT).show()
            } else {
                //sendFeedbackToBackend(feedback)
                // id buildingId
                CoroutineScope(Dispatchers.IO).launch {
                    postComment(buildingId, feedback)
                }
            }
        }

        //mapbutton cliquable for map
        mapButton = findViewById(R.id.map_button);
        mapButton.setOnClickListener {
            try{
                val intent = Intent(this,MapsActivity::class.java)
                startActivity(intent)
            }
            catch (e: Exception){
                println(e)
            }
        }

        //profileButton cliquable for settings
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

    private suspend fun postComment(id : Int, comment : String) {
        val userInfoSharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = userInfoSharedPreferences.getString("username", "error")
        val client = OkHttpClient.Builder().build()
        val mediaType = "application/json".toMediaTypeOrNull()!!
        val body = "".toRequestBody(mediaType) // Adjust body content as needed
        val request = Request.Builder()
            .url("http://52.231.109.42:80/api/Station/post_comment/${id}/${username}/${comment}")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            // Execute the request on a background thread
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: "No response body"

            // Switch to the main thread to show a toast message
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FilterActivity, "Comment posted successfully", Toast.LENGTH_LONG).show()
                    val feedbackEditText = findViewById<EditText>(R.id.input_feed)
                    feedbackEditText.setText("") // clear the field after submission
                } else {
                    Toast.makeText(this@FilterActivity, "Failed: ${response.code}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            // Handle any exceptions and show an error message
            withContext(Dispatchers.Main) {
                Toast.makeText(this@FilterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun GetComments(id: Int) {
        val client = OkHttpClient.Builder().build()
        val mediaType = "application/json".toMediaTypeOrNull()!!
        val body = "".toRequestBody(mediaType) // Adjust body content as needed
        val request = Request.Builder()
            .url("http://52.231.109.42:80/api/Station/get_all_comments/$id")
            .get() // Ensure the method is "GET" and not "Get" (case-sensitive)
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            // Perform network call on Dispatchers.IO
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }
            val responseBody = response.body?.string() ?: "No response body"

            if (response.isSuccessful) {
                val responseBodyParsed = Klaxon().parseArray<Comment>(responseBody)
                val commentDescriptions = responseBodyParsed?.map { it.commentDescription }

                // Switch to main thread to update UI
                withContext(Dispatchers.Main) {
                    val recyclerView = findViewById<RecyclerView>(R.id.list_comments)
                    recyclerView.adapter = commentDescriptions?.let { FeedbacksAdapter(it) }
                }
            } else {
                // Handle unsuccessful response
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FilterActivity, "Failed: ${response.code}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
            withContext(Dispatchers.Main) {
                Toast.makeText(this@FilterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun SubmitVote(id : Int, vote : Int){
        val userInfoSharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = userInfoSharedPreferences.getString("username", "error")
        val client = OkHttpClient.Builder().build()
        val mediaType = "application/json".toMediaTypeOrNull()!!
        val body = "".toRequestBody(mediaType) // Adjust body content as needed
        val request = Request.Builder()
            .url("http://52.231.109.42:80/api/Station/submit_vote/${id}/${vote}")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            // Execute the request on a background thread
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: "No response body"

            // Switch to the main thread to show a toast message
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FilterActivity, "Vote submitted successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@FilterActivity, "Failed: ${response.code}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            // Handle any exceptions and show an error message
            withContext(Dispatchers.Main) {
                Toast.makeText(this@FilterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun GetCurrentOccupancy(id : Int) {
        val client = OkHttpClient.Builder().build()
        val mediaType = "application/json".toMediaTypeOrNull()!!
        val body = "".toRequestBody(mediaType) // Adjust body content as needed
        val request = Request.Builder()
            .url("http://52.231.109.42:80/api/Station/get_station_occupancy/$id")
            .get() // Ensure the method is "GET" and not "Get" (case-sensitive)
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            // Perform network call on Dispatchers.IO
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }
            val responseBody = response.body?.string() ?: "No response body"

            if (response.isSuccessful) {
                val responseBodyParsed = responseBody.toInt()
                // Switch to main thread to update UI
                withContext(Dispatchers.Main) {
                    val currentOccuBar = findViewById<SeekBar>(R.id.current_occu_bar)
                    if (responseBodyParsed != null) {
                        currentOccuBar.progress = responseBodyParsed * 80
                    }


                }
            } else {
                // Handle unsuccessful response
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FilterActivity, "Failed: ${response.code}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
            withContext(Dispatchers.Main) {
                Toast.makeText(this@FilterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}