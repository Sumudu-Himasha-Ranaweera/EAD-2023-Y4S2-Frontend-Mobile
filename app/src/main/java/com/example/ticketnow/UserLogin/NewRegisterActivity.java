package com.example.ticketnow.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ticketnow.R;
import com.example.ticketnow.TestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.muddz.styleabletoast.StyleableToast;

public class NewRegisterActivity extends AppCompatActivity {

    //attribute for remove bottom status bar
    private View decorView;

    // Components from the XML layout
    EditText editTxtNIC, editTxtFname, editTxtLname, editTxtNumber, editTxtEmail, editTxtPassword;
    TextView txt_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        // Initialize EditText fields
        editTxtNIC = findViewById(R.id.EditTxtID);
        editTxtFname = findViewById(R.id.EditTxtFname);
        editTxtLname = findViewById(R.id.EditTxtLname);
        editTxtNumber = findViewById(R.id.EditTxtNumber);
        editTxtEmail = findViewById(R.id.EditTxtEmail);
        editTxtPassword = findViewById(R.id.EditTxtPassword);

        txt_signIn = (TextView) findViewById(R.id.HaveAccount);

        // Already have an account will redirect to Login Page
        txt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginPage = new Intent(NewRegisterActivity.this, NewLoginActivity.class);
                startActivity(LoginPage);
            }
        });

        // ImageButton for registration
        ImageButton imgBtnRegister = findViewById(R.id.img_btn_check);

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


        //Remove bottom status bar
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
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
                        StyleableToast.makeText(NewRegisterActivity.this, "User registered successfully", R.style.SuccessToast).show();

                        Intent SignUp =new Intent(NewRegisterActivity.this, TestActivity.class);
                        startActivity(SignUp);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Log.e("RegisterActivity", "Registration failed: " + error.toString());
                StyleableToast.makeText(NewRegisterActivity.this, "Registration failed:>>>>>> " + error.getMessage(), R.style.InvalidToast).show();
            }
        });

        // Add the request to the RequestQueue
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }









    //remove bottom status bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}