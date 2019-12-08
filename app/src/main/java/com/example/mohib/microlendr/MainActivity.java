package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }





    public void onClick(View view) {


        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);

    }


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

    public void onClickSendRequest(View view) {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")) {

            Intent intent5 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent5);


        } else if (!Objects.equals(token, "")) {
            Intent intent6 = new Intent(getApplicationContext(), SendRequestActivity.class);
            startActivity(intent6);

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

    public void onClickVerify(View view) {


        Intent goToAuthentication = new Intent(MainActivity.this, Authentication.class);
        startActivity(goToAuthentication);
        finish();

    }
}


