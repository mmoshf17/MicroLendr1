package com.example.mohib.microlendr;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FingerPrint extends AppCompatActivity {


    private TextView textView;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private Button btnGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        textView = findViewById(R.id.txtFingerprintInfo);
        btnGotoLogin = findViewById(R.id.btnGotoLogin);


        // Check: Android version is greater or equal to Marshmallow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){


        }
        // Check: If the device is fingerprint compitable
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        if (!fingerprintManager.isHardwareDetected()){

            textView.setText("Fingerprint Scanner not detected in Device");

            btnGotoLogin.setVisibility(View.VISIBLE);

        }

        // Check: permission to use fingerpirnt in app
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){

            textView.setText("Permission not granted to use Fingerprint");

            btnGotoLogin.setVisibility(View.VISIBLE);
        }


        // Check: There should be at lease one type of lock security on the phone to use the fingerprint
        else if (!keyguardManager.isKeyguardSecure()){

            textView.setText("Add a lock system on your phone, to use fingerprint");

            btnGotoLogin.setVisibility(View.VISIBLE);
        }


        // Check: At least one fingerprint is registered with your phone
        else if(!fingerprintManager.hasEnrolledFingerprints()){

            textView.setText("Add at lest one fingerprint in order to use fingerprint feature");

            btnGotoLogin.setVisibility(View.VISIBLE);
        }

        else {

            textView.setText("Please place your finger on the fingerprint scanner to login");



            FingerprintHandler fingerprintHandler = new FingerprintHandler(this);

            fingerprintHandler.startAuth(fingerprintManager, null);

        }


        btnGotoLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });


    }

}
