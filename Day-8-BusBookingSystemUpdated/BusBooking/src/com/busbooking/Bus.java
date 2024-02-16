package com.busbooking;

public class Bus {
    
    private String busno;
    private int noOfSeats;
    private float fare;

    public Bus(String busno, int noOfSeats, float fare) {
        this.busno = busno;
        this.noOfSeats = noOfSeats;
        this.fare = fare;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public String getBusno() {
        return busno;
    }
    public int getNoOfSeats() {
        return noOfSeats;
    }
    public float getFare() {
        return fare;
    }

    @Override
    public String toString() {
        return "Bus [busno = " + busno + ", noOfSeats = " + noOfSeats + ", fare = " + fare + "]\n";
    }
}
