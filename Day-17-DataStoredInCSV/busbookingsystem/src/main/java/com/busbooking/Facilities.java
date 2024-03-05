package com.busbooking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalTime;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import java.io.*;

public class Facilities {

    static ObjectMapper mapper = new ObjectMapper();

    static Scanner scanner = new Scanner(System.in);

    private static final String BUS_FILE_PATH = "bus.csv";
    private static final String BOOKINGS_FILE_PATH = "bookings.csv";

    public Map<String, Bus> getBuses() throws IOException {
        mapper.registerModule(new JavaTimeModule());
        return readBusesFromCSV(BUS_FILE_PATH);
    }

    public ArrayList<Booking> getBookings() throws IOException {
        mapper.registerModule(new JavaTimeModule());
        return readBookingsFromCSV(BOOKINGS_FILE_PATH);
    }

    public ArrayList<Bus> checkAvailability(String source, String destination, String bookingDate, String bookingTime,
            File busesFile, File file) throws Exception {

        LocalTime time = LocalTime.parse(bookingTime);

        Map<String, Bus> busesMap = getBuses();
        ArrayList<Booking> bookings = getBookings();

        Route route = new Route(source, destination);
        boolean flag = false;

        ArrayList<Bus> availableBuses = new ArrayList<>();

        System.out.println("-----------------------------------------------");
        System.out.println("Bus Number \tTime\t Route \t\tR Seats\t    Date");
        for (Map.Entry<String, Bus> entry : busesMap.entrySet()) {
            LocalTime busTime = LocalTime.parse(entry.getValue().getTime());
            if (entry.getValue().getRoute().equals(route) && (time).isBefore(busTime)) {
                int availableSeats = getAvailableSeats(busesMap, entry.getKey(), bookingDate,
                        busTime.toString(),
                        route, bookings);

                System.out
                        .println(entry.getKey() + "\t       " + busTime + "\t" + entry.getValue().getRoute() + "\t  "
                                + availableSeats + " \t  "
                                + bookingDate);
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

        LocalTime time = LocalTime.parse(bookingTime);
        Map<String, Bus> busesMap = getBuses();
        boolean flag = false;

        ArrayList<Booking> bookings = getBookings();

        for (Map.Entry<String, Bus> entry : busesMap.entrySet()) {
            LocalTime busTime = LocalTime.parse(entry.getValue().getTime());
            if (entry.getValue().getRoute().equals(route) && (time.equals(busTime) || (time).isBefore(busTime))) {
                int availableSeats = getAvailableSeats(busesMap, entry.getKey(), bookingDate,
                        busTime.toString(),
                        route, bookings);

                Bus b1 = entry.getValue();
                flag = true;
                if (seatsToBook <= availableSeats) {

                    int bookingId = (int) Math.round(Math.random() * 10000);
                    double f = b1.getFare() * seatsToBook;

                    bookings.add(new Booking(bookingId, entry.getKey(), passengerName, seatsToBook,
                            bookingDate, entry.getValue().getTime(), route, f));
                    b1.setAvailableSeats(b1.getAvailableSeats() - seatsToBook);
                    System.out.println("--------------------------------------------------------------");
                    System.out.println(seatsToBook + " seats booked successfully for " + passengerName
                            + "\n Your Booking ID is :" + bookingId);
                    System.out.println("--------------------------------------------------------------");

                    appendBookingToCSV(new Booking(bookingId, entry.getKey(), passengerName, seatsToBook, bookingDate,
                            entry.getValue().getTime(), route, f));

                    return seatsToBook;

                }

            }
        }
        if (flag) {
            throw new BusBookingException("Not enough seats available. ");
        }

        throw new BusBookingException("No bus found for the specified route.");
    }

    private int getAvailableSeats(Map<String, Bus> buses, String busNumber, String bookingDate, String bookingTime,
            Route route, ArrayList<Booking> bookings) throws BusBookingException {

        int totalSeats = 0;
        int bookedSeats = 0;
        if (bookings != null) {

            for (Booking booking : bookings) {
                if (booking.getBusNumber().equals(busNumber) &&
                        booking.getBookingDate().equals(bookingDate) &&
                        booking.getBookingTime().equals(bookingTime) &&
                        booking.getRoute().equals(route)) {
                    bookedSeats += booking.getBookedSeats();
                }
            }
        }

        for (Map.Entry<String, Bus> entry : buses.entrySet()) {
            if (entry.getKey().equals(busNumber)) {
                totalSeats = entry.getValue().getTotalSeats();
                break;
            }
        }

        return totalSeats - bookedSeats;
    }

    public Booking printBookingDetails(int id) throws Exception {

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

        Map<String, Bus> busesMap = getBuses();
        ArrayList<Booking> bookings = getBookings();

        for (Booking booking : bookings) {
            if (booking.getBookingId() == id) {
                for (Map.Entry<String, Bus> entry : busesMap.entrySet()) {
                    if (entry.getKey().equals(booking.getBusNumber())) {
                        entry.getValue()
                                .setAvailableSeats(entry.getValue().getAvailableSeats() + booking.getBookedSeats());
                        bookings.remove(booking);

                        WriteBookingToCsv(booking);
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

    private Map<String, Bus> readBusesFromCSV(String filePath) throws IOException {
        Map<String, Bus> busesMap = new HashMap<>();

        try (Reader reader = new FileReader(filePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {

                String busNumber = csvRecord.get(0);
                String time = csvRecord.get(1);
                Route route = new Route(csvRecord.get(2), csvRecord.get(3));
                int totalSeats = Integer.parseInt(csvRecord.get(4));
                double fare = Double.parseDouble(csvRecord.get(6));

                Bus bus = new Bus(time, route, totalSeats, fare);
                busesMap.put(busNumber, bus);
            }
        }
        return busesMap;
    }

    private ArrayList<Booking> readBookingsFromCSV(String filePath) throws IOException {
        ArrayList<Booking> bookings = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            csvParser.forEach(csvRecord -> {

                int bookingId = Integer.parseInt(csvRecord.get(0));
                String busNumber = csvRecord.get(1);
                String passengerName = csvRecord.get(2);
                int bookedSeats = Integer.parseInt(csvRecord.get(3));
                String bookingDate = csvRecord.get(4);
                String bookingTime = csvRecord.get(5);
                Route route = new Route(csvRecord.get(6), csvRecord.get(7));
                double fare = Double.parseDouble(csvRecord.get(8));

                Booking booking = new Booking(bookingId, busNumber, passengerName, bookedSeats, bookingDate,
                        bookingTime, route, fare);
                bookings.add(booking);
            });
        }
        return bookings;
    }

    private void WriteBookingToCsv(Booking booking) throws IOException {
        try (Writer writer = new FileWriter(BOOKINGS_FILE_PATH);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            csvPrinter.printRecord(booking.getBookingId(), booking.getBusNumber(), booking.getPassengerName(),
                    booking.getBookedSeats(), booking.getBookingDate(), booking.getBookingTime(),
                    booking.getRoute().getSource(), booking.getRoute().getDestination(), booking.getFare());

        }
    }

    private void appendBookingToCSV(Booking booking) throws IOException {
        try (Writer writer = new FileWriter(BOOKINGS_FILE_PATH, true);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            csvPrinter.printRecord(booking.getBookingId(), booking.getBusNumber(), booking.getPassengerName(),
                    booking.getBookedSeats(), booking.getBookingDate(), booking.getBookingTime(),
                    booking.getRoute().getSource(), booking.getRoute().getDestination(), booking.getFare());

        }
    }

}
