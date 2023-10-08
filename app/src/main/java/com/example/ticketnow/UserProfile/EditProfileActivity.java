package com.example.ticketnow.UserProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.ticketnow.R;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.R;
import com.example.ticketnow.TestActivity;
import com.example.ticketnow.UserLogin.NewLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class EditProfileActivity extends AppCompatActivity {

    private EditText FirstName, LastName, ContactNumber, Email;
    private TextView NIC, ViewFirstName;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Retrieve extras from the intent
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String userDetails = intent.getStringExtra("userDetails");

        try {
            JSONObject userDetailsJson = new JSONObject(userDetails);

            // Extract user details from the JSON
            String nic = userDetailsJson.getString("nic");
            String firstName = userDetailsJson.getString("firstName");
            String lastName = userDetailsJson.getString("lastName");
            String contactNumber = userDetailsJson.getString("contactNumber");
            String email = userDetailsJson.getString("email");

            // Initialize EditTexts
            NIC = findViewById(R.id.ViewNIC);
            FirstName = findViewById(R.id.ViewFirstName);
            LastName = findViewById(R.id.ViewLastName);
            ContactNumber = findViewById(R.id.ViewNumber);
            Email = findViewById(R.id.ViewEmail);
//            ViewFirstName = findViewById(R.id.textViewNIC);

            // Initialize Save Button
            btnSave = findViewById(R.id.Save);

            // Initialize Cancel Button
            btnCancel = findViewById(R.id.Cancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Cancel button clicked, just go back to the Profile Activity
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back here on pressing back
                }
            });

            // Set the existing user details in the EditTexts
            NIC.setText(nic);
            FirstName.setText(firstName);
            LastName.setText(lastName);
            ContactNumber.setText(contactNumber);
            Email.setText(email);
//            ViewFirstName.setText("㋡ Welcome " + firstName +"!");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the updated user details
                    String updatedNIC = NIC.getText().toString().trim();
                    String updatedFirstName = FirstName.getText().toString().trim();
                    String updatedLastName = LastName.getText().toString().trim();
                    String updatedContactNumber = ContactNumber.getText().toString().trim();
                    String updatedEmail = Email.getText().toString().trim();

                    // Update the user details in the JSON
                    try {
                        userDetailsJson.put("nic", updatedNIC);
                        userDetailsJson.put("firstName", updatedFirstName);
                        userDetailsJson.put("lastName", updatedLastName);
                        userDetailsJson.put("contactNumber", updatedContactNumber);
                        userDetailsJson.put("email", updatedEmail);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Make the update request
                    updateUserData(token, userDetailsJson);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUserData(String token, JSONObject updatedUserDetails) {
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
                        StyleableToast.makeText(EditProfileActivity.this, "Profile updated successfully!", R.style.SuccessToast).show();
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
                        Toast.makeText(EditProfileActivity.this, "Update failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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