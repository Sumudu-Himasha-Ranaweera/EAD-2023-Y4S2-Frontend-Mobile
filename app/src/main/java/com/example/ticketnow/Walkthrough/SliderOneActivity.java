package com.example.ticketnow.Walkthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ticketnow.R;
import com.example.ticketnow.UserLogin.NewLoginActivity;
import com.example.ticketnow.UserLogin.WelcomeActivity;

public class SliderOneActivity extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_one);

        next = (Button) findViewById(R.id.button_next);

        //next button connection
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent slide = new Intent(SliderOneActivity.this, SliderTwoActivity.class);
                startActivity(slide);
            }
        });
    }
}