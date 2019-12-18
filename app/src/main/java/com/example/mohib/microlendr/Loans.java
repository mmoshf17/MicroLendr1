package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

  public class Loans extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");
        String showLogUser = sharedPref.getString("savedUser", "");

        if (Objects.equals(token, "")) {

            Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(goToLogin);

        } else if (!Objects.equals(token, "")) {

            GetLendedInfo getLendedInfo = new GetLendedInfo();
            getLendedInfo.execute("https://microlendrapi.azurewebsites.net/api/Request/GetLendedLoan/?currentUserName=" + showLogUser);


            GetBorrowedInfo getBorrowedInfo = new GetBorrowedInfo();
            getBorrowedInfo.execute("https://microlendrapi.azurewebsites.net/api/Request/GetBorrowedLoan/?currentUserName=" + showLogUser);


            GetUpcomingPayments getUpcomingPayments = new GetUpcomingPayments();
            getUpcomingPayments.execute("https://microlendrapi.azurewebsites.net/api/Request/GetUpcomingPayments/?currentUserName=" + showLogUser);


            GetUpcomingPaybacks getUpcomingPaybacks = new GetUpcomingPaybacks();
            getUpcomingPaybacks.execute("https://microlendrapi.azurewebsites.net/api/Request/GetUpcomingPaybacks/?currentUserName=" + showLogUser);

        }

    }






      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_loans);

          BottomNavigationView navigation = findViewById(R.id.navigationView);
          navigation.setOnNavigationItemSelectedListener(item -> {
              switch (item.getItemId()) {
                  case R.id.navigation_Home:
                      item.setChecked(false);
                      Intent a = new Intent(Loans.this,MainActivity.class);
                      startActivity(a);
                      break;
                  case R.id.navigation_Requests:
                      item.setChecked(false);
                      Intent b = new Intent(Loans.this,MyRequests.class);
                      startActivity(b);
                      break;
                  case R.id.navigation_Loans:
                      item.setChecked(true);
                      Intent c = new Intent(Loans.this,Loans.class);
                      startActivity(c);
                      break;
                  case R.id.navigation_Settings:
                      item.setChecked(false);
                      Intent d = new Intent(Loans.this,ProfileSettingsActivity.class);
                      startActivity(d);
                      break;
              }
              return false;
          });
      }

    private class GetLendedInfo extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {



            //Gets the data from database and show all info into list by using loop
            final List<LendedLoans> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String borrowerUserName = obj.getString("BorrowerUserName");
                    String amount = obj.getString("Amount");
                    String amountRepaid = obj.getString("AmountPaid");
                    String amountRemaining = obj.getString("AmountRemaining");
                    String repayWithin = obj.getString("RepayWithin");
                    String installmentPaid = obj.getString("InstallmentPaid");
                    String startingDate = obj.getString("StartingDate");

                    LendedLoans lendedLoans = new LendedLoans(requestId, borrowerUserName, amount, amountRepaid, amountRemaining,
                            repayWithin, installmentPaid, startingDate);

                    loan.add(lendedLoans);

                }

                ListView listViewLended = (ListView) findViewById(R.id.listViewLended);

                ArrayAdapter<LendedLoans> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.listview_format, loan);

                listViewLended.setAdapter(adapter);

                listViewLended.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                    Intent intent = new Intent(getBaseContext(), LendedLoanDetails.class);
                    LendedLoans lendedLoans = (LendedLoans) parent.getItemAtPosition(position);
                    intent.putExtra("LendedLoans", lendedLoans);

                    startActivity(intent);
                });



            } catch (JSONException ex)
            {
                Log.e("LendedLoans", ex.getMessage());
            }


        }

    }


    private class GetBorrowedInfo extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<BorrowedLoans> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String lenderUserName = obj.getString("LenderUserName");
                    String amount = obj.getString("Amount");
                    String amountRepaid = obj.getString("AmountPaid");
                    String amountRemaining = obj.getString("AmountRemaining");
                    String repayWithin = obj.getString("RepayWithin");
                    String installmentPaid = obj.getString("InstallmentPaid");
                    String startingDate = obj.getString("StartingDate");

                    BorrowedLoans borrowedLoans = new BorrowedLoans(requestId, lenderUserName, amount, amountRepaid, amountRemaining,
                            repayWithin, installmentPaid, startingDate);

                    loan.add(borrowedLoans);

                }

                ListView listViewBorrowed = (ListView) findViewById(R.id.listViewBorrowed);

                ArrayAdapter<BorrowedLoans> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.listview_format, loan);

                listViewBorrowed.setAdapter(adapter);

                listViewBorrowed.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                    Intent goToBorrowedLoanDetails = new Intent(getBaseContext(), BorrowedLoanDetails.class);
                    BorrowedLoans borrowedLoans = (BorrowedLoans) parent.getItemAtPosition(position);
                    goToBorrowedLoanDetails.putExtra("BorrowedLoans", borrowedLoans);

                    startActivity(goToBorrowedLoanDetails);
                });




            } catch (JSONException ex)
            {
                Log.e("BorrowedLoans", ex.getMessage());
            }


        }

    }

    private class GetUpcomingPayments extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<UpcomingPayments> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String borrowerUserName = obj.getString("BorrowerUserName");
                    String amountRepaid = obj.getString("AmountPaid");


                    UpcomingPayments upcomingPayments = new UpcomingPayments(requestId, borrowerUserName, amountRepaid);

                    loan.add(upcomingPayments);

                }

                ListView listViewUpcomingPay = (ListView) findViewById(R.id.listViewUpcomingPayments);

                ArrayAdapter<UpcomingPayments> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.listview_format, loan);

                listViewUpcomingPay.setAdapter(adapter);

                /*listViewUpcomingPay.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                    Intent goToBorrowedLoanDetails = new Intent(getBaseContext(), BorrowedLoanDetails.class);
                    BorrowedLoans borrowedLoans = (BorrowedLoans) parent.getItemAtPosition(position);
                    goToBorrowedLoanDetails.putExtra("BorrowedLoans", borrowedLoans);

                    startActivity(goToBorrowedLoanDetails);
                });*/




            } catch (JSONException ex)
            {
                Log.e("UpcomingPayments", ex.getMessage());
            }


        }

    }

    private class GetUpcomingPaybacks extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<UpcomingPaybacks> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String lenderUserName = obj.getString("LenderUserName");
                    String amountRepaid = obj.getString("AmountPaid");


                    UpcomingPaybacks upcomingPaybacks = new UpcomingPaybacks(requestId, lenderUserName, amountRepaid);

                    loan.add(upcomingPaybacks);

                }

                ListView listViewPaybacks = (ListView) findViewById(R.id.listViewUpcomingPaybacks);

                ArrayAdapter<UpcomingPaybacks> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.listview_format, loan);

                listViewPaybacks.setAdapter(adapter);

                /*listViewUpcomingPay.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                    Intent goToBorrowedLoanDetails = new Intent(getBaseContext(), BorrowedLoanDetails.class);
                    BorrowedLoans borrowedLoans = (BorrowedLoans) parent.getItemAtPosition(position);
                    goToBorrowedLoanDetails.putExtra("BorrowedLoans", borrowedLoans);

                    startActivity(goToBorrowedLoanDetails);
                });*/




            } catch (JSONException ex)
            {
                Log.e("UpcomingPaybacks", ex.getMessage());
            }


        }

    }






}
