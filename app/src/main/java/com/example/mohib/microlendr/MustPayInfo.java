package com.example.mohib.microlendr;

import java.io.Serializable;

public class MustPayInfo implements Serializable {

//For getting information on accepted requests

    private int requestId;
    private String lenderUserName;
    private String amount;
    private String amountRepaid;

    public MustPayInfo(int requestId, String lenderUserName, String amount, String amountRepaid){

        this.requestId = requestId;
        this.lenderUserName = lenderUserName;
        this.amount = amount;
        this.amountRepaid = amountRepaid;


}
    public MustPayInfo(){
    }

    public String toString() {

        return "Must pay to: " + lenderUserName + "\n" + "Total Amount: " + amount + "\n" + "Amount Paid: " + amountRepaid;
    }

}
