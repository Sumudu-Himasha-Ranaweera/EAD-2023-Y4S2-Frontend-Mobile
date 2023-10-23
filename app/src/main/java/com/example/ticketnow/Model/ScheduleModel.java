package com.example.ticketnow.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;


public class ScheduleModel implements Parcelable {

    private String id;
    private String fromLocation;
    private String toLocation;
    private int ticketPrice;
    private String trainName;
    private String trainNumber;
    private String status;
    private int totalSeats;

    public ScheduleModel(String id, String fromLocation, String toLocation, int ticketPrice,
                         String trainName, String trainNumber, String status, int totalSeats) {
        this.id = id;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.ticketPrice = ticketPrice;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.status = status;
        this.totalSeats = totalSeats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }


    // Parcelable implementation
    protected ScheduleModel(Parcel in) {
        id = in.readString();
        fromLocation = in.readString();
        toLocation = in.readString();
        ticketPrice = in.readInt();
        trainName = in.readString();
        trainNumber = in.readString();
        status = in.readString();
        totalSeats = in.readInt();
    }

    @Override
    public String toString() {
        // Use Gson to serialize the object to JSON format.
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static final Creator<ScheduleModel> CREATOR = new Creator<ScheduleModel>() {
        @Override
        public ScheduleModel createFromParcel(Parcel in) {
            return new ScheduleModel(in);
        }

        @Override
        public ScheduleModel[] newArray(int size) {
            return new ScheduleModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fromLocation);
        dest.writeString(toLocation);
        dest.writeInt(ticketPrice);
        dest.writeString(trainName);
        dest.writeString(trainNumber);
        dest.writeString(status);
        dest.writeInt(totalSeats);
    }

}
