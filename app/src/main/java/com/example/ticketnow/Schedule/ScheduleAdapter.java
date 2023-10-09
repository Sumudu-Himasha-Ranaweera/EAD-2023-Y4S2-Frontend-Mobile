package com.example.ticketnow.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketnow.Model.ScheduleModel;
import com.example.ticketnow.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private List<ScheduleModel> list;

    public ScheduleAdapter(Context context, List<ScheduleModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        ScheduleModel schedule = list.get(position);

        holder.textTrainName.setText("Train Name: " + schedule.getTrainName());
        holder.textTrainNumber.setText("Train Number: " + schedule.getTrainNumber());
        holder.textStatus.setText("Status: " + schedule.getStatus());
        holder.textTicketPrice.setText("Ticket Price: " + schedule.getTicketPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView textTrainName, textTrainNumber, textStatus, textTicketPrice;

        public ScheduleViewHolder(View view) {
            super(view);

            textTrainName = view.findViewById(R.id.trainNameTextView);
            textTrainNumber = view.findViewById(R.id.trainNumberTextView);
            textStatus = view.findViewById(R.id.statusTextView);
            textTicketPrice = view.findViewById(R.id.ticketPriceTextView);
        }
    }
}