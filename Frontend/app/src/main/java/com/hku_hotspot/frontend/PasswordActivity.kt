package com.hku_hotspot.frontend

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PasswordActivity : AppCompatActivity() {

    private lateinit var currentPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var userSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        currentPasswordEditText = findViewById(R.id.input_current_password)
        newPasswordEditText = findViewById(R.id.input_new_password)
        updateButton = findViewById(R.id.button_update)
        userSharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        updateButton.setOnClickListener {
            val currentPassword = currentPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()

            if (validatePassword(currentPassword, newPassword)) {
                CoroutineScope(Dispatchers.IO).launch {
                    changePassword(newPassword)
                }            } else {
                Toast.makeText(this@PasswordActivity, "Invalid Input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun changePassword(newPassword: String) {
        val email = userSharedPreferences.getString("email", "error")


        val client = OkHttpClient.Builder().build()
        val mediaType = "application/json".toMediaTypeOrNull()!!
        val body = "".toRequestBody(mediaType) // Adjust body content as needed
        val request = Request.Builder()
            .url("http://52.231.109.42:80/api/Account/change_password/$email/$newPassword")
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
                    Toast.makeText(this@PasswordActivity, "Password changed sucessfully", Toast.LENGTH_LONG).show()
                }
            } else {
                // Handle unsuccessful response
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PasswordActivity, "Failed: ${response.code}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
            withContext(Dispatchers.Main) {
                Toast.makeText(this@PasswordActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validatePassword(currentPassword: String, newPassword: String): Boolean {
        return when {
            currentPassword.isEmpty() || newPassword.isEmpty() -> false
            currentPassword == newPassword -> false
            else -> true
        }
    }
}
