package com.example.ticketnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ticketnow.UserProfile.ProfileActivity;
import com.example.ticketnow.Schedule.TrainScheduleActivity;
import com.example.ticketnow.Reservation.TicketReserveActivity;

import io.github.muddz.styleabletoast.StyleableToast;

public class HomeActivity extends AppCompatActivity {

    CardView UserProfile, TrainSchedule, TicketReserve, AboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Retrieve extras from the intent
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String userDetails = intent.getStringExtra("userDetails");

        UserProfile = (CardView) findViewById(R.id.cardUserProfile);
        TrainSchedule = (CardView) findViewById(R.id.cardTrainSchedule);
        TicketReserve = (CardView) findViewById(R.id.cardTicketReserve);
        AboutUs = (CardView) findViewById(R.id.cardAboutUs);

        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StyleableToast.makeText(HomeActivity.this, "Yayy, Let's go to Profile!", Toast.LENGTH_SHORT, R.style.SuccessToast).show();

                // Pass the data back to Profile Activity
                Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
                profile.putExtra("token", token);
                profile.putExtra("userDetails", userDetails);

                // Start the HomeActivity with the intent
                startActivity(profile);

            }
        });

        TrainSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StyleableToast.makeText(HomeActivity.this, "Let's go to Train Schedule!", Toast.LENGTH_SHORT, R.style.SuccessToast).show();

                Intent schedule = new Intent(HomeActivity.this, TrainScheduleActivity.class);
                startActivity(schedule);
            }
        });

        TicketReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StyleableToast.makeText(HomeActivity.this, "Let's go to Ticket Reservation!", Toast.LENGTH_SHORT, R.style.SuccessToast).show();

                Intent reserve = new Intent(HomeActivity.this, TicketReserveActivity.class);
                startActivity(reserve);
            }
        });

    }
}