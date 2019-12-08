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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        Information information = new Information();

        information.doInBackground();
    }


    public class Information extends AsyncTask<String, String, String> {


        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String savedSignedUser = sharedPref.getString("savedSignedUser", "");

        TextView vCode = findViewById(R.id.txtVerify);

        @Override
        protected String doInBackground(String... params) {

            try {
                // Creating & connection Connection with url and required Header.
                URL url = new URL("https://microlendrapi.azurewebsites.net/api/Account/VerifyUser?email=sam@hotmail.com&code=6156");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                //urlConnection.setRequestProperty("header-param_3", "value-3");
                //urlConnection.setRequestProperty("header-param_4", "value-4");
                //urlConnection.setRequestProperty("Authorization", "Basic Y2tfNDIyODg0NWI1YmZiZT1234ZjZWNlOTA3ZDYyZjI4MDMxY2MyNmZkZjpjc181YjdjYTY5ZGM0OTUwODE3NzYwMWJhMmQ2OGQ0YTY3Njk1ZGYwYzcw");
                urlConnection.setRequestMethod("GET");   //POST or GET
                urlConnection.connect();

                // Create JSONObject Request
//                JSONObject jsonRequest = new JSONObject();
//                jsonRequest.put("email", savedSignedUser);
//                jsonRequest.put("code",vCode.getText());

                // Write Request to output stream to server.
//                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//                out.write(jsonRequest.toString());
//                out.close();

                // Check the connection status.
                int statusCode = urlConnection.getResponseCode();
                String statusMsg = urlConnection.getResponseMessage();

                // Connection success. Proceed to fetch the response.
                if (statusCode == 200) {
                    InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    StringBuilder dta = new StringBuilder();
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        dta.append(chunks);
                    }
                    String returndata = dta.toString();
                    return returndata;
                } else {
                    //Handle else case
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }

}
