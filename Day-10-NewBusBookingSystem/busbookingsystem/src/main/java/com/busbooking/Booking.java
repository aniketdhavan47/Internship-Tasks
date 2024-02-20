package com.busbooking;

class Booking {
    private int bookingId;
    private String busNumber;
    private String passengerName;
    private int bookedSeats;
    private String bookingDate;
    private String bookingTime;
    private Route route;
    private double fare;

    public Booking(int id, String busNumber, String passengerName, int bookedSeats, String bookingDate,
            String bookingTime, Route route, double f) {
        this.bookingId = id;
        this.busNumber = busNumber;
        this.passengerName = passengerName;
        this.bookedSeats = bookedSeats;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.route = route;
        this.fare = f;
    }

  

    public int getBookingId() {
        return bookingId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getPassengerName() {
        return passengerName;
       
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public Route getRoute() {
        return route;
    }

    public double getFare() {
        return fare;
    }

}
