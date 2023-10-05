package com.example.ticketnow.Walkthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ticketnow.R;
import com.example.ticketnow.UserLogin.WelcomeActivity;

public class SliderTwoActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_two);

        start = (Button) findViewById(R.id.button_start);

        //next button connection
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent slide = new Intent(SliderTwoActivity.this, WelcomeActivity.class);
                startActivity(slide);
            }
        });

    }
}