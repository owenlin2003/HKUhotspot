package com.hku_hotspot.frontend

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class UsernameActivity : AppCompatActivity() {

    private lateinit var inputNewUsername: EditText
    private lateinit var buttonUpdate: Button
    private lateinit var userSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_username)

        inputNewUsername = findViewById(R.id.input_new_username)
        buttonUpdate = findViewById(R.id.button_update)
        userSharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        buttonUpdate.setOnClickListener {
            val newUsername = inputNewUsername.text.toString()

            if (validateUsername(newUsername)) {
                CoroutineScope(Dispatchers.IO).launch {
                    changeUsername(newUsername)
                }
            } else {
                Toast.makeText(this, "Invalid Username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun changeUsername(newUsername: String) {
        val email = userSharedPreferences.getString("email", "error")


        val client = OkHttpClient.Builder().build()
        val mediaType = "application/json".toMediaTypeOrNull()!!
        val body = "".toRequestBody(mediaType) // Adjust body content as needed
        val request = Request.Builder()
            .url("http://52.231.109.42:80/api/Account/change_username/$email/$newUsername")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            // Perform network call on Dispatchers.IO
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }
            val responseBody = response.body?.string() ?: "No response body"

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UsernameActivity, "Username changed sucessfully", Toast.LENGTH_LONG).show()
                    val editor = userSharedPreferences.edit()
                    editor.putString("username", newUsername)
                    editor.apply()
                }
            } else {
                // Handle unsuccessful response
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UsernameActivity, "Failed: ${response.code}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
            withContext(Dispatchers.Main) {
                Toast.makeText(this@UsernameActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateUsername(newUsername: String): Boolean {
        return newUsername.isNotBlank()
    }
}
