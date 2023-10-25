package com.example.ticketnow.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.Model.ScheduleModel;
import com.example.ticketnow.R;
import com.example.ticketnow.Reservation.TicketReserveActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllScheduleActivity extends AppCompatActivity {

    private String url = "https://restapi.azurewebsites.net/api/Schedule/getIncomingSchedules";

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ScheduleModel> scheduleList;
    private RecyclerView.Adapter adapter;

    private String startStation;
    private String endStation;

    private TextView viewFromLocation, viewToLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedule);

        mList = findViewById(R.id.main_list);
        viewFromLocation = findViewById(R.id.ViewFromLocation);
        viewToLocation = findViewById(R.id.ViewToLocation);

        // Retrieve the selected stations from the intent
        Intent intent = getIntent();
        startStation = intent.getStringExtra("startStation");
        endStation = intent.getStringExtra("endStation");

        // Set the selected stations to the TextViews
        viewFromLocation.setText(startStation);
        viewToLocation.setText(endStation);

        // Add a click listener for the RecyclerView items.
        mList.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull @NotNull RecyclerView rv, @NonNull @NotNull MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    int position = rv.getChildAdapterPosition(childView);
                    ScheduleModel selectedSchedule = scheduleList.get(position);

                    // Print the response body of the selected card to the console.
                    Log.d("Response Body >>>>>>> ", selectedSchedule.toString());

                    // Convert the selectedSchedule object to a JSON string
                    String selectedScheduleJson = scheduleModelToJSON(selectedSchedule);

                    // Start the TicketReservationActivity and pass necessary data.
                    Intent intent = new Intent(AllScheduleActivity.this, TicketReserveActivity.class);
                    intent.putExtra("selectedSchedule", selectedScheduleJson);
                    startActivity(intent);

                    return true; // Consume the click event to prevent it from propagating to other cards.
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull @NotNull RecyclerView rv, @NonNull @NotNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        scheduleList = new ArrayList<>();
        adapter = new ScheduleAdapter(this, scheduleList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        // Execute the method in onCreate()
        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Initialize a Volley request queue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ScheduleModel schedule = parseSchedule(jsonObject);

                                // Check if either of the stations matches the selected start or end station.
                                if (schedule.getFromLocation().equals(startStation) ||
                                        schedule.getToLocation().equals(endStation)) {
                                    scheduleList.add(schedule);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        // Add the request to the queue.
        requestQueue.add(jsonArrayRequest);
    }

    private ScheduleModel parseSchedule(JSONObject jsonObject) {
        String JSONID = jsonObject.optString("id", "");
        String fromLocation = jsonObject.optString("fromLocation", "");
        String toLocation = jsonObject.optString("toLocation", "");
        int JSONTicketPrice = jsonObject.optInt("ticketPrice", 0);

        JSONObject trainObject = jsonObject.optJSONObject("train");

        if (trainObject != null) {
            String JSONTrainName = trainObject.optString("trainName", "");
            String JSONTrainNumber = trainObject.optString("trainNumber", "");
            String JSONStatus = trainObject.optString("status", "");
            int JSONTotalSeats = trainObject.optInt("totalSeats", 0);

            return new ScheduleModel(JSONID, fromLocation, toLocation, JSONTicketPrice, JSONTrainName, JSONTrainNumber, JSONStatus, JSONTotalSeats);
        } else {
            // Return a new ScheduleModel with default values.
            return new ScheduleModel(JSONID, fromLocation, toLocation, JSONTicketPrice, "", "", "", 0);
        }
    }

    private String scheduleModelToJSON(ScheduleModel schedule) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", schedule.getId());
            json.put("fromLocation", schedule.getFromLocation());
            json.put("toLocation", schedule.getToLocation());
            json.put("ticketPrice", schedule.getTicketPrice());
            json.put("trainName", schedule.getTrainName());
            json.put("trainNumber", schedule.getTrainNumber());
            json.put("status", schedule.getStatus());
            json.put("totalSeats", schedule.getTotalSeats());
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}"; // Return an empty JSON object as a fallback
        }
    }
}
