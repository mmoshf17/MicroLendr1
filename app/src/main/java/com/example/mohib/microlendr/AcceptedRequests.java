package com.example.mohib.microlendr;

import java.io.Serializable;

public class AcceptedRequests implements Serializable {

    private int requestId;
    private String amount;
    private String borrowUserName;

    public AcceptedRequests(int requestId, String borrowUserName, String amount) {
        // public Tickets(String name){


        this.requestId = requestId;
        this.borrowUserName = borrowUserName;
        this.amount = amount;

    }

    public AcceptedRequests(){
    }

    public String toString() {

        return "From: " + borrowUserName + "\n" + "Total Amount: " + amount;
    }
}
