package com.example.ticketnow.UserLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ticketnow.R;
//import com.example.ticketnow.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.github.muddz.styleabletoast.StyleableToast;

public class RegisterActivity extends AppCompatActivity {

    // Components from the XML layout
    EditText editTxtNIC, editTxtFname, editTxtLname, editTxtNumber, editTxtEmail, editTxtPassword;
    TextView txt_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize EditText fields
        editTxtNIC = findViewById(R.id.EditTxtNIC);
        editTxtFname = findViewById(R.id.EditTxtFname);
        editTxtLname = findViewById(R.id.EditTxtLname);
        editTxtNumber = findViewById(R.id.EditTxtPhone);
        editTxtEmail = findViewById(R.id.EditTxtEmail);
        editTxtPassword = findViewById(R.id.EditTxtPassword);

//        txt_signIn = (TextView) findViewById(R.id.HaveAccount);

        // ImageButton for registration
        Button imgBtnRegister = findViewById(R.id.btn_register);

        // Set a click listener for registration
        imgBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String nic = editTxtNIC.getText().toString().trim();
                String firstName = editTxtFname.getText().toString().trim();
                String lastName = editTxtLname.getText().toString().trim();
                String contactNumber = editTxtNumber.getText().toString().trim();
                String email = editTxtEmail.getText().toString().trim();
                String password = editTxtPassword.getText().toString().trim();

                // Call the function to register the user
                registerUser(nic, firstName, lastName, contactNumber, email, password);
            }
        });

    }

    // Function to register the user
    private void registerUser(String nic, String firstName, String lastName, String contactNumber, String email, String password) {
        // Hard code salutation and userType
        int salutation = 1;
        int userType = 3;

        // Create a JSON object for the request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("salutation", salutation);
            requestBody.put("firstName", firstName);
            requestBody.put("lastName", lastName);
            requestBody.put("contactNumber", contactNumber);
            requestBody.put("email", email);
            requestBody.put("nic", nic);
            requestBody.put("userType", userType);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Define the POST request URL
        String url = "https://restapi.azurewebsites.net/api/User/SignUp";

        // Make a POST request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        // You can process the response JSON if needed
                        Log.d("RegisterActivity", "Registration successful. Response:>>>>>> " + response.toString());

                        // For simplicity, we'll just show a Toast
                        StyleableToast.makeText(RegisterActivity.this, "User registered successfully", R.style.SuccessToast).show();

                        Intent SignUp =new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(SignUp);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Log.e("RegisterActivity", "Registration failed: " + error.toString());
                StyleableToast.makeText(RegisterActivity.this, "Registration failed: " + error.getMessage(), R.style.InvalidToast).show();
            }
        });

        // Add the request to the RequestQueue
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}
