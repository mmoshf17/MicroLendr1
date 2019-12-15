package com.example.mohib.microlendr;

import java.io.Serializable;

public class LoanRequests implements Serializable {

    //For getting lender's requests
    private int requestId;
    private String borrowerUserName;
    private String amount;
    private String repayWithin;
    private String startingDate;
    private String dateCreated;






    public LoanRequests(int requestId, String borrowerUserName, String amount, String repayWithin, String startingDate, String dateCreated){
        // public Tickets(String name){


        this.requestId = requestId;
        this.borrowerUserName = borrowerUserName;
        this.amount = amount;
        this.repayWithin = repayWithin;
        this.startingDate = startingDate;
        this.dateCreated = dateCreated;


    }

    public LoanRequests(){
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
    public String getRepayWithin() { return repayWithin; }
    public String getStartingDate() { return startingDate; }
    public String getDateCreated() { return dateCreated; }



    public String toString() {

        return "Borrower Name: " + borrowerUserName + "\n" + "Amount Requested: " + amount;
    }



}
