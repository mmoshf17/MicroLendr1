package com.example.mohib.microlendr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

 public class LendedLoanDetails extends AppCompatActivity {


        private LendedLoans lendedLoans;
        private TextView borrowerUserName;
        private TextView amount;
        private TextView amountRepaid;
        private TextView amountRemaining;
        private TextView repaywithin;
        private TextView installmentPaid;
        private TextView startingDate;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lended_loans_details);

            Intent intent1 = getIntent();
            lendedLoans = (LendedLoans) intent1.getSerializableExtra("LendedLoans");

            borrowerUserName = findViewById(R.id.lblLendedBorrower);
            borrowerUserName.setText("Borrower: " + lendedLoans.getBorrowerUserName());

            amount = findViewById(R.id.lblLendedAmount);
            amount.setText("Amount lended: " + lendedLoans.getAmount());

            repaywithin = findViewById(R.id.lblLendedRepayWithin);
            repaywithin.setText("Repay within months: " + lendedLoans.getRepaywithin());

            amountRepaid = findViewById(R.id.lblLendedAmountPaid);
            amountRepaid.setText("Amount paid: " + lendedLoans.getAmountRepaid());

            startingDate = findViewById(R.id.lblLendedStartingDate);
            startingDate.setText("First payment: " + lendedLoans.getStartingDate());

            amountRemaining = findViewById(R.id.lblLendedAmountRemaining);
            amountRemaining.setText("Amount remaining: " + lendedLoans.getAmountRemaining());

            installmentPaid = findViewById(R.id.lblLendedInstallmentPaid);
            installmentPaid.setText("Installment paid: " + lendedLoans.getInstallmentPaid());

        }


    }




