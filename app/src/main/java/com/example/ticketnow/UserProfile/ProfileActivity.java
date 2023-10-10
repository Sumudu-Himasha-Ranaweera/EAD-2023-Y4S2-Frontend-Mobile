package com.example.ticketnow.UserProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ticketnow.R;
import com.example.ticketnow.Reservation.ReservationHistoryActivity;
import com.example.ticketnow.TestActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewFirstName, textViewUserEmail, textViewPW_Change, textViewNIC, viewFirstName, viewLastName, viewNumber, viewEmail;
    private LinearLayout History;
    private Button btnEditProfile;

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

        // Initialize Buttons
        btnEditProfile = findViewById(R.id.buttonEdit);

        // Initialize Linear Layout
        History = (LinearLayout) findViewById(R.id.LayoutViewHistory);
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ViewHistory = new Intent(ProfileActivity.this, ReservationHistoryActivity.class);
                startActivity(ViewHistory);
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent EditProfile = new Intent(ProfileActivity.this, EditProfileActivity.class);

                // Pass the data back to Edit Profile Activity
                EditProfile.putExtra("token", token);
                EditProfile.putExtra("userDetails", userDetails);

                // Start the Edit Profile Activity with the intent
                startActivity(EditProfile);

            }
        });

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

    private void loading() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void loadingCancel() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.dismiss();
    }
}