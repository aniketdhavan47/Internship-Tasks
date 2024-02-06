
// There are 10 busses available, and they run on one route from Mumbai to pune, and pune to mumbai vise versa everyday 
// You have to create task to book bus (Right now we will focus on full bus booking)
// Bus will have property like bus number, number of seats and fare

// Add facility to check availability for date
// Add facility to book bus
// Add facility to enter customer details while booking
// Add facility to print bus booking like bellow

// Booking Bill
// ---------------------
// Agency name: xyz travels, Date:
// Bus number: 
// Booked seats:
// Source stop:pune
// destination:mumbai
// ------------------------
// Customer name: Contact:
// ------------------------
// Total Fare:
// -----------------------
 
// Add facility to cancel booking

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

// Bus will have property like bus number, number of seats and fare
class Bus {
    int busNumber;
    int totalSeats;
    int remainingSeats;
    int fare;
    public static final java.time.format.DateTimeFormatter DATE_FORMAT = java.time.format.DateTimeFormatter
            .ofPattern("dd-MM-yyyy");
    ArrayList<HashMap<LocalDate, Integer>> bookingDetails;

    Bus(int bNo, int total, int fare) {
        this.busNumber = bNo;
        this.totalSeats = total;
        this.fare = fare;
        this.remainingSeats = total;
    }

    public int getBusNumber() {
        return this.busNumber;
    }

    public int totalSeats() {
        return this.totalSeats;
    }

    public void setRemainingSeats(int num) {
        this.remainingSeats = num;
    }

    public int getRemainingSeats() {
        return this.remainingSeats;
    }

    public int getFare() {
        return this.fare;
    }

    // Functionalities
    public boolean isBooked(LocalDate d1) {
        for (int i = 0; i < bookingDetails.size(); i++) {
            if (bookingDetails.get(i).get(d1) == 0) {
                return true;
            }
        }
        return false;
    }

}

class Booking {
    Bus bus;
    String source;
    String destination;
    LocalDate date;
    int numberOfSeats;
    String customerName;
    long contactDetails;
    int bookingId;

    // b1,source,destination,date,seats,customerName,contactNumber
    Booking(Bus b1, String source, String destination, LocalDate d1, int seats, String name, long contact) {
        this.bus = b1;
        this.source = source;
        this.destination = destination;
        this.date = d1;
        this.numberOfSeats = seats;
        this.customerName = name;
        this.contactDetails = contact;
        this.bookingId = (int) Math.round(Math.random() * 1000);
        b1.setRemainingSeats(b1.getRemainingSeats() - seats);
        System.out.println("Booking Successful");
    }

    public Bus getBus() {
        return bus;
    }

    public String getSource() {
        return this.source;
    }

    public String getDestination() {
        return this.destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public long getContactDetails() {
        return this.contactDetails;
    }

    public int getBookingId() {
        return this.bookingId;
    }
}

public class BusBooking {
    public static void main(String[] args) {

        // Main Menu
        Bus b1 = new Bus(1001, 50, 100);
        Scanner scanner = new Scanner(System.in);
        ArrayList<Booking> details = new ArrayList<>();
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
                    boolean isFull = checkAvailability(b1);
                    if (isFull) {
                        System.out.println("------------------------------");
                        System.out.println("No Seats Are Avilable");
                        System.out.println("------------------------------");
                    } else {
                        System.out.println("------------------------------");
                        System.out.println(b1.getRemainingSeats() + " seats Are Avilable");
                        System.out.println("------------------------------");
                    }

                    break;
                case 2:
                    Booking book1 = bookBus(b1);
                    if (book1 == null) {
                        System.out.println("------------------------------");
                        System.out.println("Can not Book Seats");
                        System.out.println("------------------------------");
                    } else {
                        System.out.println("------------------------------");
                        System.out.println("Your Booking id is :" + book1.bookingId);
                        System.out.println("------------------------------");
                    }
                    details.add(book1);
                    break;
                case 3:
                    cancelBooking(details);
                    break;
                case 4:
                    printBookingBill(details);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
    // Functionality

    public static boolean checkAvailability(Bus b1) {
        if (b1.getRemainingSeats() == 0)
            return true;
        return false;
    }

    public static Booking bookBus(Bus b1) {
        Booking book1 = null;
        try{

            System.out.println("Enter the Source Stop :");
            Scanner sc = new Scanner(System.in);
            String source = sc.next();
            System.out.println("Enter the Destination Stop:");
            String destination = sc.next();
            System.out.println("Enter the Date in Format (dd-mm-yyyy):");
            String userInput = sc.next();
            LocalDate date = null;

            date = LocalDate.parse(userInput, b1.DATE_FORMAT);

            // Validating Booking Date
            LocalDate currDate = LocalDate.now();
                if (date.isBefore(currDate)) {
                    System.out.println("------------------------------");
                    System.out.println("Invalid Date-Can Not Book Seats For Previous Dates");
                    System.out.println("------------------------------");
                    return null;
                }

            System.out.println("Enter the number of seats want to book");
            int seats = sc.nextInt();
            System.out.println("Enter Customer Name:");
            String customerName = sc.next();
            System.out.println("Enter Contact Number");
            long contactNumber = sc.nextLong();
            
                if (b1.getRemainingSeats() >= seats) {
                    book1 = new Booking(b1, source, destination, date, seats, customerName, contactNumber);
                }
        
        }catch(InputMismatchException ime){
            System.out.println("------------------------------");
            System.out.println("Input MisMatch");
            System.out.println("------------------------------");
        }catch(DateTimeParseException dtpe){
            System.out.println("------------------------------");
            System.out.println("Invald Date Format");
            System.out.println("------------------------------");
        }
      
        return book1;
    }

    public static void cancelBooking(ArrayList<Booking> details) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Booking Id :");
        int id = sc.nextInt();
        boolean isFind = false;
        if (details.size() > 0) {
            for (int i = 0; i < details.size(); i++) {
                try {
                    if (id == details.get(i).getBookingId()) {
                        details.remove(i);
                        isFind = true;
                        System.out.println("Booking Cancelled");
                    }
                } catch (NullPointerException npe) {
                    System.out.println("Null");
                }
            }
        }
        if (!isFind) {
            System.out.println("Booking is Not Avilable");
        }

    }

    // ---------------------
    // Agency name: xyz travels, Date:
    // Bus number:
    // Booked seats:
    // Source stop:pune
    // destination:mumbai
    // ------------------------
    // Customer name: Contact:
    // ------------------------
    // Total Fare:
    // -----------------------
    public static void printBookingBill(ArrayList<Booking> details) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Booking Id");
        int id = sc.nextInt();
        boolean isFind = false;
        if (details.size() > 0) {
            for (int i = 0; i < details.size(); i++) {
                try {
                    if (id == details.get(i).getBookingId()) {
                        Booking book1 = details.get(i);
                        System.out.println("-----------------Booking Details------------");
                        System.out.println("Bus Number :" + book1.getBus().getBusNumber());
                        System.out.println("Booked Seats :" + book1.getNumberOfSeats());
                        System.out.println("Source Stop :" + book1.getSource());
                        System.out.println("Destination Stop :" + book1.getDestination());
                        System.out.println("---------------------------------");
                        System.out.println(
                                "Customer Name :" + book1.getCustomerName() + " Contact:" + book1.getContactDetails());
                        System.out.println("-----------------------------------------------");
                        isFind = true;
                    }
                } catch (NullPointerException npe) {
                   System.out.println("Null");
                }
            }
        }
        if (isFind == false) {
            System.out.println("---------------------------------");
            System.out.println("Booking Not Avilable");
            System.out.println("---------------------------------");
        }
    }

}
