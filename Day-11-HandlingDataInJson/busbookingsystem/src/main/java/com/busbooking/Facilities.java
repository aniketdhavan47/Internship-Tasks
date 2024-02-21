package com.busbooking;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

class Facilities {

    static ObjectMapper mapper = new ObjectMapper();

    static Scanner scanner = new Scanner(System.in);

    public void checkAvailability(String source, String destination, String bookingDate, String bookingTime,
            Map<LocalTime, Bus> buses, File file)
            throws BusBookingException, StreamReadException, DatabindException, IOException {

        LocalTime time = LocalTime.parse(bookingTime);
        ArrayList<Booking> bookings = mapper.readValue(file, new TypeReference<ArrayList<Booking>>() {});

        Route route = new Route(source, destination);
        
        for (Map.Entry<LocalTime, Bus> entry : buses.entrySet()) {

            if (entry.getValue().getRoute().equals(route) && buses.containsKey(time)) {
                int availableSeats = getAvailableSeats(buses, entry.getValue().getBusNumber(), bookingDate, bookingTime,
                        route, bookings);
                System.out.println("--------------------------------------------------------------");
                System.out.println("Available seats For " + route + " on " + bookingDate + " at " + bookingTime + ": "
                        + availableSeats);
                System.out.println("--------------------------------------------------------------");
                return;
            }
        }

        throw new BusBookingException("No bus found for the specified route.");
    }

    public void bookSeats(String source, String destination, String bookingDate, String bookingTime, int seatsToBook,
            String passengerName, Map<LocalTime, Bus> buses, File file)
            throws BusBookingException, StreamWriteException, DatabindException, IOException {

        Route route = new Route(source, destination);

        ArrayList<Booking> bookings = mapper.readValue(file, new TypeReference<ArrayList<Booking>>() {
        });

        for (Map.Entry<LocalTime, Bus> entry : buses.entrySet()) {
            if (entry.getValue().getRoute().equals(route)) {
                int availableSeats = getAvailableSeats(buses, entry.getValue().getBusNumber(), bookingDate, bookingTime,
                        route, bookings);

                if (seatsToBook <= availableSeats) {

                    int bookingId = (int) Math.round(Math.random() * 10000);
                    double f = entry.getValue().getFare() * seatsToBook;

                    bookings.add(new Booking(bookingId, entry.getValue().getBusNumber(), passengerName, seatsToBook,
                            bookingDate, bookingTime, route, f));
                    entry.getValue().setAvailableSeats(entry.getValue().getAvailableSeats() - seatsToBook);
                    System.out.println("--------------------------------------------------------------");
                    System.out.println(seatsToBook + " seats booked successfully for " + passengerName
                            + "\n Your Booking ID is :" + bookingId);
                    System.out.println("--------------------------------------------------------------");
                    mapper.writeValue(file, bookings);
                } else {
                    throw new BusBookingException(
                            "Not enough seats available. Please try again with a smaller number.");
                }

                return;
            }
        }

        throw new BusBookingException("No bus found for the specified route.");
    }

    private int getAvailableSeats(Map<LocalTime, Bus> buses, String busNumber, String bookingDate, String bookingTime,
            Route route, ArrayList<Booking> bookings) throws BusBookingException {

        int totalSeats = 0;
        int bookedSeats = 0;

        for (Booking booking : bookings) {
            if (booking.getBusNumber().equals(busNumber) &&
                    booking.getBookingDate().equals(bookingDate) &&
                    booking.getBookingTime().equals(bookingTime) &&
                    booking.getRoute().equals(route)) {
                bookedSeats += booking.getBookedSeats();
            }
        }

        for (Map.Entry<LocalTime, Bus> entry : buses.entrySet()) {
            if (entry.getValue().getBusNumber().equals(busNumber)) {
                totalSeats = entry.getValue().getTotalSeats();
                break;
            }
        }

        return totalSeats - bookedSeats;
    }

    public String printBookingDetails(int id, File file) throws StreamReadException, DatabindException, IOException {

        boolean flag = false;
        ArrayList<Booking> bookings = mapper.readValue(file, new TypeReference<ArrayList<Booking>>() {
        });

        for (Booking booking : bookings) {
            if (booking.getBookingId() == id) {
                System.out.println("--------------------------------------------------------------");
                System.out.println("Booking Details:");
                System.out.println("Bus Number: " + booking.getBusNumber() +
                        ", Passenger Name: " + booking.getPassengerName() +
                        ", Booked Seats: " + booking.getBookedSeats() +
                        ", Booking Date: " + booking.getBookingDate() +
                        ", Booking Time: " + booking.getBookingTime() +
                        ", Total Fare: " + booking.getFare() +
                        ", Route: " + booking.getRoute());
                System.out.println("--------------------------------------------------------------");
                flag = true;
                return "Booking details Printed";
            }
        }
        if (flag == false)
            return "Booking Details not Available";
        throw new BusBookingException("Invalid Booking Id");

    }

    public  void cancelBooking(int id, Map<LocalTime, Bus> buses, File bookingsFile) {
        try {

            
            ArrayList<Booking> bookings = mapper.readValue(bookingsFile, new TypeReference<ArrayList<Booking>>() {
            });

            for (Booking booking : bookings) {
                if (booking.getBookingId() == id) {
                    for (Map.Entry<LocalTime, Bus> entry : buses.entrySet()) {
                        if (entry.getValue().getBusNumber().equals(booking.getBusNumber())) {
                            entry.getValue()
                                    .setAvailableSeats(entry.getValue().getAvailableSeats() + booking.getBookedSeats());
                            bookings.remove(booking);
                            break; 
                        }
                    }
                    break;
                }
            }

            
            mapper.writeValue(bookingsFile, bookings);

            System.out.println("--------------------------------------------------------------");
            System.out.println("Booking canceled successfully for Id " + id);
            System.out.println("--------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
