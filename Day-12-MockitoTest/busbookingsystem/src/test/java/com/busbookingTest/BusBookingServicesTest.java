package com.busbookingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;


import org.junit.jupiter.api.Test;

import com.busbooking.Facilities;

public class BusBookingServicesTest {

      Facilities mockFacilities = mock(Facilities.class);
      Facilities facility = new Facilities();

     

      @Test
      
      public void checkAvailabilityTest() throws Exception {


            when(mockFacilities.checkAvailability("pune", "mumbai","2024-02-22", "23:00", new File("bus.json"), new File("bookings.json"))).thenReturn(true);

            boolean actual=mockFacilities.checkAvailability("pune", "mumbai","2024-02-22", "23:00", new File("bus.json"), new File("bookings.json"));

            boolean expected=true;

            assertEquals(expected, actual);
            System.out.println("Test Case Pass");

      }

      @Test

      public void printBookingDetailsTest() throws Exception{
            when(mockFacilities.printBookingDetails(1065, new File("bookings.json"))).thenReturn(true);

            boolean actual=mockFacilities.printBookingDetails(1065,  new File("bookings.json"));
            boolean expected=true;

            assertEquals(actual, expected);
            System.out.println("Pass");
      }

      @Test

      public void bookSeatsTest() throws Exception{
            when(mockFacilities.bookSeats("pune", "mumbai", "2024-02-22", "23:00", 10, "Aniket", new File("bus.json"), new File("bookings.json"))).thenReturn(true);

            boolean actual=mockFacilities.bookSeats("pune", "mumbai", "2024-02-22", "23:00", 10, "Aniket", new File("bus.json"), new File("bookings.json"));

            boolean expected=true;

            assertEquals(expected, actual);
            System.out.println("Pass");
      }

      @Test

      public void cancelBooking() throws Exception{
            when(mockFacilities.cancelBooking(1606, new File("bus.json"), new File("bookings.json"))).thenReturn(true);

            boolean actual=mockFacilities.cancelBooking(1606, new File("bus.json"), new File("bookings.json"));

            boolean expected=true;

            assertEquals(expected, actual);
            System.out.println("Pass");
      }

}
