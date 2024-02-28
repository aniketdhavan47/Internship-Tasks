package com.busbooking;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class Facilities {

    static ObjectMapper mapper = new ObjectMapper();

    static Scanner scanner = new Scanner(System.in);

    public static Map<LocalTime, Bus> getBuses() throws StreamReadException, DatabindException, IOException {
        mapper.registerModule(new JavaTimeModule());
        return (Map<LocalTime, Bus>) mapper.readValue(new File("bus.json"),
                new TypeReference<HashMap<LocalTime, Bus>>() {
                });
    }

    public static ArrayList<Booking> getBookings() throws StreamReadException, DatabindException, IOException {
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(new File("bookings.json"), new TypeReference<ArrayList<Booking>>() {
        });
    }

    public ArrayList<Bus> checkAvailability(String source, String destination, String bookingDate, String bookingTime,
            File busesFile, File file) throws Exception {

        LocalTime time = LocalTime.parse(bookingTime);

        Map<LocalTime, Bus> busesMap = getBuses();
        ArrayList<Booking> bookings = getBookings();

        Route route = new Route(source, destination);
        boolean flag = false;

        ArrayList<Bus> availableBuses = new ArrayList<>();

        System.out.println("-----------------------------------------------");
        System.out.println("Time\t Route \t\tR Seats\t    Date\t  Status");
        for (Map.Entry<LocalTime, Bus> entry : busesMap.entrySet()) {
            if (entry.getValue().getRoute().equals(route) && (time).isBefore(entry.getKey())) {
                int availableSeats = getAvailableSeats(busesMap, entry.getValue().getBusNumber(), bookingDate,
                        entry.getKey().toString(),
                        route, bookings);

                System.out
                        .println(entry.getKey() + "\t" + entry.getValue().getRoute() + "\t  " + availableSeats + " \t  "
                                + bookingDate + "\t " + "Available");
                availableBuses.add(entry.getValue());

                flag = true;

            }

        }
        System.out.println("-----------------------------------------------");
        if (flag) {
            return availableBuses;
        }
        throw new BusBookingException("No bus found for the specified route.");
    }

    public int bookSeats(String source, String destination, String bookingDate, String bookingTime, int seatsToBook,
            String passengerName, File busesFile, File file) throws Exception {

        Route route = new Route(source, destination);

        LocalTime bookTime = LocalTime.parse(bookingTime);
        Map<LocalTime, Bus> busesMap = getBuses();
        ArrayList<Booking> bookings = getBookings();

        for (Map.Entry<LocalTime, Bus> entry : busesMap.entrySet()) {
            Bus b1 = entry.getValue();
            if (b1.getRoute().equals(route) && (bookTime.equals(entry.getKey()) || bookTime.isBefore(entry.getKey()))) {
                int availableSeats = getAvailableSeats(busesMap, b1.getBusNumber(), bookingDate,
                        entry.getKey().toString(),
                        route, bookings);

                if (seatsToBook <= availableSeats) {

                    int bookingId = (int) Math.round(Math.random() * 10000);
                    double f = b1.getFare() * seatsToBook;

                    bookings.add(new Booking(bookingId, b1.getBusNumber(), passengerName, seatsToBook,
                            bookingDate, entry.getKey().toString(), route, f));
                    b1.setAvailableSeats(b1.getAvailableSeats() - seatsToBook);
                    System.out.println("--------------------------------------------------------------");
                    System.out.println(seatsToBook + " seats booked successfully for " + passengerName
                            + "\n Your Booking ID is :" + bookingId);
                    System.out.println("--------------------------------------------------------------");
                    mapper.writeValue(file, bookings);

                    return seatsToBook;

                }

            } else {
                throw new BusBookingException("Not enough seats available. ");
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

    public Booking printBookingDetails(int id, File file) throws Exception {

        ArrayList<Booking> bookings = getBookings();

        for (Booking booking : bookings) {
            if (booking.getBookingId() == id) {
               System.out.println(booking);
                return booking;
            }
        }

        throw new BusBookingException("Invalid Booking Id");

    }

    public int cancelBooking(int id, File busesFile, File bookingsFile) throws Exception {

        Map<LocalTime, Bus> busesMap = getBuses();
        ArrayList<Booking> bookings = getBookings();

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

                        return booking.getBookingId();
                    }
                }

            }
        }

        throw new BusBookingException("Invalid Booking Id");

    }
}
