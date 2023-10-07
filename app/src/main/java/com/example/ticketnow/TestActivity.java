package com.example.ticketnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.ticketnow.UserLogin.NewLoginActivity;
import com.example.ticketnow.UserLogin.RegisterActivity;
import com.example.ticketnow.UserLogin.WelcomeActivity;

import java.io.IOException;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    Button Back;
    TextView textViewArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Back = (Button) findViewById(R.id.btnBack);


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StyleableToast.makeText(TestActivity.this, "Sign-in Failed !", R.style.InvalidToast).show();
            }
        });

        textViewArea = (TextView) findViewById(R.id.response);

        findViewById(R.id.btnGet).setOnClickListener(click->{
            Request.Builder builder = new Request.Builder();
//            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "");
            Request request = builder
                    .url("https://restapi.azurewebsites.net/api/User?page=1&perPage=10&direction=desc")
                    .get()
                    .build();
            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                textViewArea.setText(request.body().toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}