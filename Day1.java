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
