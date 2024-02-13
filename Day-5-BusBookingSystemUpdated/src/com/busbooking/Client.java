package com.busbooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

enum Route1{
    pune,mumbai
}
enum Route2{
    mumbai,pune
}
public class Client  {
    
    static Map<LocalTime, Bus> mumbaiTOpuneTime = new LinkedHashMap<>();
    static Map<LocalTime, Bus> punetoMumbaiTime = new LinkedHashMap<>();
    
    static Scanner sc = new Scanner(System.in);

    private static void addBuses() {
        Bus bus1 = new Bus("MH 12 A 4563", 50, 150.00f);
        Bus bus2 = new Bus("MH 12 B 9873", 40, 180.00f);
        Bus bus3 = new Bus("MH 12 C 8765", 60, 120.00f);
        Bus bus4 = new Bus("MH 12 D 3458", 50, 150.00f);
        Bus bus5 = new Bus("MH 12 E 2323", 40, 180.00f);
        Bus bus6 = new Bus("MH 12 F 3945", 60, 120.00f);
        Bus bus7 = new Bus("MH 12 G 2354", 50, 150.00f);
        Bus bus8 = new Bus("MH 12 H 7345", 40, 180.00f);
        Bus bus9 = new Bus("MH 12 I 8234", 60, 120.00f);
        Bus bus10 = new Bus("MH 12 J 6342", 60, 120.00f);

        mumbaiTOpuneTime.put(LocalTime.of(8, 0), bus1);
        mumbaiTOpuneTime.put(LocalTime.of(10, 0), bus2);
        mumbaiTOpuneTime.put(LocalTime.of(12, 0), bus3);
        mumbaiTOpuneTime.put(LocalTime.of(14, 0), bus4);
        mumbaiTOpuneTime.put(LocalTime.of(16, 0), bus5);
        mumbaiTOpuneTime.put(LocalTime.of(18, 0), bus6);
        mumbaiTOpuneTime.put(LocalTime.of(20, 0), bus7);
        mumbaiTOpuneTime.put(LocalTime.of(22, 0), bus8);
        mumbaiTOpuneTime.put(LocalTime.of(0, 0), bus9);
        mumbaiTOpuneTime.put(LocalTime.of(2, 0), bus10);

        punetoMumbaiTime.put(LocalTime.of(7, 0),  bus10);
        punetoMumbaiTime.put(LocalTime.of(9, 0),  bus9);
        punetoMumbaiTime.put(LocalTime.of(11, 0), bus8);
        punetoMumbaiTime.put(LocalTime.of(13, 0), bus7);
        punetoMumbaiTime.put(LocalTime.of(15, 0), bus6);
        punetoMumbaiTime.put(LocalTime.of(17, 0), bus5);
        punetoMumbaiTime.put(LocalTime.of(19, 0), bus4);
        punetoMumbaiTime.put(LocalTime.of(21, 0), bus3);
        punetoMumbaiTime.put(LocalTime.of(23, 0), bus2);
        punetoMumbaiTime.put(LocalTime.of(1, 0),  bus1);
      
    }

        public static boolean isValidSource(String source,String destination){
            if(
                (source.toLowerCase().equals("pune") && destination.toLowerCase().equals("mumbai"))
                ||(source.toLowerCase().equals("pune") && destination.toLowerCase().equals("mumbai"))
                ){
                    return true;
                }
                return false;
        }

    public static void main( String[] args ) {
         
        Facilities facilities = new Facilities();
        addBuses();
        Map<LocalDate, Map<LocalTime, Bus>> bookingDate = new HashMap<>();
        Map<Integer, BillingDetails> billingDetails = new HashMap<>();
       
       
       
        char ch;
        do {
            System.out.println("************** MENU **************");
            System.out.println("1. Check Availability for Date.");
            System.out.println("2. Book Bus.");
            System.out.println("3. Print Bill");
            System.out.println("4. Booking Cancel");
            System.out.println("5. Exit");
            System.out.println("----------------------------------");

            System.out.println("Enter your choice : ");
            int choice=5;
            
            try {
                  
              choice = sc.nextInt();
            } catch(InputMismatchException e) {
            
                System.out.println("Input Mismatch");
            }

            switch (choice) {
                
                case 1:{
                        System.out.println("Enter Date : ");
                        String date = sc.next();


                        try {

                            
                            LocalDate localDate = LocalDate.parse(date);
                            LocalDate todaysDate=LocalDate.now();
                             
                            if(!localDate.isBefore(todaysDate)){
                                System.out.println("Enter Source : ");
                                String source = sc.next();

                                System.out.println("Enter Destination : ");
                                String dest = sc.next();

                                if(Route2.valueOf(source)!=null  && Route2.valueOf(dest)!=null) {
                                    facilities.checkBusAvailability(localDate, bookingDate, mumbaiTOpuneTime);
                                } else if(Route1.valueOf(source)!=null  && Route1.valueOf(dest)!=null) {

                                    facilities.checkBusAvailability(localDate, bookingDate, punetoMumbaiTime);
                                } else {

                                    System.out.println("Source and destination not found !!!");
                                }
                        }else{
                            System.out.println("Can Not Check Availability For Previous Date");
                        }
                        } catch(DateTimeParseException e) {

                            System.out.println("Enter Date (like [YYYY-MM-DD])");
                        }
                    }
                    break;

                case 2 :{
                        try {
                            System.out.println("Enter Date : ");
                            String date = sc.next();

                            LocalDate localDate = LocalDate.parse(date);
                            LocalDate todaysDate=LocalDate.now();
                        
                            if(!localDate.isBefore(todaysDate)){
                            System.out.println("Enter Source : ");
                            String source = sc.next();

                            System.out.println("Enter Destination : ");
                            String dest = sc.next();

                            if(Route2.valueOf(source)!=null  && Route2.valueOf(dest)!=null) {
                                facilities.booking(localDate, mumbaiTOpuneTime, billingDetails, source, dest);
                            } else if(Route1.valueOf(source)!=null  && Route1.valueOf(dest)!=null) {

                                facilities.booking(localDate, punetoMumbaiTime, billingDetails, source, dest);
                            } else {

                                System.out.println("Source and destination not found !!!");
                            }
                        }else{
                            System.out.println("Can Not Book Seats For Provided Date");
                        }

                        } catch (DateTimeParseException e) {
                            System.out.println("Invallid date");
                        }
                    }
                    break;
                
                case 3:{
                        System.out.println("Enter Billing Id : ");
                        int billingId = sc.nextInt();
                        facilities.printBill(billingId, billingDetails);
                    }
                    break;
                
                case 4:{
                        System.out.println("Enter Billing Id : ");
                        int billingId = sc.nextInt();
                        if(billingDetails.containsKey(billingId)) {
                            BillingDetails bDetails = billingDetails.get(billingId);
                            String source = bDetails.getSource();
                            String dest = bDetails.getDestination();
                            if(Route2.valueOf(source)!=null  && Route2.valueOf(dest)!=null) {

                                facilities.bookingCancel(billingId, billingDetails, mumbaiTOpuneTime);
                            } else if(Route1.valueOf(source)!=null  && Route1.valueOf(dest)!=null) {
        
                                facilities.bookingCancel(billingId, billingDetails, punetoMumbaiTime);
                            }
                        
                        } else {

                            System.out.println("Booking Not Found");
                        }
                    }
                    break;
                    case 5:{
                        System.out.println("Exiting");
                        return;
                    }
                    
             
            }
            System.out.println("Do you want to continue ? : ");
            ch = sc.next().charAt(0);
            
        } while(ch == 'Y' || ch == 'y');
    }
}
