package com.busbookingTest;

import com.busbooking.Booking;
import com.busbooking.Bus;
import com.busbooking.BusBookingException;
import com.busbooking.Facilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.File;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class BusBookingTestNg {
        @Mock
        private ObjectMapper mockMapper;

        Facilities mockFacilities = mock(Facilities.class);
        
        @DataProvider(name = "printBookingData")
        public Object[][] getPrintBookingData() {
                return new Object[][] {
                                { 4742, new File("bookings.json") },
                                { 9561, new File("bookings.json") },
                                { 3106, new File("bookings.json") },
                                { 8286, new File("bookings.json") }
                };
        }

        @Test(dataProvider = "printBookingData")
        public void printBookingDetailsTest(int id, File busBooking) throws Exception {
                when(mockFacilities.printBookingDetails(id, busBooking)).thenReturn(new Booking());

                Booking actual = mockFacilities.printBookingDetails(id, busBooking);
                Booking expected = new Booking();

                  System.out.println("Pass");

        }

        @DataProvider(name = "bookSeatsData")
        public Object[][] getBookSeatData() {
                return new Object[][] {
                                { "pune", "mumbai", "2024-02-28", "23:00", 10, "aniket", new File("bus.json"),
                                                new File("bookings.json") },
                                { "mumbai", "pune", "2024-02-28", "22:00", 12, "sanket", new File("bus.json"),
                                                new File("bookings.json") }
                };
        }

        @Test(dataProvider = "bookSeatsData")

        public void bookSeatsTest(String source, String destination, String bookingDate, String bookingTime, int seats,
                        String name, File bus, File busBooking) throws Exception {
                when(mockFacilities.bookSeats(source, destination, bookingDate, bookingTime, seats, name, bus,
                                busBooking))
                                .thenReturn(seats);

                int actual = mockFacilities.bookSeats(source, destination, bookingDate, bookingTime, seats, name,
                                bus,
                                busBooking);

                int expected = seats;

                Assert.assertEquals(actual,expected);

                System.out.println("Pass");
        }

        @DataProvider(name = "cancelBookingData")

        public Object[][] getCancelBookingData() {
                return new Object[][] {
                                { 4742, new File("bus.json"), new File("bookings.json") },
                                { 9561, new File("bus.json"), new File("bookings.json") },
                                { 3106, new File("bus.json"), new File("bookings.json") },
                                { 8286, new File("bus.json"), new File("bookings.json") }
                };
        }

        @Test(dataProvider = "cancelBookingData")

        public void cancelBooking(int id, File bus, File busBooking) throws Exception {
                when(mockFacilities.cancelBooking(id, bus, busBooking)).thenReturn(id);

                int actual = mockFacilities.cancelBooking(id, bus, busBooking);

                int expected = id;

                Assert.assertEquals(actual,expected);
                System.out.println("Pass");
        }

        @DataProvider(name = "checkAvailability")
        public Object[][] getCheckAvailability() {
                return new Object[][] {
                                { "pune", "delhi", "2024-02-28", "23:00", new File("bus.json"),
                                                new File("bookings.json") },
                                { "mumbai", "pune", "2024-02-25", "22:00", new File("bus.json"),
                                                new File("bookings.json") }
                };
        }

        @Test(dataProvider = "checkAvailability")
        public void CheckAvailability_NoBusFound_Test(String source, String destination, String bookingDate,
                        String bookingTime, File bus, File busBooking) throws Exception {

                when(mockFacilities.checkAvailability(source, destination, bookingDate, bookingTime, bus, busBooking))
                                .thenThrow(new BusBookingException("No bus found for the specified route."));

                BusBookingException actual = assertThrows(BusBookingException.class,
                                () -> mockFacilities.checkAvailability(source, destination, bookingDate, bookingTime,
                                                bus, busBooking));
                Exception expected = new BusBookingException("No bus found for the specified route.");
                Assert.assertEquals( actual.getMessage(), expected.getMessage());
        }

        @DataProvider(name = "bookSeats")
        public Object[][] getBookSeats() {
                return new Object[][] {
                                { "pune", "mumbai", "2024-02-28", "23:00", 40, "aniket", new File("bus.json"),
                                                new File("bookings.json") },
                                { "mumbai", "pune", "2024-02-28", "22:00", 50, "sanket", new File("bus.json"),
                                                new File("bookings.json") },
                              
                };
        }

        @Test(dataProvider = "bookSeats")

        public void bookSeats_NotEnoughSeats_Test(String source, String destination, String bookingDate,
                        String bookingTime,
                        int seats, String name, File bus, File busBooking) throws Exception {
                when(mockFacilities.bookSeats(source, destination, bookingDate, bookingTime, seats, name, bus,
                                busBooking))
                                .thenThrow(new BusBookingException("Not enough seats available. "));

                Exception actual = assertThrows(BusBookingException.class,
                                () -> mockFacilities.bookSeats(source, destination, bookingDate, bookingTime, seats,
                                                name, bus,
                                                busBooking));

                Exception expected = new BusBookingException("Not enough seats available. ");

                Assert.assertEquals(actual.getMessage(),expected.getMessage());

        }

        @DataProvider(name = "bookSeatsInvalid")
        public Object[][] getBookSeatsInvalid() {
                return new Object[][] {
                                { "pune", "delhi", "2024-02-28", "23:00", 6, "aniket", new File("bus.json"),
                                                new File("bookings.json") },
                                { "delhi", "pune", "2024-02-28", "22:00", 5, "sanket", new File("bus.json"),
                                                new File("bookings.json") }
                };
        }

        @Test(dataProvider = "bookSeatsInvalid")

        public void bookSeats_NoBusFound_Test(String source, String destination, String bookingDate, String bookingTime,
                        int seats, String name, File bus, File busBooking) throws Exception {
                when(mockFacilities.bookSeats(source, destination, bookingDate, bookingTime, seats, name, bus,
                                busBooking))
                                .thenThrow(new BusBookingException("No bus found for the specified route."));

                Exception actual = assertThrows(BusBookingException.class,
                                () -> mockFacilities.bookSeats(source, destination, bookingDate, bookingTime, seats,
                                                name, bus,
                                                busBooking));

                Exception expected = new BusBookingException("No bus found for the specified route.");

                Assert.assertEquals(expected.getMessage(), actual.getMessage());
        }

        @DataProvider(name = "printBooking")
        public Object[][] getPrintBooking() {
                return new Object[][] {
                                { 4742, new File("bookings.json") },
                                { 9561, new File("bookings.json") },
                                { 3106, new File("bookings.json") },
                                { 8286, new File("bookings.json") }
                };
        }

        @Test(dataProvider = "printBooking")

        public void printBookingDetails_BookingNotFound_Test(int id, File busBooking) throws Exception {
                when(mockFacilities.printBookingDetails(id, busBooking))
                                .thenThrow(new BusBookingException("Invalid Booking Id"));

                Exception actual = assertThrows(BusBookingException.class,
                                () -> mockFacilities.printBookingDetails(id, busBooking));

                Exception expected = new BusBookingException("Invalid Booking Id");

                Assert.assertEquals(expected.getMessage(), actual.getMessage());

        }

        @DataProvider(name = "cancelBooking")

        public Object[][] getCancelBooking() {
                return new Object[][] {
                                { 4742, new File("bus.json"), new File("bookings.json") },
                                { 9561, new File("bus.json"), new File("bookings.json") },
                                { 3106, new File("bus.json"), new File("bookings.json") },
                                { 8286, new File("bus.json"), new File("bookings.json") }
                };
        }

        @Test(dataProvider = "cancelBooking")
        public void cancelBooking_BookingNotFound_Test(int id, File bus, File busBooking) throws Exception {
                when(mockFacilities.cancelBooking(id, bus, busBooking))
                                .thenThrow(new BusBookingException("Invalid Booking Id"));

                Exception actual = assertThrows(BusBookingException.class,
                                () -> mockFacilities.cancelBooking(id, bus, busBooking));

                Exception expected = new BusBookingException("Invalid Booking Id");

                Assert.assertEquals(expected.getMessage(), actual.getMessage());
        }

}







        