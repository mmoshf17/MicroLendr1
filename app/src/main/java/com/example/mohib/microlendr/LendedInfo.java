package com.example.mohib.microlendr;

import java.io.Serializable;

public class LendedInfo implements Serializable {

    //For getting information on accepted requests

    private int requestId;
    private String borrowerUserName;
    private String amount;
    private String amountRepaid;



    public LendedInfo(int requestId, String borrowerUserName, String amount, String amountRepaid){
        // public Tickets(String name){


        this.requestId = requestId;
        this.borrowerUserName = borrowerUserName;
        this.amount = amount;
        this.amountRepaid = amountRepaid;


    }

    public LendedInfo(){
    }

    public int getRequestId()
    {
        return requestId;
    }
    public String getBorrowerUserName()
    {
        return borrowerUserName;
    }
    public String getAmount()
    {
        return amount;
    }
    public String getAmountRepaid() { return amountRepaid; }



    public String toString() {

        return "Lended: " + borrowerUserName + "\n" + "Total Amount: " + amount + "\n" + "Amount Paid: " + amountRepaid;
    }

}
