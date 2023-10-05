package com.example.ticketnow.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ticketnow.R;

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

        //After click "Sign Up" textView it will redirect to Sign Up Page
        TextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewLoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });//End code

    }
}