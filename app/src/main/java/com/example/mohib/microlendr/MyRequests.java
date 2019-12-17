package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class MyRequests extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String showLogUser = sharedPref.getString("savedUser", "");


        String token = sharedPref.getString("token", "");


        GetReceivedRequests receivedRequests = new GetReceivedRequests();
        receivedRequests.execute("https://microlendrapi.azurewebsites.net/api/Request/GetLoanRequests/?currentUserName=" + showLogUser);

        GetAcceptedRequests acceptedRequests = new GetAcceptedRequests();
        acceptedRequests.execute("https://microlendrapi.azurewebsites.net/api/Request/GetAcceptedRequests/?currentUserName=" + showLogUser);

        GetRejectedRequests rejectedRequests = new GetRejectedRequests();
        rejectedRequests.execute("https://microlendrapi.azurewebsites.net/api/Request/GetRejectedRequests/?currentUserName=" + showLogUser);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
    }

    public void onClickSendRequest(View view) {


        Intent goToSendRequestActivity = new Intent(getApplicationContext(), SendRequestActivity.class);
        startActivity(goToSendRequestActivity);

    }


    private class GetReceivedRequests extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<LoanRequests> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    int requestId = obj.getInt("Id");
                    String borrowerUserName = obj.getString("BorrowerUserName");
                    String amount = obj.getString("Amount");
                    String repayWithinMonths = obj.getString("RepayWithin");
                    String startingDate = obj.getString("DateCreated");
                    String dateCreated = obj.getString("StartingDate");

                    LoanRequests loanRequests = new LoanRequests(requestId, borrowerUserName, amount, repayWithinMonths, startingDate, dateCreated);

                    loan.add(loanRequests);
                }

                ListView listView = findViewById(R.id.showReceivedRequests);
                ArrayAdapter<LoanRequests> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, loan);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                    Intent goToLoanRequestsDetail = new Intent(getBaseContext(), LoanRequestsDetail.class);
                    LoanRequests loanRequests = (LoanRequests) parent.getItemAtPosition(position);
                    goToLoanRequestsDetail.putExtra("LoanRequests", loanRequests);

                    startActivity(goToLoanRequestsDetail);
                });
            } catch (JSONException ex)
            {
                //messageTextView.setText(ex.getMessage());
                Log.e("LoanRequest", ex.getMessage());
            }


        }
    }

    private class GetAcceptedRequests extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {



            //Gets the data from database and show all info into list by using loop
            final List<AcceptedRequests> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String borrowerUserName = obj.getString("BorrowerUserName");
                    String amount = obj.getString("Amount");



                    AcceptedRequests acceptedRequests = new AcceptedRequests(requestId, borrowerUserName, amount);

                    loan.add(acceptedRequests);


                }


                ListView listView = findViewById(R.id.showAcceptedRequests);
                ArrayAdapter<AcceptedRequests> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, loan);
                listView.setAdapter(adapter);
               // listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                   // Intent goToLoanRequestsDetail = new Intent(getBaseContext(), LoanRequestsDetail.class);
                  //  LoanRequests loanRequests = (LoanRequests) parent.getItemAtPosition(position);
                   // goToLoanRequestsDetail.putExtra("LoanRequests", loanRequests);

                   // startActivity(goToLoanRequestsDetail);
                //});
            } catch (JSONException ex)
            {
                //messageTextView.setText(ex.getMessage());
                Log.e("AcceptedRequests", ex.getMessage());
            }


        }
    }

    private class GetRejectedRequests extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<RejectedRequests> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String borrowerUserName = obj.getString("BorrowerUserName");
                    String amount = obj.getString("Amount");

                    RejectedRequests rejectedRequests = new RejectedRequests(requestId, borrowerUserName, amount);

                    loan.add(rejectedRequests);


                }


                ListView listView = findViewById(R.id.showRejectedRequests);
                ArrayAdapter<RejectedRequests> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, loan);
                listView.setAdapter(adapter);
//                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
//
//                    Intent goToLoanRequestsDetail = new Intent(getBaseContext(), LoanRequestsDetail.class);
//                    LoanRequests loanRequests = (LoanRequests) parent.getItemAtPosition(position);
//                    goToLoanRequestsDetail.putExtra("LoanRequests", loanRequests);
//
//                    startActivity(goToLoanRequestsDetail);
//                });
            } catch (JSONException ex)
            {
                Log.e("RejectedRequests", ex.getMessage());
            }


        }
    }
}
