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


        //SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String token = sharedPref.getString("token", "");

        //HttpURLConnection httpURLConnection = null;




        //readTask.execute("https://microlendrapi.azurewebsites.net/api/Request/GetLoanRequests/?currentUserName=" + showLogUser);

        ReadTask readTask = new ReadTask();
        readTask.execute("https://microlendrapi.azurewebsites.net/api/Request/GetLoanRequests/?currentUserName=" + showLogUser);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
    }



    private class ReadTask extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all tickets into list by using loop
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


                ListView listView = findViewById(R.id.showMyRequests);
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
}
