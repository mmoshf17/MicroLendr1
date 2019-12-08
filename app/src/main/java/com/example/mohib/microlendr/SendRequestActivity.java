package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.chrono.MinguoChronology;
import java.util.Date;
import java.util.Iterator;

public class SendRequestActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

    }


    public void onClickSendRequest(View view) {

        new HttpClient3().execute();


    }

    //...............................................................................................................

        public class HttpClient3 extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... params) {


                EditText userName = findViewById(R.id.txtSendRequestUserName);
                EditText amount = findViewById(R.id.txtSendRequestAmount);
                EditText repayWithinMonths = findViewById(R.id.txtSendRequestRepayMonths);
                CalendarView startingDate = findViewById(R.id.clndStartingDate);


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDate = sdf.format(new Date(startingDate.getDate()));

                URL url;
                HttpURLConnection urlConnection = null;



                try {
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("LenderUserName", userName.getText());
                    postDataParams.put("Amount", amount.getText());
                    postDataParams.put("RepayWithin", repayWithinMonths.getText());
                    postDataParams.put("StartingDate", selectedDate);



                    SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                    String token = sharedPref.getString("token", "");



                    url = new URL("https://microlendrapi.azurewebsites.net/api/Request/CreateRequest");
                    //url = new URL("http://localhost:56624/api/Values/CreateRequest");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestProperty("Authorization", "Bearer " + token);

                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);

                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    PostDataString postDataString = new PostDataString();

                    writer.write(postDataString.getPostDataString(postDataParams));

                    writer.flush();
                    writer.close();
                    os.close();


                    int responseCode = urlConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                        Intent intent = new Intent(SendRequestActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                        Intent intentLogin = new Intent(SendRequestActivity.this, MainActivity.class);
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


