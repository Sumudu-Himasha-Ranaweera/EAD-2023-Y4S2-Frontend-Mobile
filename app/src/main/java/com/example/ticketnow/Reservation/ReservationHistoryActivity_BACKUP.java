//package com.example.ticketnow.Reservation;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.ProgressDialog;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.ticketnow.Model.ReserveHistoryModel;
//import com.example.ticketnow.Model.ScheduleModel;
//import com.example.ticketnow.R;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReservationHistoryActivity extends AppCompatActivity {
//
//    // Replace "YOUR_USER_ID" with the actual userId you want to use
//    String userId = "string"; // Replace with the actual user ID
//
//    // Replace "YOUR_BASE_URL" with the actual base URL of your API
//    private String url = "https://restapi.azurewebsites.net/api/Reservation/getReservationsByUserId/" + userId;
//
//    private RecyclerView mList;
//
//    private LinearLayoutManager linearLayoutManager;
//    private DividerItemDecoration dividerItemDecoration;
//    private List<ReserveHistoryModel> reserveList;
//    private RecyclerView.Adapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reservation_history);
//
//        mList = findViewById(R.id.history_list);
//
//        reserveList = new ArrayList<>();
//        adapter = new ReservationHistoryAdapter(getApplicationContext(),reserveList);
//
//        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
//
//        mList.setHasFixedSize(true);
//        mList.setLayoutManager(linearLayoutManager);
//        mList.addItemDecoration(dividerItemDecoration);
//        mList.setAdapter(adapter);
//
//        //Execute the method in onCreate()
//        getData(userId);
//    }
//
//    private void getData(String userId) {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // Parse the JSON response and update the reserveList
//                        parseResponse(response);
//                        progressDialog.dismiss();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors
//                        Log.e("API Request", "Error: " + error.getMessage());
//                        Toast.makeText(ReservationHistoryActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        requestQueue.add(jsonArrayRequest);
//    }
//
//    private void parseResponse(JSONArray response) {
//        // Parse the JSON response and update the reserveList
//        reserveList.clear(); // Clear the previous data
//
//        try {
//            for (int i = 0; i < response.length(); i++) {
//                JSONObject jsonObject = response.getJSONObject(i);
//                // Extract data and create ReserveHistoryModel objects
//                String displayName = jsonObject.getString("displayName");
//                String createdAt = jsonObject.getString("createdAt");
//                int reservedCount = jsonObject.getInt("reservedCount");
//                String reservationDate = jsonObject.getString("reservationDate");
//                String reservationStatus = jsonObject.getString("reservationStatus");
//                int amount = jsonObject.getInt("amount");
//
//                // Create a ReserveHistoryModel object and add to the list
//                ReserveHistoryModel reserveHistoryModel =
//                        new ReserveHistoryModel(displayName, createdAt, reservedCount, reservationDate, reservationStatus, amount);
//                reserveList.add(reserveHistoryModel);
//            }
//
//            // Notify the adapter that the data has changed
//            adapter.notifyDataSetChanged();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
////            Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
//        }
//    }
//}