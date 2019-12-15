package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Authentication extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
    }



    public void onClickVerify(View view) {

        new AcceptRequest().execute();
    }

    public class AcceptRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {


            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

            String savedSignedUser = sharedPref.getString("savedSignedUser", "");

            TextView vCode = findViewById(R.id.txtVerify);


            //https://microlendrapi.azurewebsites.net/api/Account/VerifyUser?email=sam@hotmail.com&code=6156




            URL url;
            HttpURLConnection urlConnection = null;


            try {
                //JSONObject postDataParams = new JSONObject();
                // postDataParams.put("Id", requestId.getText());
                //postDataParams.put("Status", acceptRequest.getText().toString());



                String token = sharedPref.getString("token", "");


                //url = new URL("https://microlendrapi.azurewebsites.net/api/Request/LoanRequestStatus");
                url = new URL("https://microlendrapi.azurewebsites.net/api/Account/VerifyUser?email=" + savedSignedUser + "&code=" + vCode.getText().toString());
                //url = new URL("http://localhost:56624/api/Values/CreateRequest");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Authorization", "Bearer " + token);

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                //PostDataString postDataString = new PostDataString();

                //writer.write(postDataString.getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();


                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    Intent intent = new Intent(Authentication.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Verification successful. Login to continue!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                } else {

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Incorrect verification code, Please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    });



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
