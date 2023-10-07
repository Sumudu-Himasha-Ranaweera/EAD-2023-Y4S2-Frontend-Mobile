package com.example.ticketnow.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import java.io.IOException;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import android.util.Log;
//import okhttp3.Call;
//import okhttp3.Callback;
//import org.jetbrains.annotations.NotNull;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.ticketnow.R;
import com.example.ticketnow.TestActivity;

import io.github.muddz.styleabletoast.StyleableToast;

public class NewLoginActivity extends AppCompatActivity {

    ImageButton btnGo;
    EditText NIC, password;
    TextView TextSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        NIC = (EditText) findViewById(R.id.EditTxtNIC);
        password = (EditText) findViewById(R.id.EditTxtPassword);

        btnGo = (ImageButton) findViewById(R.id.btn_login);
        TextSignUp = (TextView) findViewById(R.id.txtSignUp);

        // Set a click listener for the "Sign Up" textView to redirect to the registration page
        TextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewLoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        // Set a click listener for the login button
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input for NIC and password
                String userInputNIC = NIC.getText().toString().trim();
                String userInputPassword = password.getText().toString().trim();

                // Call the method to perform sign-in
                signIn(userInputNIC, userInputPassword);
            }
        });
    }

    private void signIn(String nic, String password) {
        // Create the HTTP request URL with user input
        String url = "https://restapi.azurewebsites.net/api/User/SignIn?nic=" + nic + "&password=" + password;

        // Create a JsonObjectRequest to make the HTTP POST request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the token and user details from the response
                            String token = response.getString("token");
                            JSONObject userDetails = response.getJSONObject("userDetails");

                            // Display the token and user details in the console
                            System.out.println("Token: " + token);
                            System.out.println("User Details: " + userDetails.toString());

                            // Display a toast message indicating successful sign-in
                            StyleableToast.makeText(NewLoginActivity.this, "Sign-in successful!", R.style.SuccessToast).show();

                            Intent i = new Intent(NewLoginActivity.this, TestActivity.class);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Display an error message in case of an error
                        System.err.println("Error: " + error.toString());
                        StyleableToast.makeText(NewLoginActivity.this, "Sign-in Failed !", R.style.InvalidToast).show();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }

}