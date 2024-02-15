package com.busbooking;

 class Route {
    private String source;
    private String destination;

    public Route(String src,String dest){
        this.source=src;
        this.destination=dest;
    }
    
    public String getSource(){
        return this.source;
    }
    public String getDestination(){
        return this.destination;
    }
    
}