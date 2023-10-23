package com.example.ticketnow.Model;

public class BookingsModel {

    public String fromLocation;
    public String toLocation;
    public String trainName;
    public String trainNumber;
    public String displayName;
    public String createdAt;
    public int reservedCount;
    public String reservationDate;
    public String reservationStatus;
    public int amount;

    public BookingsModel(String fromLocation, String toLocation, String trainName,
                         String trainNumber, String displayName, String createdAt,
                         int reservedCount, String reservationDate, String reservationStatus, int amount) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.reservedCount = reservedCount;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
        this.amount = amount;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(int reservedCount) {
        this.reservedCount = reservedCount;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
