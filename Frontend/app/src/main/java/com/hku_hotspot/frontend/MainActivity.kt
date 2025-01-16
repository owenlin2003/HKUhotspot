package com.hku_hotspot.frontend

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var settingButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        settingButton = findViewById(R.id.settingsButton);
        settingButton!!.setOnClickListener {
            try{
                val intent = Intent(this,SettingsActivity::class.java)
                startActivity(intent)
            }
            catch (e: Exception){
                println(e)
            }
        }

    }
}