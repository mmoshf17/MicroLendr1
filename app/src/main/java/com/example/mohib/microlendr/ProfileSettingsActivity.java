package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;



public class ProfileSettingsActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();


        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String showLogUser = sharedPref.getString("savedUser", "");

        TextView showLoggedInUser = (TextView) findViewById(R.id.txtLoggedinAs);

        showLoggedInUser.setText(showLogUser);

        //ReadTask task3 = new ReadTask();
        //task3.execute("https://microlendrapi.azurewebsites.net/api/GetUserInfo?userId=7069e6cf-4f03-4838-aafd-4e301dd11e7e");


        //task3.execute("https://microlendrapi.azurewebsites.net/api/GetUserInfo?userId=" + getLoggedInUser.getUserID());
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
}


    /*private class ReadTask extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {
            //TextView messageTextView = findViewById(R.id.show_list);

                TextView showLoggedInUser = (TextView) findViewById(R.id.txtLoggedinAs);

            //final List<GetLoggedInUser> getUser = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());

                    JSONObject obj = array.getJSONObject(0);
                    //Get the following data from Database

                   // String email = obj.toString();

                   // String email = obj.toString();

                String email = obj.getString("Username");

                    //Adding values to the list
                    //getUser.add(email.toString());
                    showLoggedInUser.setText(email);




            } catch (JSONException ex) {
                //messageTextView.setText(ex.getMessage());
                Log.e("Tickets", ex.getMessage());
            }


        }
    }*/


