package com.example.ticketnow.Reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.ticketnow.R;
import com.example.ticketnow.Sqlite.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.UserProfile.ProfileActivity;

import io.github.muddz.styleabletoast.StyleableToast;


public class TicketModifyActivity extends AppCompatActivity {

    NumberPicker NoOfTickets;
    EditText Name;
    TextView reservationDate, totalPrice;
    ImageButton Date;

    Button updateButton;

    int year, month, day;

    // Variables to store ticket price and total ticket price
    int ticketPrice;
    int reservedCount;
    int amount;
    String ReserveDate;
    String createdAt;
    String reservationStatus, reservationId, scheduleId;

    DatabaseHelper databaseHelper;

    // Define your API endpoint
    private static final String API_ENDPOINT = "https://restapi.azurewebsites.net/api/Reservation/updateReservationForSchedule/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_modify);

        Name = (EditText) findViewById(R.id.ViewDisplayName);
        reservationDate = (TextView) findViewById(R.id.ViewReservationDate);
        totalPrice = (TextView) findViewById(R.id.ViewAmount);
        Date = (ImageButton) findViewById(R.id.selectDate);
        updateButton = (Button) findViewById(R.id.updateButton);
        databaseHelper = new DatabaseHelper(this);

        // Initialize the Volley RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Set an OnClickListener for the "updateButton"
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Construct the request URL with scheduleId and reservationId
                String requestUrl = API_ENDPOINT + scheduleId + "/" + reservationId;

                // Create a JSON request body
                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("userId", getDetailsFromSQLite());
                    requestBody.put("displayName", Name.getText().toString());
                    requestBody.put("createdAt", createdAt);
                    requestBody.put("reservedCount", reservedCount);
                    requestBody.put("reservationDate", ReserveDate);
                    requestBody.put("reservationStatus", reservationStatus);
                    requestBody.put("amount", amount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create a StringRequest to send the HTTP request
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, requestUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Handle the response, e.g., update UI or show a message
                                // Note: You should implement proper error handling and response parsing here.
                                // For simplicity, this example assumes a successful response.
                                Toast.makeText(TicketModifyActivity.this, "Reservation updated successfully!", Toast.LENGTH_SHORT).show();
                                Intent profile = new Intent(TicketModifyActivity.this, ReservationHistoryActivity.class);
                                startActivity(profile);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle the error, e.g., show an error message
                                Toast.makeText(TicketModifyActivity.this, "Error updating reservation", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    public byte[] getBody() {
                        // Convert the JSON request body to bytes
                        return requestBody.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };

                // Add the request to the RequestQueue to execute it
                requestQueue.add(stringRequest);
            }
        });


        NoOfTickets = (NumberPicker) findViewById(R.id.NumberPicker_Tickets);
        // Initialize the NumberPicker and add a value change listener.
        NoOfTickets.setMinValue(1);
        NoOfTickets.setMaxValue(4);

        // Retrieve the details from the Intent
        Intent intent = getIntent();

        scheduleId = intent.getStringExtra("scheduleId");
        reservationId = intent.getStringExtra("reservationId");
        ticketPrice = intent.getIntExtra("ticketPrice", 0);
        String displayName = intent.getStringExtra("displayName");
        createdAt = intent.getStringExtra("createdAt");
        reservedCount = intent.getIntExtra("reservedCount", 0);
        ReserveDate = intent.getStringExtra("reservationDate");
        reservationStatus = intent.getStringExtra("reservationStatus");
        amount = intent.getIntExtra("amount", 0);

        // Print the details in the console
        System.out.println("ScheduleId: " + scheduleId);
        System.out.println("ReservationId: " + reservationId);
        System.out.println("ticketPrice: " + ticketPrice);
        System.out.println("displayName: " + displayName);
        System.out.println("createdAt: " + createdAt);
        System.out.println("reservedCount: " + reservedCount);
        System.out.println("reservationDate: " + ReserveDate);
        System.out.println("reservationStatus: " + reservationStatus);
        System.out.println("amount: " + amount);



        // Set the values in the TextViews
        NoOfTickets.setValue(reservedCount);
        Name.setText(displayName);
        totalPrice.setText(amount + " LKR"); // Display the default amount
        reservationDate.setText(ReserveDate);



        // Split the reservation date to set year, month, and day
        String[] dateParts = ReserveDate.split("-");
        year = Integer.parseInt(dateParts[0]);
        month = Integer.parseInt(dateParts[1]);
        day = Integer.parseInt(dateParts[2].substring(0, 2));




        // Set up a DatePickerDialog to allow users to change the reservation date
        Date.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, monthOfYear, dayOfMonth) -> {
                // Update the displayed reservation date and the internal year, month, and day variables
                reservationDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                this.year = year;
                this.month = monthOfYear;
                this.day = dayOfMonth;
            }, year, month - 1, day);
            datePickerDialog.show();
        });



        // Set a value change listener for the NumberPicker to update the amount
        NoOfTickets.setOnValueChangedListener((picker, oldVal, newVal) -> {
            reservedCount = newVal;
            amount = ticketPrice * reservedCount; // Recalculate the amount based on the new reservedCount
            totalPrice.setText(amount + " LKR"); // Display the updated amount
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
                return userId;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        cursor.close();

        // Return a default value if no user details were found
        return "No UserID";
    }
}