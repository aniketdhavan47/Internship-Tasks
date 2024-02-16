package com.busbooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Facilities {

    static Scanner sc = new Scanner(System.in);

    public Map<LocalTime, Bus> bookingHelper(Map<Route, Map<LocalTime, Bus>> routeAndTime, String source,
            String destination) {
        for (Map.Entry<Route, Map<LocalTime, Bus>> entry : routeAndTime.entrySet()) {
            Route r = entry.getKey();
            if (source.toLowerCase().equals(r.getSource()) && destination.toLowerCase().equals(r.getDestination())) {
                return (entry.getValue());

            }
        }
        throw new BusBookingException("Source or Destination is Invalid");
    }

    private void printBusAvaibility(Map<LocalTime, Bus> timeAndBus, LocalDate date) {
        LocalDate todaysDate = LocalDate.now();
        System.out.println("Time\tBus No\t\tR Seats\t   Fare\t status");
        for (Map.Entry<LocalTime, Bus> entry : timeAndBus.entrySet()) {
            Bus bus = entry.getValue();

            if (bus.getNoOfSeats() > 0 && (entry.getKey().isAfter(LocalTime.now()) || todaysDate.isBefore(date))) {
                System.out.println(entry.getKey() + "\t" + bus.getBusno() + "\t  " + bus.getNoOfSeats() + " \t  "
                        + bus.getFare() + "\t " + "Available");
            } else {
                System.out.println(entry.getKey() + "\t" + bus.getBusno() + "\t  " + bus.getNoOfSeats() + " \t  "
                        + bus.getFare() + "\t " + "Not Available");
            }
        }
    }

    public void checkBusAvailability(LocalDate date, Map<LocalDate, Map<LocalTime, Bus>> bookingDateMap,
            Map<Route, Map<LocalTime, Bus>> routeTimeMap) {
               
                System.out.println("Enter Source : ");
                String source = sc.next();

                System.out.println("Enter Destination : ");
                String destination = sc.next();

                Map<LocalTime, Bus> timeAndBus=bookingHelper(routeTimeMap, source, destination);
               
                if (bookingDateMap.containsKey(date)) {

                    printBusAvaibility(bookingDateMap.get(date), date);
                } else {
    
                    printBusAvaibility(timeAndBus, date);
                }

    }

    

    public void booking(LocalDate date, Map<Route, Map<LocalTime, Bus>> routeAndTime,
            Map<Integer, BillingDetails> billingDetails, String source, String dest) {
        Map<LocalTime, Bus> timeAndBus = bookingHelper(routeAndTime, source, dest);
        if (timeAndBus == null) {
            throw new BusBookingException("Source or Destination Invalid");
        }

        try {
            System.out.println("Enter Bus Time : ");
            String time = sc.next();
            LocalTime localTime = LocalTime.parse(time);

            System.out.println("Enter No of Seats : ");
            int noOfSeats = sc.nextInt();

            System.out.println("Customer Name : ");
            String customername = sc.next();

            System.out.println("Customer Contact : ");
            long contact = sc.nextLong();

            if (timeAndBus.containsKey(localTime) && (localTime.isAfter(LocalTime.now()))) {

                Bus bus = timeAndBus.get(localTime);

                if (noOfSeats <= bus.getNoOfSeats()) {

                    bus.setNoOfSeats(bus.getNoOfSeats() - noOfSeats);
                    BillingDetails bd = new BillingDetails();
                    bd.setAjencyName("XYZ");
                    bd.setDate(date);
                    bd.setBookedSeats(noOfSeats);
                    bd.setBusNumber(bus.getBusno());
                    bd.setCustomerName(customername);
                    bd.setContact(contact);
                    bd.setSource(source);
                    bd.setDestination(dest);
                    bd.setTotalFare(noOfSeats * bus.getFare());
                    bd.setTime(localTime);
                    int bookingId = (int) Math.round(Math.random() * 10000);
                    billingDetails.put(bookingId, bd);

                    System.out.println("Booking Successfull !!!\n Your Booking id is :" + bookingId);
                } else {
                    System.out.println(bus.getNoOfSeats() + " " + "Seats Available");
                    throw new BusBookingException(noOfSeats + " " + "Seats not are availables");

                }
            } else {

                throw new BusBookingException("Invalid Time");
            }
        } catch (InputMismatchException e) {
           System.out.println(e.getMessage());
        }
    }

    public void printBill(int billingId, Map<Integer, BillingDetails> billingDetails) {
        if (billingDetails.containsKey(billingId)) {
            BillingDetails bDetails = billingDetails.get(billingId);

            System.out.println("*************** BILL ****************");
            System.out.println("--------------------------------------");
            System.out.println("Ajency Name : " + bDetails.getAjencyName() + "  " + "Date : " + bDetails.getDate());
            System.out.println("Bus Number : " + bDetails.getBusNumber());
            System.out.println("Time Of Bus :" + bDetails.getTime());
            System.out.println("Booked Seats : " + bDetails.getBookedSeats());
            System.out.println(
                    "Source : " + bDetails.getSource() + "---->" + "Desination : " + bDetails.getDestination());
            System.out.println("--------------------------------------");
            System.out.println("Customer Name : " + bDetails.customerName);
            System.out.println("Contact : " + bDetails.getContact());
            System.out.println("--------------------------------------");
            System.out.println("Total Fare : " + bDetails.getTotalFare());
            System.out.println("--------------------------------------");
        } else {

            throw new BusBookingException("Booking Not Found");
        }
    }

    public void bookingCancel(int billingId, Map<Integer, BillingDetails> billingDetails,
            Map<Route, Map<LocalTime, Bus>> routeAndTime) {

        BillingDetails bDetails = billingDetails.get(billingId);
        Map<LocalTime, Bus> timeAndBus = bookingHelper(routeAndTime, bDetails.getSource(), bDetails.getDestination());
        LocalTime time = bDetails.getTime();
        if (timeAndBus.containsKey(time)) {
            Bus bus = timeAndBus.get(time);
            bus.setNoOfSeats(bus.getNoOfSeats() + bDetails.getBookedSeats());

            timeAndBus.put(time, bus);
            billingDetails.remove(billingId);

            System.out.println("Booking Cancel");
        } else {

            throw new BusBookingException("Booking Not Found");
        }
    }
}
