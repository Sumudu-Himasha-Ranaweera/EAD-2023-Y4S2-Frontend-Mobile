package com.example.ticketnow.Reservation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketnow.Model.BookingsModel;
import com.example.ticketnow.Model.ReserveHistoryModel;
import com.example.ticketnow.Model.ScheduleModel;
import com.example.ticketnow.R;
import com.example.ticketnow.Schedule.ScheduleAdapter;
import com.example.ticketnow.Reservation.TicketModifyActivity;
import com.example.ticketnow.UserProfile.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.muddz.styleabletoast.StyleableToast;

public class ReservationHistoryAdapter extends RecyclerView.Adapter<ReservationHistoryAdapter.ReserveViewHolder> {

    private Context context;
    private List<BookingsModel> list;

    public ReservationHistoryAdapter(Context context, List<BookingsModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ReservationHistoryAdapter.ReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_single_item, parent, false);
        return new ReservationHistoryAdapter.ReserveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReservationHistoryAdapter.ReserveViewHolder holder, int position) {
        BookingsModel booking = list.get(position);

        holder.textViewSTATUS.setText(booking.getReservationStatus());
        holder.textViewSTART.setText(booking.getFromLocation());
        holder.textViewEND.setText(booking.getToLocation());
//        holder.ticketPrice.setText(booking.getTicketPrice());

        holder.textViewTrainName.setText(booking.getTrainName());
        holder.textViewTrainNumber.setText(booking.getTrainNumber());

        holder.displayNameTextView.setText("Display Name : " + booking.getDisplayName());
        holder.createDateTextView.setText("Created At : " + booking.getCreatedAt());
        holder.reserveCountTextView.setText("No of Reservations : " + booking.getReservedCount());
        holder.reserveDateTextView.setText("Reservation Date : " + booking.getReservationDate());
        holder.statusTextView.setText("Reservation Status : " + booking.getReservationStatus());
        holder.amountTextView.setText("Full Tickets Price : " + booking.getAmount());

        //set click listener for the updateButton
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Send the details to TicketModifyActivity
                sendDetailsToTicketModifyActivity(booking);
            }
        });

        //set click listener for the deleteButton
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the reservation date from the booking
                String reservationDateStr = booking.getReservationDate();

                // Parse the reservation date string into a Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date reservationDate = dateFormat.parse(reservationDateStr);
                    Date currentDate = new Date(); // Get the current date

                    // Calculate the time difference in days
                    long timeDiff = reservationDate.getTime() - currentDate.getTime();
                    long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

                    // Check if the difference is greater than or equal to 4 days
                    if (daysDiff >= 4) {
                        // If yes, make a PUT request to update the reservation status
                        updateReservationStatus(booking.getScheduleId(), booking.getReservationId());
                    } else {
                        // If the difference is less than 4 days, show a message to the user

                        StyleableToast.makeText(context, "Reservation can only be cancelled if it's 4 or more days before the reservation date.", Toast.LENGTH_LONG, R.style.InvalidToast).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void sendDetailsToTicketModifyActivity(BookingsModel booking) {
        Intent modify = new Intent(context, TicketModifyActivity.class);

        // Put the details as extras in the intent
        modify.putExtra("scheduleId", booking.getScheduleId());
        modify.putExtra("reservationId", booking.getReservationId());
        modify.putExtra("ticketPrice", booking.getTicketPrice());
        modify.putExtra("displayName", booking.getDisplayName());
        modify.putExtra("createdAt", booking.getCreatedAt());
        modify.putExtra("reservedCount", booking.getReservedCount());
        modify.putExtra("reservationDate", booking.getReservationDate());
        modify.putExtra("reservationStatus", booking.getReservationStatus());
        modify.putExtra("amount", booking.getAmount());

        context.startActivity(modify);
    }


    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ReserveViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSTATUS, textViewSTART, textViewEND,
                textViewTrainName, textViewTrainNumber,
                displayNameTextView, createDateTextView, reserveCountTextView, reserveDateTextView,
                statusTextView, amountTextView;
        ImageButton updateButton, deleteButton;

        public ReserveViewHolder(View view) {
            super(view);

            textViewSTATUS = itemView.findViewById(R.id.textViewSTATUS);
            textViewSTART = itemView.findViewById(R.id.textViewSTART);
            textViewEND = itemView.findViewById(R.id.textViewEND);
            textViewTrainName = itemView.findViewById(R.id.textViewTrainName);
            textViewTrainNumber = itemView.findViewById(R.id.textViewTrainNumber);

            displayNameTextView = view.findViewById(R.id.displayNameTextView);
            createDateTextView = view.findViewById(R.id.createDateTextView);
            reserveCountTextView = view.findViewById(R.id.reserveCountTextView);
            reserveDateTextView = view.findViewById(R.id.reserveDateTextView);
            statusTextView = view.findViewById(R.id.statusTextView);
            amountTextView = view.findViewById(R.id.amountTextView);

            // Initialize the updateButton
            updateButton = view.findViewById(R.id.updateButton);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }

    private void updateReservationStatus(String scheduleId, String reservationId) {
        // Build the API URL
        String apiUrl = "https://restapi.azurewebsites.net/api/Reservation/updateReservationForSchedule/" + scheduleId + "/" + reservationId;

        // Create a JsonObjectRequest using Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response, for example, show a success message
                        StyleableToast.makeText(context, "Reservation cancelled successfully", Toast.LENGTH_LONG, R.style.SuccessToast).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors, for example, show an error message
                        StyleableToast.makeText(context, "Failed to cancel reservation", Toast.LENGTH_LONG, R.style.InvalidToast).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set any headers if needed, such as authorization
                Map<String, String> headers = new HashMap<>();
                // headers.put("Authorization", "Bearer your-access-token");
                return headers;
            }
        };

        // Add the request to the Volley request queue
        Volley.newRequestQueue(context).add(request);
    }

}