package com.busbooking;

import java.io.File;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Facilities f1 = new Facilities();

        while (true) {
            System.out.println("1. Check availability");
            System.out.println("2. Book seats");
            System.out.println("3. Print booking details");
            System.out.println("4. Cancel booking");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.println("Enter source: ");
                        String source = scanner.nextLine();

                        System.out.println("Enter destination: ");
                        String destination = scanner.nextLine();

                        System.out.println("Enter booking date (YYYY-MM-DD): ");
                        String bookingDate = scanner.nextLine();
                        LocalDate localDate = LocalDate.parse(bookingDate);

                        System.out.println("Enter booking time (HH:mm): ");
                        String bookingTime = scanner.nextLine();
                        LocalTime localTime=LocalTime.parse(bookingTime);
                    

                        if ((localDate.equals(LocalDate.now())&& localTime.isAfter(LocalTime.now()))||(localDate.isAfter(LocalDate.now()))) {
                            f1.checkAvailability(source, destination, bookingDate, bookingTime,
                                    new File("bus.json"), new File("bookings.json"));
                        } else {
                            throw new BusBookingException("Can Not check availability for given date and time");
                        }

                    } catch (Exception e) {
                        System.out.println("--------------------------------------------------------------");
                        System.out.println(e.getMessage());
                        System.out.println("--------------------------------------------------------------");
                    }
                    break;

                case 2:
                    try {

                        System.out.println("Enter source: ");
                        String source = scanner.nextLine();
                        System.out.println("Enter destination: ");
                        String destination = scanner.nextLine();
                        System.out.println("Enter booking date (YYYY-MM-DD): ");
                        String bookingDate = scanner.nextLine();
                        System.out.println("Enter booking time (HH:mm): ");
                        String bookingTime = scanner.nextLine();
                        System.out.print("Enter number of seats to book: ");
                        int seatsToBook = scanner.nextInt();

                        if (seatsToBook <= 0) {
                            throw new BusBookingException("Invalid Number of Seats");
                        }

                        System.out.print("Enter passenger name: ");
                        String passengerName = scanner.next();
                        LocalDate localDate = LocalDate.parse(bookingDate);
                        
                        if (!localDate.isBefore(LocalDate.now())) {
                            f1.bookSeats(source, destination, bookingDate, bookingTime, seatsToBook, passengerName,
                                    new File("bus.json"), new File("bookings.json"));
                        } else {
                            throw new BusBookingException("Can Not Book Seats for given date and time");
                        }

                    } catch (Exception e) {
                        System.out.println("--------------------------------------------------------------");
                        System.out.println(e.getMessage());
                        System.out.println("--------------------------------------------------------------");
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Enter the Booking Id:");
                        int id = scanner.nextInt();
                        f1.printBookingDetails(id, new File("bookings.json"));
                    } catch (Exception e) {
                        System.out.println("--------------------------------------------------------------");
                        System.out.println(e.getMessage());
                        System.out.println("--------------------------------------------------------------");
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Enter booking  Id: ");
                        int id = scanner.nextInt();
                        f1.cancelBooking(id, new File("bus.json"), new File("bookings.json"));
                    } catch (Exception e) {
                        System.out.println("--------------------------------------------------------------");
                        System.out.println(e.getMessage());
                        System.out.println("--------------------------------------------------------------");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the application. Thank you!");
                    System.exit(0);
                    break;
                default:
                System.out.println("Invalid choice. Please try again.");
            }

        }

    }
}
