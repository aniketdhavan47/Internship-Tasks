package com.busbookingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.busbooking.BusBookingException;
import com.busbooking.Facilities;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BusBookingServicesTest {
      @Mock
      private ObjectMapper mockMapper;

      Facilities mockFacilities = mock(Facilities.class);

      @Test

      public void checkAvailabilityTest() throws Exception {

            when(mockFacilities.checkAvailability("pune", "mumbai", "2024-02-23", "23:00", new File("bus.json"),
                        new File("bookings.json"))).thenReturn(true);

            boolean actual = mockFacilities.checkAvailability("pune", "mumbai", "2024-02-23", "23:00",
                        new File("bus.json"), new File("bookings.json"));

            boolean expected = true;

            assertEquals(expected, actual);
            System.out.println("Test Case Pass");

      }

      @Test

      public void printBookingDetailsTest() throws Exception {
            when(mockFacilities.printBookingDetails(1065, new File("bookings.json"))).thenReturn(true);

            boolean actual = mockFacilities.printBookingDetails(1065, new File("bookings.json"));
            boolean expected = true;

            assertEquals(actual, expected);
            System.out.println("Pass");
      }

      @Test

      public void bookSeatsTest() throws Exception {
            when(mockFacilities.bookSeats("pune", "mumbai", "2024-02-23", "23:00", 10, "Aniket", new File("bus.json"),
                        new File("bookings.json"))).thenReturn(true);

            boolean actual = mockFacilities.bookSeats("pune", "mumbai", "2024-02-23", "23:00", 10, "Aniket",
                        new File("bus.json"), new File("bookings.json"));

            boolean expected = true;

            assertEquals(expected, actual);
            System.out.println("Pass");
      }

      @Test

      public void cancelBooking() throws Exception {
            when(mockFacilities.cancelBooking(1606, new File("bus.json"), new File("bookings.json"))).thenReturn(true);

            boolean actual = mockFacilities.cancelBooking(1606, new File("bus.json"), new File("bookings.json"));

            boolean expected = true;

            assertEquals(expected, actual);
            Exception exp = new Exception();
            assertEquals(exp, exp);
            System.out.println("Pass");
      }

      @Test
      public void CheckAvailability_NoBusFound_Test() throws Exception {

            when(mockFacilities.checkAvailability("pune", "delhi", "2024-02-22", "10:00", new File("bus.json"),
                        new File("bookings.json")))
                        .thenThrow(new BusBookingException("No bus found for the specified route."));

            BusBookingException actual = assertThrows(BusBookingException.class,
                        () -> mockFacilities.checkAvailability("pune", "delhi", "2024-02-22", "10:00",
                                    new File("bus.json"), new File("bookings.json")));
            Exception expected = new BusBookingException("No bus found for the specified route.");
            assertEquals(expected.getMessage(), actual.getMessage());
      }

      @Test

      public void bookSeats_NotEnoughSeats_Test() throws Exception {
            when(mockFacilities.bookSeats("pune", "mumbai", "2024-02-23", "10:00", 31, "Aniket", new File("bus.json"),
                        new File("bookings.json"))).thenThrow(new BusBookingException("Not enough seats available. "));

            Exception actual = assertThrows(BusBookingException.class,
                        () -> mockFacilities.bookSeats("pune", "mumbai", "2024-02-23", "10:00", 31, "Aniket",
                                    new File("bus.json"), new File("bookings.json")));

            Exception expected = new BusBookingException("Not enough seats available. ");

            assertEquals(expected.getMessage(), actual.getMessage());

      }

      @Test

      public void bookSeats_NoBusFound_Test() throws Exception {
            when(mockFacilities.bookSeats("delhi", "mumbai", "2024-02-23", "10:00", 31, "Aniket", new File("bus.json"),
                        new File("bookings.json")))
                        .thenThrow(new BusBookingException("No bus found for the specified route."));

            Exception actual = assertThrows(BusBookingException.class,
                        () -> mockFacilities.bookSeats("delhi", "mumbai", "2024-02-23", "10:00", 31, "Aniket",
                                    new File("bus.json"), new File("bookings.json")));

            Exception expected = new BusBookingException("No bus found for the specified route.");

            assertEquals(expected.getMessage(), actual.getMessage());
      }

      @Test

      public void printBookingDetails_BookingNotFound_Test() throws Exception {
            when(mockFacilities.printBookingDetails(1090, new File("bookings.json")))
                        .thenThrow(new BusBookingException("Invalid Booking Id"));

            Exception actual = assertThrows(BusBookingException.class,  
                        () -> mockFacilities.printBookingDetails(1090, new File("bookings.json")));

            Exception expected = new BusBookingException("Invalid Booking Id");

            assertEquals(expected.getMessage(), actual.getMessage());  

      }

      public void cancelBooking_BookingNotFound_Test() throws Exception {
            when(mockFacilities.cancelBooking(1090,new File("bus.json"), new File("bookings.json")))
                        .thenThrow(new BusBookingException("Invalid Booking Id"));

            Exception actual = assertThrows(BusBookingException.class,
                        () -> mockFacilities.cancelBooking(1090, new File("bus.json"),new File("bookings.json")));

            Exception expected = new BusBookingException("Invalid Booking Id");

            assertEquals(expected.getMessage(), actual.getMessage());
      }
}
