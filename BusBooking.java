/* 
There are 10 busses available, and they run on one route from Mumbai to pune, and pune to mumbai vise versa everyday 
You have to create task to book bus (Right now we will focus on full bus booking)
Bus will have property like bus number, number of seats and fare

Add facility to check availability for date
Add facility to book bus
Add facility to enter customer details while booking
Add facility to print bus booking like bellow

Booking Bill
---------------------
Agency name: xyz travels, Date:
Bus number: 
Booked seats:
Source stop:pune
destination:mumbai
------------------------
Customer name: Contact:
------------------------
Total Fare:
-----------------------

Add facility to cancel booking

*/

import java.util.*;

class Bus{
    String busNumber;
    int numberOfSeats;
    boolean isAvialable[]=new boolean[numberOfSeats];
    double fare;
    String sourceStop;
    String destination;
    Bus(String busNo,int noOfSeats,double fare,String source,String destination){
        this.busNumber=busNo;
        this.numberOfSeats=noOfSeats;
        this.fare=fare;
        this.sourceStop=source;
        this.destination=destination;
    }
     
}

class CustomerDetails{
    ArrayList<Integer> bookedSeats;
    String source;
    String destination;
    String customerName;
    long contactNumber;

    CustomerDetails(ArrayList<Integer> seats,String source,String destination,String name,long contact){
        this.bookedSeats=seats;
        this.source=source;
        this.destination=destination;
        this.customerName=name;
        this.contactNumber=contact;
    }
    public void showDetails(){
        System.out.print("Booked Seats:");
        for(int i=0;i<bookedSeats.size();i++){
            System.out.print(bookedSeats.get(i)+",");
        }
        System.out.println();
        System.out.println("Source Stop :"+this.source);
        System.out.println("Destination : "+this.destination );
        System.out.println("Customer Name : "+this.customerName);
        System.out.println("Customer Contact Number : "+this.contactNumber);

        
    }
}

public class BusBooking{
    public static double claculateFare(int noOfSeat,double fare){
        return noOfSeat*fare;
    }

    public static void showDetails(CustomerDetails c1,Bus b1){
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println();
       System.out.println(b1.busNumber);
       System.out.print("Booked Seats:");
       for(int i=0;i<c1.bookedSeats.size();i++){
           System.out.print(c1.bookedSeats.get(i)+",");
       }
       System.out.println();
       System.out.println("Source Stop :"+c1.source);
       System.out.println("Destination : "+c1.destination );
       System.out.println("Fare : "+claculateFare(c1.bookedSeats.size(), 100));
       System.out.println("Customer Name : "+c1.customerName);
       System.out.println("Customer Contact Number : "+c1.contactNumber);
    }
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);

        System.out.print("Enter Source Stop:");
        String source=sc.next();

        System.out.print("Enter Destination Stop:");
        String destination=sc.next();

        System.out.print("Count Of Seats Want to Book:");
        int seatCnt=sc.nextInt();

        System.out.print("Enter Your Name:");
        String customerName=sc.next();

        System.out.print("Enter your Contact Number:");
        long contactNumber=sc.nextLong();

        ArrayList<Integer> seats=new ArrayList<>();
        for(int i=0;i<seatCnt;i++){
            seats.add(i);
        }
        CustomerDetails c1=new CustomerDetails(seats, source, destination, customerName, contactNumber);
        Bus b1=new Bus(customerName, seatCnt, contactNumber, source, destination);

        showDetails(c1, b1);


    }
}
