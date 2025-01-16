package com.hku_hotspot.frontend;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SubmitOccupancyActivity extends AppCompatActivity
{
    private SeekBar currentSeekBar;
    private SeekBar reportSeekBar;
    Button submitOccu;

    EditText inputFeed;
    Button submitFeed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        reportSeekBar = findViewById(R.id.report_occu_bar);
        submitOccu = findViewById(R.id.submit_but);

        // Report Occupancy Activity
        reportSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    // can display the rating no. later if need
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // submit the occupancy level
        submitOccu.setOnClickListener(view -> {
            // current level
            int reportValue = reportSeekBar.getProgress();
            // Toast message for now
            Toast.makeText(SubmitOccupancyActivity.this, "Occupancy level: " + reportValue, Toast.LENGTH_SHORT).show();

            // !!!!! [PENDING] connect to backend --> database
        });
    }
}

