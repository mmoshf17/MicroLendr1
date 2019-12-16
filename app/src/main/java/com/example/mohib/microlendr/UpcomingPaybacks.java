package com.example.mohib.microlendr;

import java.io.Serializable;

public class UpcomingPaybacks implements Serializable {


    private int requestId;
    private String lenderUserName;
    private String amountRepaid;


    public UpcomingPaybacks(int requestId, String lenderUserName, String amountRepaid ){

        this.requestId = requestId;
        this.lenderUserName = lenderUserName;
        this.amountRepaid = amountRepaid;



    }


    public UpcomingPaybacks(){
    }




    public String toString() {

        return "To: " + "\n" + lenderUserName + "\n" + "Amount: " + amountRepaid;
    }
}
