package com.example.mohib.microlendr;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.support.v4.text.HtmlCompat;
import android.text.Html;

import java.io.Serializable;

import static android.graphics.Typeface.*;
import static android.provider.Settings.System.getString;

public class LendedInfo<BOLD> implements Serializable {

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

        return "Lended to: " + borrowerUserName + "\n" + "Total Amount: " + amount + "\n" + "Amount Paid: " + amountRepaid;
    }

}
