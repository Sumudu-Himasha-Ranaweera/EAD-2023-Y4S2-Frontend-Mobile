package com.example.ticketnow.Reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketnow.Model.BookingsModel;
import com.example.ticketnow.Model.ReserveHistoryModel;
import com.example.ticketnow.Model.ScheduleModel;
import com.example.ticketnow.R;
import com.example.ticketnow.Schedule.ScheduleAdapter;

import java.util.List;

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
        holder.textViewTrainName.setText(booking.getTrainName());
        holder.textViewTrainNumber.setText(booking.getTrainNumber());

        holder.displayNameTextView.setText("Display Name : " + booking.getDisplayName());
        holder.createDateTextView.setText("Created At : " + booking.getCreatedAt());
        holder.reserveCountTextView.setText("No of Reservations : " + booking.getReservedCount());
        holder.reserveDateTextView.setText("Reservation Date : " + booking.getReservationDate());
        holder.statusTextView.setText("Reservation Status : " + booking.getReservationStatus());
        holder.amountTextView.setText("Full Tickets Price : " + booking.getAmount());

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
        }
    }
}