import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Route {
    String source;
    String destination;

    public Route(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    // Override equals and hashCode for proper comparison and usage in collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Route route = (Route) obj;
        return source.equals(route.source) && destination.equals(route.destination);
    }

    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode();
    }

    @Override
    public String toString() {
        return source + " to " + destination;
    }
}

class Bus {
    String busNumber;
    Route route;
    int totalSeats;
    int availableSeats;

    public Bus(String busNumber, Route route, int totalSeats) {
        this.busNumber = busNumber;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }
}

class Booking {
    int bookingId;
    String busNumber;
    String passengerName;
    int bookedSeats;
    String bookingDate;
    String bookingTime;
    Route route;

    public Booking(int id,String busNumber, String passengerName, int bookedSeats, String bookingDate, String bookingTime, Route route) {
       this.bookingId=id;
        this.busNumber = busNumber;
        this.passengerName = passengerName;
        this.bookedSeats = bookedSeats;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.route = route;
    }
}

public class BusBookingApp {
    static ArrayList<Bus> buses = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        initializeBuses();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Check availability");
            System.out.println("2. Book seats");
            System.out.println("3. Print booking details");
            System.out.println("4. Cancel booking");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    checkAvailability(scanner);
                    break;
                case 2:
                    bookSeats(scanner);
                    break;
                case 3:
                    printBookingDetails(scanner);
                    break;
                case 4:
                    cancelBooking(scanner);
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

    private static void initializeBuses() {
        // Add some sample buses
        buses.add(new Bus("Bus001", new Route("mumbai", "pune"), 20));
        buses.add(new Bus("Bus002", new Route("pune", "mumbai"), 30));
    }

    private static void checkAvailability(Scanner scanner) {
        System.out.print("Enter source: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String bookingDate = scanner.nextLine();
        System.out.print("Enter booking time (HH:mm): ");
        String bookingTime = scanner.nextLine();

        Route route = new Route(source, destination);

        for (Bus bus : buses) {
            if (bus.route.equals(route)) {
                int availableSeats = getAvailableSeats(bus.busNumber, bookingDate, bookingTime, route);
                System.out.println("Available seats on " + route + " on " + bookingDate + " at " + bookingTime + ": " + availableSeats);
                return;
            }
        }

        System.out.println("No bus found for the specified route.");
    }

    private static void bookSeats(Scanner scanner) {
        System.out.print("Enter source: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String bookingDate = scanner.nextLine();
        System.out.print("Enter booking time (HH:mm): ");
        String bookingTime = scanner.nextLine();

        Route route = new Route(source, destination);

        for (Bus bus : buses) {
            if (bus.route.equals(route)) {
                int availableSeats = getAvailableSeats(bus.busNumber, bookingDate, bookingTime, route);
                System.out.println("Available seats on " + route + " on " + bookingDate + " at " + bookingTime + ": " + availableSeats);

                System.out.print("Enter number of seats to book: ");
                int seatsToBook = scanner.nextInt();

                if (seatsToBook <= availableSeats) {
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.next();
                    int bookingId = (int) Math.round(Math.random() * 10000);

                    bookings.add(new Booking(bookingId,bus.busNumber, passengerName, seatsToBook, bookingDate, bookingTime, route));
                    bus.availableSeats -= seatsToBook;

                    System.out.println(seatsToBook + " seats booked successfully for " + passengerName+"\n Your Booking ID is :"+bookingId);
                    
                } else {
                    System.out.println("Not enough seats available. Please try again with a smaller number.");
                }

                return;
            }
        }

        System.out.println("No bus found for the specified route.");
    }

    private static int getAvailableSeats(String busNumber, String bookingDate, String bookingTime, Route route) {
        int totalSeats = 0;
        int bookedSeats = 0;

        for (Booking booking : bookings) {
            if (booking.busNumber.equals(busNumber) &&
                booking.bookingDate.equals(bookingDate) &&
                booking.bookingTime.equals(bookingTime) &&
                booking.route.equals(route)) {
                bookedSeats += booking.bookedSeats;
            }
        }

        for (Bus bus : buses) {
            if (bus.busNumber.equals(busNumber)) {
                totalSeats = bus.totalSeats;
                break;
            }
        }

        return totalSeats - bookedSeats;
    }

    private static void printBookingDetails(Scanner sc) {
        System.out.println("Enter the Booking Id:");
        int id=sc.nextInt();
        System.out.println("Booking Details:");
        for (Booking booking : bookings) {
           if(booking.bookingId==id){
            System.out.println("Bus Number: " + booking.busNumber +
            ", Passenger Name: " + booking.passengerName +
            ", Booked Seats: " + booking.bookedSeats +
            ", Booking Date: " + booking.bookingDate +
            ", Booking Time: " + booking.bookingTime +
            ", Route: " + booking.route);
           }
        }
        
    }

    private static void cancelBooking(Scanner scanner) {
        System.out.print("Enter booking  Id: ");
        int id = scanner.nextInt();

        for (Booking booking : bookings) {
            if (booking.bookingId==id) {
                for (Bus bus : buses) {
                    if (bus.busNumber.equals(booking.busNumber)) {
                        bus.availableSeats += booking.bookedSeats;
                        bookings.remove(booking);
                        System.out.println("Booking canceled successfully for " + id);
                        return;
                    }
                }
            }
        }

        System.out.println("No booking found for passenger " + id);
    }
}
