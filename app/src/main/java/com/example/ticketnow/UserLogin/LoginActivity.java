package com.example.ticketnow.UserLogin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.HomeActivity;
import com.example.ticketnow.R;
import com.example.ticketnow.Sqlite.DatabaseHelper;
import org.json.JSONException;
import org.json.JSONObject;
import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {

    //attribute for remove bottom status bar
//    private View decorView;

    Button btnGo;
    EditText NIC, password;
    TextView TextSignUp;
    ConstraintLayout Signup_Constraint;

    DatabaseHelper databaseHelper;

    // Default constructor with no arguments
    public LoginActivity() {
        // Default constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        btnGo = (Button) findViewById(R.id.btn_login);

        NIC = (EditText) findViewById(R.id.EditTxtNIC);
        password = (EditText) findViewById(R.id.EditTxtPassword);

        Signup_Constraint = (ConstraintLayout) findViewById(R.id.SignUP_Constraint);
        // Set a click listener for the "Sign Up" textView to redirect to the registration page
        Signup_Constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }
        });

        // Set a click listener for the login button
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input for NIC and password
                String userInputNIC = NIC.getText().toString().trim();
                String userInputPassword = password.getText().toString().trim();

                //Validations
//                if (!isNICValid(userInputNIC)) {
//                    // Display a toast message for invalid NIC
//                    StyleableToast.makeText(LoginActivity.this, "Invalid NIC", R.style.InvalidToast).show();
//                } else if (!isValidPassword(userInputPassword)) {
//                    // Display a toast message for empty password
//                    StyleableToast.makeText(LoginActivity.this, "Password should be at least 6 characters", R.style.InvalidToast).show();
//                } else {
//                    // Call the method to perform sign-in
//                    signIn(userInputNIC, userInputPassword);
//                }

//                // Call the method to perform sign-in
                signIn(userInputNIC, userInputPassword);

            }
        });

    }

    private void signIn(String nic, String password) {
        // Create the HTTP request URL with user input
        String url = "https://restapi.azurewebsites.net/api/User/SignIn";

        // Create a JSONObject to hold the request body data
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("nic", nic);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Call the method to clear the user details from the table
        databaseHelper.clearUserDetails();


        // Create a JsonObjectRequest to make the HTTP POST request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the token and user details from the response
                            String token = response.getString("token");
                            JSONObject userDetails = response.getJSONObject("userDetails");

                            // Insert the data into the SQLite database
                            insertDataIntoDatabase(token, userDetails.toString());

                            // Display the token and user details in the console
                            System.out.println("Token: " + token);
                            System.out.println("User Details: " + userDetails.toString());

                            // Display a toast message indicating successful sign-in
                            StyleableToast.makeText(LoginActivity.this, "Sign-in successful!", R.style.SuccessToast).show();

                            // Create an intent to navigate to the ProfileActivity and pass response details as extras
                            Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                            home.putExtra("token", token);
                            home.putExtra("userDetails", userDetails.toString());

                            // Start the ProfileActivity with the intent
                            startActivity(home);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Display an error message in case of an error
                        System.err.println("Error123: " + error.networkResponse);
                        StyleableToast.makeText(LoginActivity.this, "Sign-in Failed !", R.style.InvalidToast).show();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }

//    private boolean isNICValid(String nicNumber) {
//        if (nicNumber.length() == 10) {
//            if (nicNumber.substring(0, 9).matches("\\d+") && ("x".equals(nicNumber.substring(9, 10).toLowerCase()) || "v".equals(nicNumber.substring(9, 10).toLowerCase()))) {
//                return true; // New NIC format
//            }
//        } else if (nicNumber.length() == 12 && nicNumber.matches("\\d+")) {
//            return true; // Old NIC format
//        }
//        return false; // NIC is invalid
//    }

    private boolean isValidPassword(String password) {
        // You can define your password validation logic here
        // For example, check if the password meets certain criteria like length, special characters, etc.
        // Return true if the password is valid, otherwise false
        return password.length() >= 5; // Example: Password should be at least 6 characters
    }

    private void insertDataIntoDatabase(String token, String userDetails) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TOKEN, token);
        values.put(DatabaseHelper.COLUMN_USER_DETAILS, userDetails);

        // Insert the data into the database
        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }
}