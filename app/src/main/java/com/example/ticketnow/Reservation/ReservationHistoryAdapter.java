package com.example.ticketnow.Reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketnow.Model.ReserveHistoryModel;
import com.example.ticketnow.Model.ScheduleModel;
import com.example.ticketnow.R;
import com.example.ticketnow.Schedule.ScheduleAdapter;

import java.util.List;

public class ReservationHistoryAdapter extends RecyclerView.Adapter<ReservationHistoryAdapter.ReserveViewHolder> {

    private Context context;
    private List<ReserveHistoryModel> list;

    public ReservationHistoryAdapter(Context context, List<ReserveHistoryModel> list) {
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
        ReserveHistoryModel reserve = list.get(position);

        holder.displayNameTextView.setText("Display Name : " + reserve.getDisplayName());
        holder.createDateTextView.setText("Created At : " + reserve.getCreatedAt());
        holder.reserveCountTextView.setText("No of Reservations : " + reserve.getReservedCount());
        holder.reserveDateTextView.setText("Reservation Date : " + reserve.getReservationDate());
        holder.statusTextView.setText("Reservation Status : " + reserve.getReservationStatus());
        holder.amountTextView.setText("Full Tickets Price : " + reserve.getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReserveViewHolder extends RecyclerView.ViewHolder {

        public TextView displayNameTextView, createDateTextView, reserveCountTextView, reserveDateTextView, statusTextView, amountTextView;

        public ReserveViewHolder(View view) {
            super(view);

            displayNameTextView = view.findViewById(R.id.displayNameTextView);
            createDateTextView = view.findViewById(R.id.createDateTextView);
            reserveCountTextView = view.findViewById(R.id.reserveCountTextView);
            reserveDateTextView = view.findViewById(R.id.reserveDateTextView);
            statusTextView = view.findViewById(R.id.statusTextView);
            amountTextView = view.findViewById(R.id.amountTextView);
        }
    }
}