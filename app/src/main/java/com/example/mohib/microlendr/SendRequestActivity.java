package com.example.mohib.microlendr;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
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
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.chrono.MinguoChronology;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class SendRequestActivity extends AppCompatActivity {



    @Override
    protected void onStart(){
        super.onStart();

//Hiding day from the date


        DatePicker startingDate = findViewById(R.id.clndStartingDate);
        startingDate.init(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),



                new DatePicker.OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker view,int year, int monthOfYear,int dayOfMonth) {

                    }
                });
        int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
        if (daySpinnerId != 0)
        {
            View daySpinner = startingDate.findViewById(daySpinnerId);
            if (daySpinner != null)
            {
                daySpinner.setVisibility(View.INVISIBLE);
            }
        }



        }


    //Getting date
    /*public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = 01;
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }*/

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

                DatePicker startingDate = findViewById(R.id.clndStartingDate);

                EditText userName = findViewById(R.id.txtSendRequestUserName);
                EditText amount = findViewById(R.id.txtSendRequestAmount);
                EditText repayWithinMonths = findViewById(R.id.txtSendRequestRepayMonths);


                int day = 1;
                int month = startingDate.getMonth();
                int year =  startingDate.getYear();


                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy");
                String calendar = dateformat.format(c.getTime());






                URL url;
                HttpURLConnection urlConnection = null;



                try {
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("LenderUserName", userName.getText());
                    postDataParams.put("Amount", amount.getText());
                    postDataParams.put("RepayWithin", repayWithinMonths.getText());
                    postDataParams.put("StartingDate", calendar);



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


