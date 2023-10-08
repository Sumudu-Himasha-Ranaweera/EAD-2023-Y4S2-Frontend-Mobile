package com.example.ticketnow.UserProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ticketnow.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewFirstName;
    private TextView textViewUserEmail;
    private TextView textViewPW_Change;
    private TextView textViewNIC;
    private TextView viewFirstName;
    private TextView viewLastName;
    private TextView viewNumber;
    private TextView viewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Retrieve extras from the intent
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String userDetails = intent.getStringExtra("userDetails");

        // Initialize TextViews
        textViewFirstName = findViewById(R.id.textViewFirstName);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewNIC = findViewById(R.id.textViewNIC);
        viewFirstName = findViewById(R.id.ViewFirstName);
        viewLastName = findViewById(R.id.ViewLastName);
        viewNumber = findViewById(R.id.ViewNumber);
        viewEmail = findViewById(R.id.ViewEmail);

        try {
            JSONObject userDetailsJson = new JSONObject(userDetails);
            // Extract user details from the JSON
            String firstName = userDetailsJson.getString("firstName");
            String lastName = userDetailsJson.getString("lastName");
            String nic = userDetailsJson.getString("nic");
            String contactNumber = userDetailsJson.getString("contactNumber");
            String email = userDetailsJson.getString("email");

            // Update TextViews with user details
            textViewFirstName.setText("㋡ Welcome " + firstName +"!");
            textViewUserEmail.setText(email);
            textViewNIC.setText(nic);
            viewFirstName.setText(firstName);
            viewLastName.setText(lastName);
            viewNumber.setText(contactNumber);
            viewEmail.setText(email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}