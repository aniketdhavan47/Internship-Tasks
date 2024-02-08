package com.busbooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class Facilities {

    static Scanner sc = new Scanner(System.in);

    private void printBusAvaibility(Map<LocalTime, Bus> timeAndBus, LocalDate date) {
        LocalDate todaysDate = LocalDate.now();
        System.out.println("Time\tBus No\t\tR Seats\t   Fare\t status");
        for (Map.Entry<LocalTime, Bus> entry : timeAndBus.entrySet()) {
            Bus bus = entry.getValue();

            if (bus.getNoOfSeats() > 0 && (entry.getKey().isAfter(LocalTime.now()) || todaysDate.   isBefore(date))) {
                System.out.println(entry.getKey() + "\t" + bus.getBusno() + "\t  " + bus.getNoOfSeats() + " \t  "
                        + bus.getFare() + "\t " + "Available");
            } else {
                System.out.println(entry.getKey() + "\t" + bus.getBusno() + "\t  " + bus.getNoOfSeats() + " \t  "
                        + bus.getFare() + "\t " + "Not Available");
            }
        }
    }

    public void checkBusAvailability(LocalDate date, Map<LocalDate, Map<LocalTime, Bus>> bookingDateMap,
            Map<LocalTime, Bus> timeAndBus) {
        LocalDate todaysDate = LocalDate.now();
        if (date.isEqual(todaysDate) || todaysDate.isBefore(date)) {
            if (bookingDateMap.containsKey(date)) {

                printBusAvaibility(bookingDateMap.get(date), date);
            } else {

                printBusAvaibility(timeAndBus, date);
            }
        } else {

            System.out.println("Invalid Date");
        }
    }

    public void booking(LocalDate date, Map<LocalDate, Map<LocalTime, Bus>> bookingDateMap,
            Map<LocalTime, Bus> timeAndBus, Map<Integer, BillingDetails> billingDetails, String source, String dest) {
        LocalDate todaysDate = LocalDate.now();
        if (date.isEqual(todaysDate) || todaysDate.isBefore(date)) {
            if (bookingDateMap.containsKey(date)) {

                timeAndBus = bookingDateMap.get(date);
                bookingDateMap.put(date, bookedSeat(date, timeAndBus, billingDetails, source, dest));
            } else {

                bookingDateMap.put(date, bookedSeat(date, timeAndBus, billingDetails, source, dest));
            }
        }
    }

    private Map<LocalTime, Bus> bookedSeat(LocalDate date, Map<LocalTime, Bus> timeAndBus,
            Map<Integer, BillingDetails> billingDetails, String source, String dest) {

        LocalDate todayDate = LocalDate.now();
        System.out.println("Enter Booking Id : ");
        int bookingId = sc.nextInt();

        System.out.println("Enter Bus Time : ");
        String time = sc.next();
        LocalTime localTime = LocalTime.parse(time);

        System.out.println("Enter No of Seats : ");
        int noOfSeats = sc.nextInt();
        
        System.out.println("Customer Name : ");
        String customername = sc.next();

        System.out.println("Customer Contact : ");
        long contact = sc.nextLong();

        if (!billingDetails.containsKey(bookingId)) {

            if (timeAndBus.containsKey(localTime) && (localTime.isAfter(LocalTime.now()) || todayDate.isBefore(date))) {

                Bus bus = timeAndBus.get(localTime);

                if (noOfSeats <= bus.getNoOfSeats()) {

                    bus.setNoOfSeats(bus.getNoOfSeats() - noOfSeats);
                    BillingDetails bd = new BillingDetails()
                    .setAjencyName("XYZ")
                    .setDate(new Date())
                    .setBookedSeats(noOfSeats)
                    .setBusNumber(bus.getBusno())
                    .setCustomerName(customername)
                    .setContact(contact)
                    .setSource(source)
                    .setDestination(dest)
                    .setTotalFare(noOfSeats * noOfSeats)
                    .setTime(localTime);

                    billingDetails.put(bookingId, bd);

                    System.out.println("Booking Successfull !!!");
                } else {

                    System.out.println("Seats not availables");
                }
            } else {

                System.out.println("Invalid Time");
            }
        } else {

            System.out.println("Billng Id Already Exists");
        }

        return timeAndBus;
    }

    public void printBill(int billingId, Map<Integer, BillingDetails> billingDetails) {
        if(billingDetails.containsKey(billingId)) {
            BillingDetails bDetails = billingDetails.get(billingId);
            
            System.out.println("*************** BILL ****************");
            System.out.println("--------------------------------------");
            System.out.println("Ajency Name : " + bDetails.getAjencyName() + "  " + "Date : " + bDetails.getDate());
            System.out.println("Bus Number : " + bDetails.getBusNumber());
            System.out.println("Booked Seats : " + bDetails.getBookedSeats());
            System.out.println("Source : " + bDetails.getSource() + "---->" + "Desination : " + bDetails.getDestination());
            System.out.println("--------------------------------------");
            System.out.println("Customer Name : " + bDetails.customerName);
            System.out.println("Contact : " + bDetails.getContact());
            System.out.println("--------------------------------------");
            System.out.println("Total Fare : " + bDetails.getTotalFare());
            System.out.println("--------------------------------------");
        } else {

            System.out.println("Billing Id Not Found !!!");
        }
    }

    public void bookingCancel(int billingId, Map<Integer, BillingDetails> billingDetails, Map<LocalTime, Bus> timeAndBus) {
        
        BillingDetails bDetails = billingDetails.get(billingId);
        LocalTime time = bDetails.getTime();
        if(timeAndBus.containsKey(time)) {
            Bus bus = timeAndBus.get(time);
            bus.setNoOfSeats(bus.getNoOfSeats() + bDetails.getBookedSeats());

            timeAndBus.put(time, bus);
            billingDetails.remove(billingId);

            System.out.println("Booking Cancel");
        } else {

            System.out.println("Booking Not Found");
        }
    }
}
