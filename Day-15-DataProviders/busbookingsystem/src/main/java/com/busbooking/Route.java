package com.busbooking;

 
public class Route {
   private String source;
   private String destination;

    public Route() {
}

    public Route(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    
    @Override
    public boolean equals(Object obj) {
        Route route = (Route) obj;
        return source.toLowerCase().equals(route.source) && destination.toLowerCase().equals(route.destination);
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode();
    }

    @Override
    public String toString() {
        return source + " to " + destination;
    }

   
    
}