package com.example.mohib.microlendr;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi ( Build.VERSION_CODES.M )
class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

    private Context context;

    public  FingerprintHandler(Context context){

        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();

        fingerprintManager.authenticate(cryptoObject, cancellationSignal , 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString){

        this.update("There was an Authentication error." + errString, false);
    }

    @Override
    public void onAuthenticationFailed(){

        this.update("Authentication Failed", false );
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString){

        this.update("Error: " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){

        this.update("Successful", true);
    }

    private void update(String s , boolean b){

        TextView textView = (TextView) ((Activity)context).findViewById(R.id.txtFingerprintInfo);
        ImageView imgView = (ImageView) ((Activity)context).findViewById(R.id.imgFingerprint);

        textView.setText(s);

        if (b == false){

            textView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        else{

            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imgView.setImageResource(R.mipmap.fingerprintdone);

            Intent intent4 = new Intent(context.getApplicationContext( ) , MainActivity.class);
            context.startActivity(intent4);
            ((Activity) context).finish();
            Toast.makeText(context.getApplicationContext( ) , "\"Login Successful\"",
                    Toast.LENGTH_LONG).show();

        }

    }



}
