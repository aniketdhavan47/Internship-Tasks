package com.busbooking;

public class Bus {
 
   private String time;
   private Route route;
   private int totalSeats;
    int availableSeats;
   private double fare;
   

    public Bus(String time, Route route, int totalSeats,double d) {
        this.time=time;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare=d;
    }

    public Bus() {
    }

    public String getTime() {
        return time;
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
    @Override
    public String toString() {
        return "Bus [time=" + time + ", route=" + route + ", totalSeats=" + totalSeats + ", availableSeats="
                + availableSeats + ", fare=" + fare + "]";
    }
    
}