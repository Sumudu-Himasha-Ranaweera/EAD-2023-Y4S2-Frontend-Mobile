package com.example.ticketnow.Reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.Model.BookingsModel;
import com.example.ticketnow.R;
import com.example.ticketnow.Sqlite.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ReservationHistoryActivity extends AppCompatActivity {

    private ReservationHistoryAdapter reservationHistoryAdapter;
    private List<BookingsModel> bookingsList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_history);

        databaseHelper = new DatabaseHelper(this);
        String userId = getUserIdFromDatabase(); // Get the userId from the SQLite Database

        bookingsList = new ArrayList<>();
        reservationHistoryAdapter = new ReservationHistoryAdapter(this, bookingsList);

        // Initialize the RecyclerView and set the adapter
        RecyclerView recyclerView = findViewById(R.id.history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reservationHistoryAdapter);

        // Make an HTTP request to fetch reservations
        fetchReservations(userId);

    }

    // Method to fetch reservations via HTTP request
    private void fetchReservations(String userId) {
        String url = "https://restapi.azurewebsites.net/api/Reservation/getSchedulesByUserId/" + userId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReservationHistoryActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    // Method to parse the JSON response and update the adapter
    private void parseResponse(JSONArray response) {
        try {
            String userId = getUserIdFromDatabase(); // Get the userId from the SQLite Database

            for (int i = 0; i < response.length(); i++) {
                JSONObject scheduleObject = response.getJSONObject(i);

                // Check if the "reservations" array exists in the scheduleObject
                if (scheduleObject.has("reservations")) {
                    JSONArray reservationsArray = scheduleObject.getJSONArray("reservations");

                    // Check if the "userId" in the reservations array matches the SQLite userId
                    boolean isMatchingUser = false;
                    for (int j = 0; j < reservationsArray.length(); j++) {
                        JSONObject reservationObject = reservationsArray.getJSONObject(j);
                        String reservationUserId = reservationObject.optString("userId", "");

                        if (reservationUserId.equals(userId)) {
                            isMatchingUser = true;
                            // Extract reservation details
                            String scheduleId = scheduleObject.getString("id");
                            String fromLocation = scheduleObject.getString("fromLocation");
                            String toLocation = scheduleObject.getString("toLocation");
                            int ticketPrice = scheduleObject.getInt("ticketPrice");

                            // Extract train information
                            JSONObject trainObject = scheduleObject.getJSONObject("train");
                            String trainName = trainObject.getString("trainName");
                            String trainNumber = trainObject.getString("trainNumber");

                            // Extract reservation details
                            String reservationId = reservationObject.getString("id");
                            String displayName = reservationObject.getString("displayName");
                            String createdAt = reservationObject.getString("createdAt");
                            int reservedCount = reservationObject.getInt("reservedCount");
                            String reservationDate = reservationObject.getString("reservationDate");
                            String reservationStatus = reservationObject.getString("reservationStatus");
                            int amount = reservationObject.getInt("amount");

                            // Create a BookingsModel for the reservation and add it to the list
                            BookingsModel booking = new BookingsModel(
                                    scheduleId,
                                    fromLocation,
                                    toLocation,
                                    ticketPrice,
                                    trainName,
                                    trainNumber,
                                    reservationId,
                                    displayName,
                                    createdAt,
                                    reservedCount,
                                    reservationDate,
                                    reservationStatus,
                                    amount
                            );

                            bookingsList.add(booking);
                        }
                    }

                    if (!isMatchingUser) {
                        // Display an error message
                        Toast.makeText(this, "No reservations found for the current user.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            reservationHistoryAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method to get the userId from the SQLite Database
    private String getUserIdFromDatabase() {
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