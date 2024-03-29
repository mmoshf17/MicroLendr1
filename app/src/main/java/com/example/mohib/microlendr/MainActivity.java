package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

    public class MainActivity extends AppCompatActivity {



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
            getLendedInfo.execute("https://microlendrapiwebapp.azurewebsites.net/api/Request/GetLendedInfo/?currentUserName=" + showLogUser);


            GetMustPayInfo getMustPayInfo = new GetMustPayInfo();
            getMustPayInfo.execute("https://microlendrapiwebapp.azurewebsites.net/api/Request/GetMustPayInfo/?currentUserName=" + showLogUser);

             GetBalance getBalance = new GetBalance();
             getBalance.execute("https://microlendrapiwebapp.azurewebsites.net/api/Request/GetBalance/?currentUserName=" + showLogUser);

        }

    }

    public void onClickloans(View view){

        Intent gotoLoans = new Intent(getApplicationContext(), Loans.class);
        startActivity(gotoLoans);
    }


    private class GetLendedInfo extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all lended information into list by using loop
            final List<LendedInfo> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String borrowerUserName = obj.getString("BorrowerUserName");
                    String amount = obj.getString("Amount");
                    String amountRepaid = obj.getString("AmountPaid");

                    LendedInfo lendedInfo = new LendedInfo(requestId, borrowerUserName, amount, amountRepaid);

                    loan.add(lendedInfo);

                }

                ListView listViewLendedto = findViewById(R.id.listViewLendedto);

                ArrayAdapter<LendedInfo> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.listview_format, loan);

                listViewLendedto.setAdapter(adapter);



            } catch (JSONException ex)
            {
                Log.e("LendedInfo", ex.getMessage());
            }


        }

    }


    private class GetMustPayInfo extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all tickets into list by using loop
            final List<MustPayInfo> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String lenderUserName = obj.getString("LenderUserName");
                    String amount = obj.getString("Amount");
                    String amountRepaid = obj.getString("AmountPaid");



                    MustPayInfo mustPayInfo = new MustPayInfo(requestId, lenderUserName, amount, amountRepaid);

                    loan.add(mustPayInfo);


                }


                ListView listViewLendedto = findViewById(R.id.listViewMust);
                ArrayAdapter<MustPayInfo> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.listview_format, loan);
                listViewLendedto.setAdapter(adapter);



            } catch (JSONException ex)
            {
                Log.e("MustPayInfo", ex.getMessage());
            }


        }

    }



    private class GetBalance extends ReadHttpTask {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String showLogUser = sharedPref.getString("savedUser", "");
        int sumLender;
        int sumBorrowed;
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all tickets into list by using loop
            final List<MustPayInfo> loan = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);


                    int requestId = obj.getInt("Id");
                    String lenderUserName = obj.getString("LenderUserName");
                    int amount = obj.getInt("Amount");
                    String borrowerUseName = obj.getString("BorrowerUserName");


                    if (lenderUserName.equals(showLogUser)){

                        sumLender += amount;

                    }

                    else if (borrowerUseName.equals(showLogUser)){

                        sumBorrowed += amount;
                    }



                }

                int res = sumLender - sumBorrowed;

                TextView listViewBalance =  findViewById(R.id.txtBalance);
                listViewBalance.setText("Balance: " + Integer.toString(res));



            } catch (JSONException ex)
            {
               // Log.e("MustPayInfo", ex.getMessage());
            }


        }

    }




   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

*/
    public void onClickProfile(View view) {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")) {

            Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent3);



        } else if (!Objects.equals(token, "")) {
            Intent intent4 = new Intent(getApplicationContext(), ProfileSettingsActivity.class);
            startActivity(intent4);


        }

    }



    public void onClickMyRequest(View view) {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")) {

            Intent intent5 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent5);


        } else if (!Objects.equals(token, "")) {
            Intent intent6 = new Intent(getApplicationContext(), MyRequests.class);
            startActivity(intent6);



        }


    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            BottomNavigationView navigation = findViewById(R.id.navigationView);
            navigation.getMenu().getItem(0).setChecked(true);
            navigation.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_Home:
                        item.setChecked(true);
                        Intent a = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_Requests:
                        item.setChecked(false);
                        Intent b = new Intent(MainActivity.this,MyRequests.class);
                        startActivity(b);
                        break;
                    case R.id.navigation_Loans:
                        item.setChecked(false);
                        Intent c = new Intent(MainActivity.this,Loans.class);
                        startActivity(c);
                        break;
                    case R.id.navigation_Settings:
                        item.setChecked(false);
                        Intent d = new Intent(MainActivity.this,ProfileSettingsActivity.class);
                        startActivity(d);
                        break;
                }
                return false;
            });
        }
    }



