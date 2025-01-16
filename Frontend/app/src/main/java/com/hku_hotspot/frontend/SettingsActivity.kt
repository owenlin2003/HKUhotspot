package com.hku_hotspot.frontend

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchTheme: Switch
    private lateinit var mainLayout: RelativeLayout
    private lateinit var themeText: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var logOutButton: Button
    private lateinit var userSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize Views
        switchTheme = findViewById(R.id.switch_theme)
        mainLayout = findViewById(R.id.mainLayout)
        themeText = findViewById(R.id.themeText)
        usernameTextView = findViewById(R.id.userNameTextView)
        logOutButton = findViewById(R.id.button_log_out)

        // SharedPreferences
        val settingsSharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
        userSharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Load preferences
        val isDarkMode = settingsSharedPreferences.getBoolean("dark_mode", false)
        switchTheme.isChecked = isDarkMode
        applyTheme(isDarkMode)

        // Display username
        val username = userSharedPreferences.getString("username", "error")
        usernameTextView.text = username

        // Theme switch listener
        if (switchTheme.hasOnClickListeners().not()) {
            switchTheme.setOnCheckedChangeListener { _, isChecked ->
                settingsSharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
                applyTheme(isChecked)
            }
        }

        // Map button listener
        findViewById<ImageButton>(R.id.map_button).setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        // Profile button listener
        findViewById<ImageButton>(R.id.profile_button).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Logout button listener
        logOutButton.setOnClickListener {
            userSharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }

    private fun applyTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        mainLayout.setBackgroundColor(
            ContextCompat.getColor(this, if (isDarkMode) R.color.black else R.color.white)
        )
        themeText.setTextColor(
            ContextCompat.getColor(this, if (isDarkMode) R.color.white else R.color.black)
        )
        Log.d(String.toString(), "darkmode is " + isDarkMode.toString())
    }

    fun goToChangePassword(view: View) {
        startActivity(Intent(this, PasswordActivity::class.java))
    }

    fun goToChangeUsername(view: View) {
        startActivity(Intent(this, UsernameActivity::class.java))
    }
}
