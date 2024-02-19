package com.busbooking;

import java.time.LocalDate;
import java.time.LocalTime;


public class BillingDetails {
    String ajencyName;
    LocalDate date;
    String busNumber;
    int bookedSeats;
    String source;
    String destination;
    String customerName;
    String contact;
    float totalFare;
    LocalTime time;

    public BillingDetails(String ajencyName, LocalDate date, String busNumber, int bookedSeats, String source,
            String destination, String customerName, String contact, float totalFare, LocalTime time) {
        this.ajencyName = ajencyName;
        this.date = date;
        this.busNumber = busNumber;
        this.bookedSeats = bookedSeats;
        this.source = source;
        this.destination = destination;
        this.customerName = customerName;
        this.contact = contact;
        this.totalFare = totalFare;
        this.time = time;
    }
    
    public BillingDetails() {}

    public String getAjencyName() {
        
        return ajencyName;
    }

    public BillingDetails setAjencyName(String ajencyName) {
       
        this.ajencyName = ajencyName;
        return this;
    }

    public LocalDate getDate() {
       
        return date;
    }

    public BillingDetails setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getBusNumber() {
        
        return busNumber;
    }

    public BillingDetails setBusNumber(String busNumber) {
        this.busNumber = busNumber;
        return this;
    }

    public int getBookedSeats() {
        
        return bookedSeats;
    }

    public BillingDetails setBookedSeats(int bookedSeats) {
        
        this.bookedSeats = bookedSeats;
        return this;
    }

    public String getSource() {
        
        return source;
    }

    public BillingDetails setSource(String source) {
        
        this.source = source;
        return this;
    }

    public String getDestination() {
        
        return destination;
    }

    public BillingDetails setDestination(String destination) {
        
        this.destination = destination;
        return this;
    }

    public String getCustomerName() {
        
        return customerName;
    }

    public BillingDetails setCustomerName(String customerName) {
        
        this.customerName = customerName;
        return this;
    }

    public String getContact() {
        
        return contact;
    }

    public BillingDetails setContact(String contact2) {
        
        this.contact = contact2;
        return this;
    }

    public float getTotalFare() {
        
        return totalFare;
    }

    public BillingDetails setTotalFare(float totalFare) {
       
        this.totalFare = totalFare;
        return this;
    }

    public LocalTime getTime() {
        return time;
    }

    public BillingDetails setTime(LocalTime time) {
        this.time = time;
        return this;
    }
}
