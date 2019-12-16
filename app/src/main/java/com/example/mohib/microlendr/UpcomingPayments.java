package com.example.mohib.microlendr;

import java.io.Serializable;

public class UpcomingPayments implements Serializable {

    //For getting information on Upcoming Payments from the borrower

    private int requestId;
    private String borrowerUserName;
    private String amountRepaid;


    public UpcomingPayments(int requestId, String borrowerUserName, String amountRepaid ){
        // public Tickets(String name){


        this.requestId = requestId;
        this.borrowerUserName = borrowerUserName;
        this.amountRepaid = amountRepaid;



    }


    public UpcomingPayments(){
    }



    public String toString() {

        return "From: " + "\n" + borrowerUserName + "\n" + "Amount: " + amountRepaid;
    }
}
