package com.example.ticketnow.Model;

import java.util.Date;

public class ReserveHistoryModel {

    public String displayName;
    public String createdAt;
    public int reservedCount;
    public String reservationDate;
    public String reservationStatus;
    public int amount;

    public ReserveHistoryModel(String displayName, String createdAt, int reservedCount, String reservationDate, String reservationStatus, int amount) {
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.reservedCount = reservedCount;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
        this.amount = amount;
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
