package com.busbooking;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Map<LocalTime, Bus> timeAndBus = new HashMap<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    private static void addBuses() {
        // Add some sample buses
        timeAndBus.put(LocalTime.of(8, 0), new Bus("Bus001", new Route("mumbai", "pune"), 30, 120.520));
        timeAndBus.put(LocalTime.of(10, 0), new Bus("Bus002", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(12, 0), new Bus("Bus003", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(14, 0), new Bus("Bus004", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(16, 0), new Bus("Bus005", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(18, 0), new Bus("Bus006", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(20, 0), new Bus("Bus007", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(22, 0), new Bus("Bus008", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(1, 0), new Bus("Bus0010", new Route("mumbai", "pune"), 20, 120.5));
        timeAndBus.put(LocalTime.of(3, 0), new Bus("Bus009", new Route("mumbai", "pune"), 20, 120.5));

        timeAndBus.put(LocalTime.of(7, 0), new Bus("Bus011", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(9, 0), new Bus("Bus0012", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(11, 0), new Bus("Bus0013", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(13, 0), new Bus("Bus0014", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(15, 0), new Bus("Bus0015", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(17, 0), new Bus("Bus0016", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(19, 0), new Bus("Bus0017", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(21, 0), new Bus("Bus0018", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(23, 0), new Bus("Bus00119", new Route("pune", "mumbai"), 30, 120.5));
        timeAndBus.put(LocalTime.of(1, 0), new Bus("Bus00119", new Route("pune", "mumbai"), 30, 120.5));
    }

    public static void main(String[] args) {

        addBuses();
        ObjectMapper mapper =new ObjectMapper();

        File busesFile = new File("bus.json");
        try {
            mapper.writeValue(busesFile, timeAndBus);
        } catch (IOException e) {
             System.out.println(e.getMessage()); // TODO: handle exception
        }

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
                        LocalTime localTime = LocalTime.parse(bookingTime);
                        if (!(localDate.isBefore(LocalDate.now())) && localTime.isAfter(LocalTime.now())) {
                            System.out.println(f1.checkAvailability(source, destination, bookingDate, bookingTime,busesFile, new File("bookings.json")));
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
                        LocalTime localTime = LocalTime.parse(bookingTime);
                        if (!localDate.isBefore(LocalDate.now()) && localTime.isAfter(LocalTime.now())) {
                            f1.bookSeats(source, destination, bookingDate, bookingTime, seatsToBook, passengerName,
                                    busesFile, new File("bookings.json"));
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
                        f1.printBookingDetails(id,  new File("bookings.json"));
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
                        f1.cancelBooking(id, busesFile,  new File("bookings.json"));
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
