package com.example.ticketnow.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ticketnow.R;

import com.example.ticketnow.R;

import java.util.ArrayList;

public class TrainScheduleActivity extends AppCompatActivity {

    private Button searchButton;
    private Spinner startStationSpinner;
    private Spinner endStationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedule);

        searchButton = findViewById(R.id.buttonSearch);
        startStationSpinner = findViewById(R.id.dropdown_start_station);
        endStationSpinner = findViewById(R.id.dropdown_end_station);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startStation = startStationSpinner.getSelectedItem().toString();
                String endStation = endStationSpinner.getSelectedItem().toString();

                // Pass the selected stations to AllScheduleActivity
                Intent intent = new Intent(TrainScheduleActivity.this, AllScheduleActivity.class);
                intent.putExtra("startStation", startStation);
                intent.putExtra("endStation", endStation);
                startActivity(intent);
            }
        });
    }
}