package com.example.ticketnow.Reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.R;
import com.example.ticketnow.Sqlite.DatabaseHelper;

import com.example.ticketnow.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;

public class TicketReserveActivity extends AppCompatActivity {

    NumberPicker NoOfTickets;
    TextView ViewAmount;

    // Define variables for Name and NIC
    TextView ViewDisplayName;
    TextView ViewNIC;

    // Button for reserving the tickets
    Button reserveButton;

    // Schedule ID retrieved from the intent
    String scheduleId;

    // Volley Request Queue
    RequestQueue requestQueue;

    // Variables to store ticket price and total ticket price
    double ticketPrice;
    double totalTicketPrice = 0;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reserve);

        NoOfTickets = (NumberPicker) findViewById(R.id.NumberPicker_Tickets);
        ViewDisplayName = (TextView) findViewById(R.id.ViewDisplayName);
        ViewNIC = (TextView) findViewById(R.id.ViewNIC);
        ViewAmount = (TextView) findViewById(R.id.ViewAmount); // TextView to display total ticket price
        databaseHelper = new DatabaseHelper(this);

        // Initialize the reserve button
        reserveButton = (Button) findViewById(R.id.reserveButton);


        // Initialize the NumberPicker and add a value change listener.
        NoOfTickets.setMinValue(1);
        NoOfTickets.setMaxValue(4);

        // Retrieve Name and NIC from SQLite and set them to TextViews
        getDetailsFromSQLite();

        // Retrieve extras from the intent
        Intent intent = getIntent();
        if (intent.hasExtra("selectedSchedule")) {
            String selectedScheduleJson = intent.getStringExtra("selectedSchedule");

            try {
                // Parse the JSON string into a JSONObject
                JSONObject selectedSchedule = new JSONObject(selectedScheduleJson);

                // Now you can access the properties of selectedSchedule
                ticketPrice = selectedSchedule.optDouble("ticketPrice", 0.0);
                scheduleId = selectedSchedule.optString("id");

                // Calculate and display the total ticket price
                calculateAndDisplayTotalPrice();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Set a value change listener for the NumberPicker to update the total ticket price
        NoOfTickets.setOnValueChangedListener((picker, oldVal, newVal) -> {
            calculateAndDisplayTotalPrice();
        });

        // Initialize the Volley Request Queue
        requestQueue = Volley.newRequestQueue(this);

        // Set a click listener for the Reserve button
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                // Perform the HTTP POST request to create a reservation
                createReservation();
            }
        });

    }

    private String getDetailsFromSQLite() {
        Cursor cursor = databaseHelper.getReadableDatabase().query(
                DatabaseHelper.TABLE_NAME,
                new String[] {DatabaseHelper.COLUMN_USER_DETAILS},
                null, // No specific WHERE clause, so it retrieves all records.
                null, // No specific selection arguments.
                null, // No specific group by clause.
                null, // No specific having clause.
                null  // No specific order by clause.
        );

        if (cursor.moveToFirst()) {
            String userDetailsJson = cursor.getString(0);

            try {
                JSONObject userDetails = new JSONObject(userDetailsJson);
                String userId = userDetails.optString("id", "");
                String fname = userDetails.optString("firstName", "");
                String lname = userDetails.optString("lastName", "");
                String nic = userDetails.optString("nic", "");

                // Set the retrieved Name and NIC to the TextViews
                ViewDisplayName.setText(fname + " " + lname);
                ViewNIC.setText(nic);

                return userId;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        cursor.close();

        // Return a default value if no user details were found
        return "No UserID";
    }

    private void calculateAndDisplayTotalPrice() {
        int selectedNumberOfTickets = NoOfTickets.getValue();
        totalTicketPrice = ticketPrice * selectedNumberOfTickets;
        ViewAmount.setText(totalTicketPrice + " LKR");
    }

    // ---------------------------------- Reserve -----------------------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createReservation() {
        try {
            JSONObject requestBody = new JSONObject();
            String currentDateTime = getCurrentDateTimeAsString();


            requestBody.put("userId", getDetailsFromSQLite());
            requestBody.put("displayName", ViewDisplayName.getText());
            requestBody.put("createdAt", currentDateTime); // Add "createdAt" field
            requestBody.put("reservedCount", NoOfTickets.getValue());
            requestBody.put("reservationDate", currentDateTime); // Add "reservationDate" field
            requestBody.put("reservationStatus", "RESERVED");
            requestBody.put("amount", totalTicketPrice);
            requestBody.put("scheduleId", scheduleId);

            String url = "https://restapi.azurewebsites.net/api/Reservation/createForSchedule/" + scheduleId;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response if needed
                            // For example, show a confirmation message
                            // or navigate to a confirmation page.
                            StyleableToast.makeText(TicketReserveActivity.this, "Successfully Reserved", Toast.LENGTH_SHORT, R.style.SuccessToast).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error if the request fails
                            // For example, show an error message to the user.
                            StyleableToast.makeText(TicketReserveActivity.this, "Reservation Failed", Toast.LENGTH_SHORT, R.style.InvalidToast).show();
                        }
                    });

            // Add the request to the Volley queue
            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentDateTimeAsString() {
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        // Get the current date and time
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("UTC"));

        // Format the current date and time using the formatter
        return currentDateTime.format(formatter);
    }
}