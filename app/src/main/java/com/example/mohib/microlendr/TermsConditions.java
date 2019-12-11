package com.example.mohib.microlendr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TermsConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);


    }

    public void onClickTermsBtn(View view) {

        TextView txtGDPRLink = findViewById(R.id.textGDPR);

        String url = txtGDPRLink.getText().toString();
        Intent goToWeb = new Intent(Intent.ACTION_VIEW);
        goToWeb.setData(Uri.parse(url));
        startActivity(goToWeb);
    }
}
