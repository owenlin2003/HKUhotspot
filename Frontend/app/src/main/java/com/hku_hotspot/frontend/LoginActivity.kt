package com.hku_hotspot.frontend

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import com.beust.klaxon.Klaxon

class LoginActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private lateinit var sharedPreferences: SharedPreferences

    class User (val email: String, val userName : String, val firstName : String,
                val lastName : String, val password : String, val positionX : Int, val positionY : Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Link UI elements
        val usernameEditText = findViewById<EditText>(R.id.input_username)
        val passwordEditText = findViewById<EditText>(R.id.input_password)
        val loginButton = findViewById<Button>(R.id.loginButton)

        // Shared Preferences Logic
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Set login button click action
        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Simple check to see if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter an email and/or a password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                sendLoginRequest(email, password)
            }
        }
    }

    private fun sendLoginRequest(email: String, password: String) {

        Log.v("LoginActivity", "hello")

        // val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder().url("http://52.231.109.42/api/Account/get_user/${email}/${password}").get().build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Request failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.v("MainActivity", "Request Failed")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBodyJson : String = response.body!!.string()
                    val responseBody = Klaxon().parse<User>(responseBodyJson)
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                        Log.v("MainActivity", "Registration Successful")
                        saveUserLogin(responseBody!!.email, responseBody.userName)
                        val intent =
                            Intent(this@LoginActivity, MapsActivity::class.java) // Update to next activity if needed
                        startActivity(intent)
                        finish()
                    }
                } else {
                    runOnUiThread {
                        val errorBody = response.body?.string() // Get the error body
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Failed to login: ${response.code}", Toast.LENGTH_SHORT).show()
                            Log.v("MainActivity",  "Failed to Login: ${response.code}, Error: $errorBody")
                        }
                    }
                }
            }
        })
    }


    private fun saveUserLogin(email: String, userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("username", userName)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }
}