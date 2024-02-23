package com.busbooking;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;

import java.time.LocalTime;
import java.util.*;

public class Facilities {

    static ObjectMapper mapper = new ObjectMapper();

    static Scanner scanner = new Scanner(System.in);

    public boolean checkAvailability(String source, String destination, String bookingDate, String bookingTime,
            File busesFile, File file) throws Exception {
        mapper.registerModule(new JavaTimeModule());

        LocalTime time = LocalTime.parse(bookingTime);
        ArrayList<Booking> bookings = mapper.readValue(file, new TypeReference<ArrayList<Booking>>() {
        });

        Map<LocalTime, Bus> busesMap = (Map<LocalTime, Bus>) mapper.readValue(busesFile,
                new TypeReference<HashMap<LocalTime, Bus>>() {
                });

        Route route = new Route(source, destination);

        for (Map.Entry<LocalTime, Bus> entry : busesMap.entrySet()) {
            if (entry.getValue().getRoute().equals(route) && entry.getKey().equals(time)) {
                int availableSeats = getAvailableSeats(busesMap, entry.getValue().getBusNumber(), bookingDate,
                        bookingTime,
                        route, bookings);
                System.out.println("--------------------------------------------------------------");

                System.out.println("Available seats For " + route + " on " + bookingDate + " at " + bookingTime + ": "
                        + availableSeats);

                System.out.println("--------------------------------------------------------------");
                return true;

            }
        }
        throw new BusBookingException("No bus found for the specified route.");
    }

    public boolean bookSeats(String source, String destination, String bookingDate, String bookingTime, int seatsToBook,
            String passengerName, File busesFile, File file) throws Exception {

        Route route = new Route(source, destination);
        mapper.registerModule(new JavaTimeModule());

        ArrayList<Booking> bookings = mapper.readValue(file, new TypeReference<ArrayList<Booking>>() {
        });

        Map<LocalTime, Bus> busesMap = (Map<LocalTime, Bus>) mapper.readValue(busesFile,
                new TypeReference<HashMap<LocalTime, Bus>>() {
                });

        for (Map.Entry<LocalTime, Bus> entry : busesMap.entrySet()) {
            if (entry.getValue().getRoute().equals(route)) {
                int availableSeats = getAvailableSeats(busesMap, entry.getValue().getBusNumber(), bookingDate,
                        bookingTime,
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
                    return true;
                } else {
                    throw new BusBookingException(
                            "Not enough seats available. ");
                }

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

    public boolean printBookingDetails(int id, File file) throws Exception {

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

                return true;
            }
        }

        throw new BusBookingException("Invalid Booking Id");

    }

    public boolean cancelBooking(int id, File busesFile, File bookingsFile) throws Exception {

        mapper.registerModule(new JavaTimeModule());

        Map<LocalTime, Bus> busesMap = (Map<LocalTime, Bus>) mapper.readValue(busesFile,
                new TypeReference<HashMap<LocalTime, Bus>>() {
                });

        ArrayList<Booking> bookings = mapper.readValue(bookingsFile, new TypeReference<ArrayList<Booking>>() {
        });

        for (Booking booking : bookings) {
            if (booking.getBookingId() == id) {
                for (Map.Entry<LocalTime, Bus> entry : busesMap.entrySet()) {
                    if (entry.getValue().getBusNumber().equals(booking.getBusNumber())) {
                        entry.getValue()
                                .setAvailableSeats(entry.getValue().getAvailableSeats() + booking.getBookedSeats());
                        bookings.remove(booking);
                        mapper.writeValue(bookingsFile, bookings);

                        System.out.println("--------------------------------------------------------------");
                        System.out.println("Booking canceled successfully for Id " + id);
                        System.out.println("--------------------------------------------------------------");

                        return true;
                    }
                }

            }
        }

        throw new BusBookingException("Invalid Booking Id");

    }
}
