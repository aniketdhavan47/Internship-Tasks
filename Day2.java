package com.backtracking;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 class Bus {
    private int busNumber;
    private int numberOfSeats;
    private int fare;
    private LocalDate bookedDate;

    public Bus(int busNumber, int numberOfSeats, int fare) {
        this.busNumber = busNumber;
        this.numberOfSeats = numberOfSeats;
        this.fare = fare;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getFare() {
        return fare;
    }

    public boolean isBooked(LocalDate date) {
        return bookedDate != null && bookedDate.equals(date);
    }

    public void book(LocalDate date, int numberOfSeats) {
        this.bookedDate = date;
        this.numberOfSeats -= numberOfSeats;
    }

    public void cancelBooking() {
        this.bookedDate = null;
    }

    public static final java.time.format.DateTimeFormatter DATE_FORMAT =
            java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
}

 class Booking {
    private Bus bus;
    private LocalDate date;
    private int numberOfSeats;
    private int bookingId;

    public Booking(Bus bus, LocalDate date, int numberOfSeats) {
        this.bus = bus;
        this.date = date;
        this.numberOfSeats = numberOfSeats;
        this.bookingId = bus.getBusNumber() * 1000 + bus.getNumberOfSeats() * 100 + numberOfSeats;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Bus getBus() {
        return bus;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
	

public class BusBookingSystem {
    private static List<Bus> buses;
    private static List<Booking> bookings;

    public static void main(String[] args) {
        buses = new ArrayList<>();
        bookings = new ArrayList<>();

        // Add buses
        Bus bus1 = new Bus(1, 50, 100);
        
        
        buses.add(bus1);
       

        // Main menu
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Check availability");
            System.out.println("2. Book bus");
            System.out.println("3. Cancel booking");
            System.out.println("4. Print booking bill");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    checkAvailability();
                    break;
                case 2:
                    bookBus();
                    break;
                case 3:
//                    cancelBooking();
                    break;
                case 4:
//                    printBookingBill();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void checkAvailability() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter date (dd-mm-yyyy): ");
        String dateInput = scanner.next();
        LocalDate date = LocalDate.parse(dateInput, Bus.DATE_FORMAT);

        boolean isAvailable = true;
        for (Bus bus : buses) {
            if (bus.isBooked(date)) {
                isAvailable = false;
                break;
            }
        }

        if (isAvailable) {
            System.out.println("Bus is available for the given date.");
        } else {
            System.out.println("Bus is not available for the given date.");
        }
    }

    private static void bookBus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter date (dd-MM-yyyy): ");
        String dateInput = scanner.next();
        LocalDate date = LocalDate.parse(dateInput, Bus.DATE_FORMAT);

        boolean isBooked = false;
        for (Bus bus : buses) {
            if (!bus.isBooked(date)) {
                System.out.println("Bus number: " + bus.getBusNumber());
                System.out.println("Number of seats: " + bus.getNumberOfSeats());
                System.out.println("Fare: " + bus.getFare());
                
                System.out.print("Enter number of seats to book: ");
                int numberOfSeats = scanner.nextInt();

                if (numberOfSeats <= bus.getNumberOfSeats()) {
                    bus.book(date, numberOfSeats);
                    Booking booking = new Booking(bus, date, numberOfSeats);
                    bookings.add(booking);
                    System.out.println("Bus booked successfully.");
                    isBooked = true;
                    break;
                } else {
                    System.out.println("Invalid number of seats. Please try again.");
                }
            }
        }

        if (!isBooked) {
            System.out.println("Bus is not available for the given date.");
        }
    }
}
    
