package com.hku_hotspot.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    private lateinit var welcomeText: TextView

    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        Log.v("MainActivity", "This is a verbose log message")

        // Link UI elements
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val newPasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val firstNameEditText = findViewById<EditText>(R.id.firstNameEditText)
        val lastNameEditText = findViewById<EditText>(R.id.lastNameEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginButton = findViewById<Button>(R.id.loginButton)


        // Set register button click action
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val username = usernameEditText.text.toString()
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            // Simple check to see if fields are empty
            if (email.isEmpty() || newPassword.isEmpty() || username.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Please enter all the information asked", Toast.LENGTH_SHORT).show()
            } else {
                // Placeholder for registration logic
                sendRegistrationRequest(email, newPassword, username, firstName, lastName)
                // Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                // Redirect to LoginActivity after registration
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Closes RegisterActivity so it’s not in the back stack
            }
        }
        loginButton.setOnClickListener {
            // Naviguer vers LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optionnel : Ferme RegisterActivity après la navigation
        }
    }

    private fun sendRegistrationRequest(email: String, password: String, userName : String, firstName : String, lastName: String) {

        val json =  "{" +
                    "\"email\":\"$email\"," +
                    "\"userName\":\"$userName\"," +
                    "\"firstName\":\"$firstName\"," +
                    "\"lastName\":\"$lastName\"," +
                    "\"password\":\"$password\"," +
                    "\"positionX\":0," +
                    "\"positionY\":0" +
                    "}"

        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        // Create Request
        val request = Request.Builder().url("http://52.231.109.42/api/Account/add_user").post(requestBody).build()



        // Execute the Request
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Request failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.v("MainActivity", "Request Failed")
                }
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                        Log.v("MainActivity", "Registration Successful")
                    }
                } else {
                    runOnUiThread {
                        val errorBody = response.body?.string() // Get the error body
                        runOnUiThread {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Failed to register: ${response.code}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.v("MainActivity",  "Failed to Register: ${response.code}, Error: $errorBody")
                        }
                    }
                }
            }
        })
    }
}