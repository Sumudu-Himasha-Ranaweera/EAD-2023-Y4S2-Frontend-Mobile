package com.example.ticketnow.Model;

public class ScheduleModel {

    private String trainName;
    private String trainNumber;
    private String status;
    private int ticketPrice;

    public ScheduleModel(String trainName, String trainNumber, String status, int ticketPrice) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.status = status;
        this.ticketPrice = ticketPrice;
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
}
