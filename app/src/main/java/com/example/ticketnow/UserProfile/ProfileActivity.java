package com.example.ticketnow.UserProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.R;
import com.example.ticketnow.Reservation.ReservationHistoryActivity;
import com.example.ticketnow.TestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewFirstName, textViewUserEmail, textViewPW_Change, textViewNIC, viewFirstName, viewLastName, viewNumber, viewEmail;
    private LinearLayout History, Deactivate;
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

        //User Deactivate
        Deactivate = (LinearLayout) findViewById(R.id.LayoutDeactivate);
        Deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject userDetailsJson = null;
                try {
                    userDetailsJson = new JSONObject(userDetails);

                    userDetailsJson.put("isActive", false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DeactivateUserData(token, userDetailsJson);
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

    private void DeactivateUserData(String token, JSONObject updatedUserDetails) {
        String apiUrl = "https://restapi.azurewebsites.net/api/User/:userId"; // Update with the correct URL

        // Extract the user ID from the JSON
        String userId;
        try {
            userId = updatedUserDetails.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Update the URL with the user ID
        String url = apiUrl.replace(":userId", userId);

        // Create the request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, updatedUserDetails,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Update was successful
                        StyleableToast.makeText(ProfileActivity.this, "Profile Deactivated successfully!", R.style.SuccessToast).show();
                        Log.d("UpdateResponse", response.toString());

                        // Send the updated data back to the Profile Activity
                        Intent intent = new Intent();
                        intent.putExtra("updatedUserDetails", response.toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(ProfileActivity.this, "Deactivate failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }
}