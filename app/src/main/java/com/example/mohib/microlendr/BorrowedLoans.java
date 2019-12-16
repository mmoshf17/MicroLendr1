package com.example.mohib.microlendr;

import java.io.Serializable;

public class BorrowedLoans implements Serializable {

    //For getting information on loans lended

    private int requestId;
    private String lenderUserName;
    private String amount;
    private String amountRepaid;
    private String amountRemaining;
    private String repaywithin;
    private String installmentPaid;
    private String startingDate;

    public BorrowedLoans(int requestId, String lenderUserName, String amount, String amountRepaid, String amountRemaining,
                       String repaywithin, String installmentPaid, String startingDate ){
        // public Tickets(String name){


        this.requestId = requestId;
        this.lenderUserName = lenderUserName;
        this.amount = amount;
        this.amountRepaid = amountRepaid;
        this.amountRemaining = amountRemaining;
        this.repaywithin = repaywithin;
        this.installmentPaid = installmentPaid;
        this.startingDate = startingDate;


    }


    public BorrowedLoans(){
    }


    public String getLenderUserName(){ return lenderUserName; }
    public String getAmount()
    {
        return amount;
    }
    public String getAmountRepaid() { return amountRepaid; }
    public String getRepaywithin() { return repaywithin; }
    public String getStartingDate() { return startingDate; }
    public String getAmountRemaining() { return amountRemaining; }
    public String getInstallmentPaid() { return installmentPaid; }


    public String toString() {

        return "Borrowed: " + lenderUserName + "\n" + "Total Amount: " + amount;
    }
}
