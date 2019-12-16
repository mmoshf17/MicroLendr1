package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoanRequestsDetail extends AppCompatActivity {


    private LoanRequests loanRequests;
    //private TextView requestId;
    private TextView borrowUserName;
    private TextView amount;
    private TextView repayWithinMonths;
    private TextView dateCreated;
    private TextView startingDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_requests_detail);


        Intent intent = getIntent();
        loanRequests = (LoanRequests) intent.getSerializableExtra("LoanRequests");

        borrowUserName = findViewById(R.id.lblborrowerUserName);
        borrowUserName.setText("Borrow's UserName: " + loanRequests.getBorrowerUserName());

        amount = findViewById(R.id.lblAmount);
        amount.setText("Amount requested: " + loanRequests.getAmount());

        repayWithinMonths = findViewById(R.id.lblRepayWithin);
        repayWithinMonths.setText("Repay Within Months: " + loanRequests.getRepayWithin());

        dateCreated = findViewById(R.id.lblDateCreated);
        dateCreated.setText("Date Created: " + loanRequests.getDateCreated());

        startingDate = findViewById(R.id.lblStartingDate);
        startingDate.setText("Payment's starting date: " + loanRequests.getStartingDate());
    }

    public void btnAcceptRequest(View view) {

        new AcceptRequest().execute();
    }



    public class AcceptRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            Button acceptRequest = findViewById(R.id.btnAccept);

            URL url;
            HttpURLConnection urlConnection = null;


            try {

                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");


                  url = new URL("https://microlendrapi.azurewebsites.net/api/Request/LoanRequestStatus?currentRequestId=" + loanRequests.getRequestId() + "&status=" + acceptRequest.getText().toString());


                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Authorization", "Bearer " + token);

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));


                writer.flush();
                writer.close();
                os.close();


                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    Intent intent = new Intent(LoanRequestsDetail.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                    Intent intentLogin = new Intent(LoanRequestsDetail.this, MainActivity.class);
                    startActivity(intentLogin);
                    finish();

                    Toast.makeText(getApplicationContext(), "Please login/signup, to sell a ticket.",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }
    }

    public void btnRejectRequest(View view) {

        new RejectRequest().execute();
    }

    public class RejectRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            Button rejectRequest = findViewById(R.id.btnReject);




            URL url;
            HttpURLConnection urlConnection = null;


            try {

                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");

                url = new URL("https://microlendrapi.azurewebsites.net/api/Request/LoanRequestStatus?currentRequestId=" + loanRequests.getRequestId() + "&status=" + rejectRequest.getText().toString());

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Authorization", "Bearer " + token);

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));


                writer.flush();
                writer.close();
                os.close();


                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    Intent intent = new Intent(LoanRequestsDetail.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                    Intent intentLogin = new Intent(LoanRequestsDetail.this, MainActivity.class);
                    startActivity(intentLogin);
                    finish();

                    Toast.makeText(getApplicationContext(), "Please login/signup, to sell a ticket.",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }
    }
}






