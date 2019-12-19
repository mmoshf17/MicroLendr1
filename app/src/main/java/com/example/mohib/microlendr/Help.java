package com.example.mohib.microlendr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

public class Help extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.getMenu().getItem(3).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    Intent a = new Intent(Help.this,MainActivity.class);
                    startActivity(a);
                    break;
                case R.id.navigation_Requests:
                    Intent b = new Intent(Help.this,MyRequests.class);
                    startActivity(b);
                    break;
                case R.id.navigation_Loans:
                    Intent c = new Intent(Help.this,Loans.class);
                    startActivity(c);
                    break;
                case R.id.navigation_Settings:
                    Intent d = new Intent(Help.this,ProfileSettingsActivity.class);
                    startActivity(d);
                    break;
            }
            return false;
        });
    }
}
