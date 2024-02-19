package com.busbooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Facilities {

    public Map<LocalTime, Bus> bookingHelper(Map<Route, Map<LocalTime, Bus>> routeAndTime, String source,
            String destination) throws BusBookingException {
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
            }
        }
    }

    public void checkBusAvailability(LocalDate localDate, String source, String destination,
            Map<Route, Map<LocalTime, Bus>> routeTimeMap) throws BusBookingException {

        for (Map.Entry<Route, Map<LocalTime, Bus>> entry : routeTimeMap.entrySet()) {
            Route r = entry.getKey();
            if (source.toLowerCase().equals(r.getSource())
                    && destination.toLowerCase().equals(r.getDestination())) {
                printBusAvaibility(entry.getValue(), localDate);
                return;
            }

        }
        throw new BusBookingException("Source Or Destination is Invaild");

    }

    public void booking(LocalDate localDate, String source, String dest, LocalTime localTime, int noOfSeats,
            String customername, String contact, Map<Route, Map<LocalTime, Bus>> routeAndTime,
            Map<Integer, BillingDetails> billingDetails ) throws BusBookingException {

        Map<LocalTime, Bus> timeAndBus = bookingHelper(routeAndTime, source, dest);
        if (timeAndBus == null) {
            throw new BusBookingException("Source And Destination is Invalid");
        }
        if (timeAndBus.containsKey(localTime) && (localTime.isAfter(LocalTime.now()))) {

            Bus bus = timeAndBus.get(localTime);

            if (noOfSeats <= bus.getNoOfSeats()) {

                bus.setNoOfSeats(bus.getNoOfSeats() - noOfSeats);
                BillingDetails bd = new BillingDetails();
                bd.setAjencyName("XYZ");
                bd.setDate(localDate);
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

    }

    public void printBill(int billingId, Map<Integer, BillingDetails> billingDetails) throws BusBookingException {

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
            Map<Route, Map<LocalTime, Bus>> routeAndTime) throws BusBookingException {

        if (billingDetails.get(billingId) != null) {

            BillingDetails bDetails = billingDetails.get(billingId);

            Map<LocalTime, Bus> timeAndBus = bookingHelper(routeAndTime, bDetails.getSource(),
                    bDetails.getDestination());

            LocalTime time = bDetails.getTime();

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
