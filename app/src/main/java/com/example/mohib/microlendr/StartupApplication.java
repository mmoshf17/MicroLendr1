package com.example.mohib.microlendr;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

public class StartupApplication extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);


        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String toggleFingerprintValue = sharedPref.getString("toggleFingerprint", "");

        if (Objects.equals(toggleFingerprintValue, "ON")) {


            Intent goToFingerprint = new Intent(getApplicationContext(), FingerPrint.class);
            startActivity(goToFingerprint);
            finish();


        }

       else if (!Objects.equals(toggleFingerprintValue, "ON")) {


            Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(goToLogin);
            finish();

        }

    }
}
