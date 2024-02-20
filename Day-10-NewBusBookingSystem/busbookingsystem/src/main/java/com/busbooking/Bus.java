package com.busbooking;

class Bus {
   private String busNumber;
   private Route route;
   private int totalSeats;
    int availableSeats;
   private double fare;
   

    public Bus(String busNumber, Route route, int totalSeats,double d) {
        this.busNumber = busNumber;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare=d;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public Route getRoute() {
        return route;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getFare() {
        return fare;
    }
    
}