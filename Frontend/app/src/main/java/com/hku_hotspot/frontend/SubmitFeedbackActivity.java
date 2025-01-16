package com.hku_hotspot.frontend;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SubmitFeedbackActivity extends AppCompatActivity {

    private EditText inputFeed;
    private Button submitFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        inputFeed = findViewById(R.id.input_feed);
        submitFeed = findViewById(R.id.enter_but);

        // FeedBack Activity
        submitFeed.setOnClickListener(view -> {
            String feedback = inputFeed.getText().toString();
            if (!feedback.isEmpty()) {
                sendMessage(feedback);
                Toast.makeText(SubmitFeedbackActivity.this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
                inputFeed.setText("");
            } else {
                Toast.makeText(SubmitFeedbackActivity.this, "Please enter feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Send to the DB
    /*
        NEED TO EDIT LATER
     */
    private void sendMessage(String feedback) {
//        String url = "http://192.168.1.198:5000/tutorial?feedback=" + feedback;
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        switchActivity(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("SubmitFeedbackActivity", error.toString());
//                    }
//                }
//        );
//
//        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void switchActivity(JSONObject response) {
        // Implement the switch activity logic here
        // For example, you could start a new activity or process the response
    }
}
