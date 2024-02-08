package com.busbooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class App  {
    
    static Map<LocalTime, Bus> timeAndBus_MtoP_Map = new LinkedHashMap<>();
    static Map<LocalTime, Bus> timeAndBus_PtoM_Map = new LinkedHashMap<>();
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

        timeAndBus_MtoP_Map.put(LocalTime.of(8, 0), bus1);
        timeAndBus_MtoP_Map.put(LocalTime.of(10, 0), bus2);
        timeAndBus_MtoP_Map.put(LocalTime.of(12, 0), bus3);
        timeAndBus_MtoP_Map.put(LocalTime.of(14, 0), bus4);
        timeAndBus_MtoP_Map.put(LocalTime.of(16, 0), bus5);
        timeAndBus_MtoP_Map.put(LocalTime.of(18, 0), bus6);
        timeAndBus_MtoP_Map.put(LocalTime.of(20, 0), bus7);
        timeAndBus_MtoP_Map.put(LocalTime.of(22, 0), bus8);
        timeAndBus_MtoP_Map.put(LocalTime.of(0, 0), bus9);
        timeAndBus_MtoP_Map.put(LocalTime.of(2, 0), bus10);

        timeAndBus_PtoM_Map.put(LocalTime.of(7, 0),  bus10);
        timeAndBus_PtoM_Map.put(LocalTime.of(9, 0),  bus9);
        timeAndBus_PtoM_Map.put(LocalTime.of(11, 0), bus8);
        timeAndBus_PtoM_Map.put(LocalTime.of(13, 0), bus7);
        timeAndBus_PtoM_Map.put(LocalTime.of(15, 0), bus6);
        timeAndBus_PtoM_Map.put(LocalTime.of(17, 0), bus5);
        timeAndBus_PtoM_Map.put(LocalTime.of(19, 0), bus4);
        timeAndBus_PtoM_Map.put(LocalTime.of(21, 0), bus3);
        timeAndBus_PtoM_Map.put(LocalTime.of(23, 0), bus2);
        timeAndBus_PtoM_Map.put(LocalTime.of(1, 0),  bus1);
      
    }
    public static void main( String[] args ) {

        Facilities facilities = new Facilities();
        addBuses();
        Map<LocalDate, Map<LocalTime, Bus>> bookingDate_MtoP_Map = new HashMap<>();
        Map<LocalDate, Map<LocalTime, Bus>> bookingDate_PtoM_Map = new HashMap<>();
        Map<Integer, BillingDetails> billingDetails = new HashMap<>();
       
        String MtoP = "Mumbai to Pune";
        String PtoM = "Pune to Mumbai";
       
        char ch;
        do {
            System.out.println("************** MENU **************");
            System.out.println("1. Check Availability for Date.");
            System.out.println("2. Book Bus.");
            System.out.println("3. Print Bill");
            System.out.println("4. Booking Cancel");
            System.out.println("----------------------------------");

            System.out.println("Enter your choice : ");
            int choice;
            boolean flag = false;
            try {
                
                choice = sc.nextInt();
            } catch(InputMismatchException e) {
            
                choice = 0;
                flag = true;
            }

            switch (choice) {
                
                case 1:{
                        System.out.println("Enter Date : ");
                        String date = sc.next();
                        try {
                            LocalDate localDate = LocalDate.parse(date);

                            System.out.println("Enter Source : ");
                            String source = sc.next();

                            System.out.println("Enter Destination : ");
                            String dest = sc.next();

                            if(MtoP.equalsIgnoreCase(source + " to " + dest)) {

                                facilities.checkBusAvailability(localDate, bookingDate_MtoP_Map, timeAndBus_MtoP_Map);
                            } else if(PtoM.equalsIgnoreCase(source + " to " + dest)) {

                                facilities.checkBusAvailability(localDate, bookingDate_PtoM_Map, timeAndBus_PtoM_Map);
                            } else {

                                System.out.println("Source and destination not found !!!");
                            }
                        } catch(Exception e) {

                            System.out.println("Enter Date (like [YYYY-MM-DD])");
                        }
                    }
                    break;

                case 2 :{
                    System.out.println("Enter Date : ");
                    String date = sc.next();

                    LocalDate localDate = LocalDate.parse(date);

                    System.out.println("Enter Source : ");
                    String source = sc.next();

                    System.out.println("Enter Destination : ");
                    String dest = sc.next();

                    if(MtoP.equalsIgnoreCase(source + " to " + dest)) {

                        facilities.booking(localDate, bookingDate_MtoP_Map, timeAndBus_MtoP_Map, billingDetails, source, dest);
                    } else if(PtoM.equalsIgnoreCase(source + " to " + dest)) {

                        facilities.booking(localDate, bookingDate_PtoM_Map, timeAndBus_PtoM_Map, billingDetails, source, dest);
                    } else {

                        System.out.println("Source and destination not found !!!");
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
                            if(MtoP.equalsIgnoreCase(source + " to " + dest)) {

                                facilities.bookingCancel(billingId, billingDetails, timeAndBus_MtoP_Map);
                            } else if(PtoM.equalsIgnoreCase(source + " to " + dest)) {
        
                                facilities.bookingCancel(billingId, billingDetails, timeAndBus_PtoM_Map);
                            }
                        
                        } else {

                            System.out.println("Booking Not Found");
                        }
                    }
                    break;
                default:
                    break;
            }
            System.out.println("Do you want to continue ? : ");
            ch = sc.next().charAt(0);
            if(flag) {

                ch = 'Y';
            }
        } while(ch == 'Y' || ch == 'y');
    }
}
