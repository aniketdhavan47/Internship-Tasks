package com.busbooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {

    static Route r1 = new Route("pune", "mumbai");
    static Route r2 = new Route("mumbai", "pune");


    static Map<LocalTime, Bus> puneToMumbai = new LinkedHashMap<>(); 
    static Map<LocalTime, Bus> mumbaiToPune = new LinkedHashMap<>(); 
    static Map<Route, Map<LocalTime, Bus>> routeAndTime = new HashMap<>();

   

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


        Bus bus11 = new Bus("MH 02 Z 8563", 50, 150.00f);
        Bus bus12 = new Bus("MH 02 B 9873", 80, 180.00f);
        Bus bus13 = new Bus("MH 02 C 8765", 60, 120.00f);
        Bus bus14 = new Bus("MH 02 Y 3858", 50, 150.00f);
        Bus bus15 = new Bus("MH 02 J 2323", 80, 180.00f);
        Bus bus16 = new Bus("MH 02 F 3985", 60, 120.00f);
        Bus bus17 = new Bus("MH 02 G 2358", 50, 150.00f);
        Bus bus18 = new Bus("MH 02 K 7385", 80, 180.00f);
        Bus bus19 = new Bus("MH 02 I 8238", 60,110.00f);
        Bus bus20 = new Bus("MH 02 D 6382", 60, 120.00f);

        puneToMumbai.put(LocalTime.of(9, 0), bus1);
        puneToMumbai.put(LocalTime.of(11, 0), bus2);
        puneToMumbai.put(LocalTime.of(13, 0), bus3);
        puneToMumbai.put(LocalTime.of(15, 0), bus4);
        puneToMumbai.put(LocalTime.of(17, 0), bus5);
        puneToMumbai.put(LocalTime.of(19, 0), bus6);
        puneToMumbai.put(LocalTime.of(21, 0), bus7);
        puneToMumbai.put(LocalTime.of(23, 0), bus8);
        puneToMumbai.put(LocalTime.of(1, 0), bus9);
        puneToMumbai.put(LocalTime.of(3, 0), bus10);

        mumbaiToPune.put(LocalTime.of(8, 0), bus11);
        mumbaiToPune.put(LocalTime.of(10, 0), bus12);
        mumbaiToPune.put(LocalTime.of(12, 0), bus13);
        mumbaiToPune.put(LocalTime.of(14, 0), bus14);
        mumbaiToPune.put(LocalTime.of(16, 0), bus15);
        mumbaiToPune.put(LocalTime.of(18, 0), bus16);
        mumbaiToPune.put(LocalTime.of(20, 0), bus17);
        mumbaiToPune.put(LocalTime.of(22, 0), bus18);
        mumbaiToPune.put(LocalTime.of(0, 0), bus19);
        mumbaiToPune.put(LocalTime.of(2, 0), bus20);

        routeAndTime.put(r1, puneToMumbai);
        routeAndTime.put(r2, mumbaiToPune);
     
    }

    public static void main(String[] args) {

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
            int choice;
            boolean flag = false;
            try {

                choice = sc.nextInt();
            } catch (InputMismatchException e) {

                choice = 0;
                flag = true;
            }

            switch (choice) {

                case 1: {
                    System.out.println("Enter Date : ");
                    String date = sc.next();

                    try {

                        LocalDate localDate = LocalDate.parse(date);
                        LocalDate todaysDate = LocalDate.now();

                        if (!localDate.isBefore(todaysDate)) {
                            facilities.checkBusAvailability(localDate, bookingDate, routeAndTime);
                        }else{
                            throw new BusBookingException("Can not Check Availability for provided date");
                        }
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                    break;

                case 2: {
                    try {
                        System.out.println("Enter Date : ");
                        String date = sc.next();

                        LocalDate localDate = LocalDate.parse(date);
                        LocalDate todaysDate = LocalDate.now();

                        if (!localDate.isBefore(todaysDate)) {
                            System.out.println("Enter Source : ");
                            String source = sc.next();

                            System.out.println("Enter Destination : ");
                            String dest = sc.next();

                            facilities.booking(localDate, routeAndTime, billingDetails, source, dest);
                        } else {
                            throw new BusBookingException("Can Not Book Seats For Provided Date");
                        }

                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                    break;

                case 3: {
                    System.out.println("Enter Billing Id : ");
                    try {
                        int billingId = sc.nextInt();
                        facilities.printBill(billingId, billingDetails);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                    break;

                case 4: {
                    System.out.println("Enter Billing Id : ");
                    try {
                        int billingId = sc.nextInt();
                        if (billingDetails.containsKey(billingId)) {

                            facilities.bookingCancel(billingId, billingDetails, routeAndTime);

                        }else{
                            throw new BusBookingException("Booking Not Found");
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                    break;
                case 5: {
                    System.out.println("Exiting");
                    return;
                }

            }
            System.out.println("Do you want to continue ? : ");
            ch = sc.next().charAt(0);
            if (flag) {

                ch = 'Y';
            }

        } while (ch == 'Y' || ch == 'y');
    }
}
