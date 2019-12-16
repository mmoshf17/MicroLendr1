package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;


public class ProfileSettingsActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();


        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String showLogUser = sharedPref.getString("savedUser", "");

        String toggleFingerprintState = sharedPref.getString("toggleFingerprint", "");

        TextView showLoggedInUser = findViewById(R.id.txtLoggedinAs);

        ToggleButton toggleButton = findViewById(R.id.toggleFingerPrint);
        toggleButton.setText(toggleFingerprintState);


        showLoggedInUser.setText(showLogUser);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

    }
    public void onClickLogoutBtn(View view) {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();

        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(goToLogin);
        finish();
    }



    public void onClickHelp(View view) {

        Intent goToHelp = new Intent(getApplicationContext(), Help.class);
        startActivity(goToHelp);
        finish();

    }

    public void onClickToggleFingerprint(View view){

        ToggleButton toggleFingerprintValue = (ToggleButton) findViewById(R.id.toggleFingerPrint);
        CharSequence getToggleFingerprintValue = toggleFingerprintValue.getText();

        //Saving Toggle button's value to invoke fingerprint on startup
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("toggleFingerprint", getToggleFingerprintValue.toString());
        editor.apply();
    }
}



