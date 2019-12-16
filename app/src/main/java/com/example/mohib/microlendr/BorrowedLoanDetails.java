package com.example.mohib.microlendr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BorrowedLoanDetails extends AppCompatActivity {

    private BorrowedLoans borrowedLoans;
    private TextView lenderUserName;
    private TextView amount;
    private TextView amountRepaid;
    private TextView amountRemaining;
    private TextView repaywithin;
    private TextView installmentPaid;
    private TextView startingDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_loan_details);

        Intent intent = getIntent();
        borrowedLoans = (BorrowedLoans) intent.getSerializableExtra("BorrowedLoans");

        lenderUserName = findViewById(R.id.lblBorrowed);
        lenderUserName.setText("Lender: " + borrowedLoans.getLenderUserName());

        amount = findViewById(R.id.lblBorrowedAmount);
        amount.setText("Amount lended: " + borrowedLoans.getAmount());

        repaywithin = findViewById(R.id.lblBorrowedRepayWithin);
        repaywithin.setText("Repay within months: " + borrowedLoans.getRepaywithin());

        amountRepaid = findViewById(R.id.lblBorrowedAmountPaid);
        amountRepaid.setText("Amount paid: " + borrowedLoans.getAmountRepaid());

        startingDate = findViewById(R.id.lblBorrowedStartingDate);
        startingDate.setText("First payment: " + borrowedLoans.getStartingDate());

        amountRemaining = findViewById(R.id.lblBorrowedAmountRemaining);
        amountRemaining.setText("Amount remaining: " + borrowedLoans.getAmountRemaining());

        installmentPaid = findViewById(R.id.lblBorrowedInstallmentPaid);
        installmentPaid.setText("Installment paid: " + borrowedLoans.getInstallmentPaid());

    }
}
