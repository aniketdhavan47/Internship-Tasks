package com.busbooking;

 
class Route {
   private String source;
   private String destination;

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

   
    
}