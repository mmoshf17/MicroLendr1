package com.example.mohib.microlendr;

import java.io.Serializable;

public class RejectedRequests implements Serializable {

    private int requestId;
    private String amount;
    private String borrowUserName;

    public RejectedRequests(int requestId, String borrowUserName, String amount) {
        // public Tickets(String name){


        this.requestId = requestId;
        this.borrowUserName = borrowUserName;
        this.amount = amount;

    }

    public RejectedRequests(){
    }

    public String toString() {

        return "From: " + borrowUserName + "\n" + "Total Amount: " + amount;
    }
}
