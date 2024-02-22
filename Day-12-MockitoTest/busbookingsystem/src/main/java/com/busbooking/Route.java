package com.busbooking;

 
class Route {
   private String source;
   private String destination;

    public Route() {
}

    public Route(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    // Override equals and hashCode for proper comparison and usage in collections
    @Override
    public boolean equals(Object obj) {
        Route route = (Route) obj;
        return source.equals(route.source) && destination.equals(route.destination);
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