package com.example.mohib.microlendr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


    }

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@_.]).*$");

    public void onClickSignupBtn(View view) {

        EditText editTextFirstName = findViewById(R.id.txtFirstName);
        EditText editTextLastName = findViewById(R.id.txtLastName);
        EditText editTextPhone = findViewById(R.id.txtPhone);
        EditText editTextEmail = findViewById(R.id.txtEmail);
        EditText editTextPassword = findViewById(R.id.txtSignupPassword);


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        CheckBox chkbxTerms = findViewById(R.id.chkbxTerms);

        String passwordInput = editTextPassword.getText().toString().trim();


        if (editTextFirstName.getText().toString().isEmpty()) {
            editTextFirstName.setError("Field can't be empty");

        }

        else if (editTextLastName.getText().toString().isEmpty()) {
            editTextLastName.setError("Field can't be empty");

        }

        else if (editTextPhone.getText().toString().isEmpty()) {
            editTextPhone.setError("Field can't be empty");

        }

        else if (editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError("Field can't be empty");

        }

        else if (!editTextEmail.getText().toString().matches(emailPattern))
        {
            editTextEmail.setError("Email is incorrect");
        }

        else if (passwordInput.isEmpty()) {
            editTextPassword.setError("Field can't be empty");

        }

        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            editTextPassword.setError("The Password must consist of a minimum Length of 6 inputs, with at least one digit, one letter, one uppercase letter and one special character(@  , -  .)");

        }

        else if (!chkbxTerms.isChecked()) {

            Toast.makeText(getApplicationContext(), "\"Please accept the terms and conditions\"",
                    Toast.LENGTH_LONG).show();
        }





        else{

            new UserRegistering().execute();


        }
    }



    public void onClickTerms(View view) {

        Intent goToTermsConditions = new Intent(SignupActivity.this, TermsConditions.class);
        startActivity(goToTermsConditions);
        finish();

    }


    public class UserRegistering extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            EditText enterFirstName = (EditText) findViewById(R.id.txtFirstName);
            EditText enterLastName = (EditText) findViewById(R.id.txtLastName);
            EditText enterPhone = (EditText) findViewById(R.id.txtPhone);
            EditText enterEmail = (EditText) findViewById(R.id.txtEmail);
            EditText enterPassword = (EditText) findViewById(R.id.txtSignupPassword);


            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("FirstName", enterFirstName.getText());
                postDataParams.put("LastName", enterLastName.getText());
                postDataParams.put("PhoneNumber", enterPhone.getText());
                postDataParams.put("Email", enterEmail.getText());
                postDataParams.put("Password", enterPassword.getText());


                url = new URL("https://microlendrapiwebapp.azurewebsites.net/api/Account/Register");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();


                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();


                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                //Saving logged in user for showing it later on in Profile Settings
                String saveSignedUser = enterEmail.getText().toString();
                editor.putString("savedSignedUser", saveSignedUser);
                editor.apply();



                if (responseCode == HttpURLConnection.HTTP_OK) {


                    Intent goToAuthentication = new Intent(SignupActivity.this, Authentication.class);
                    startActivity(goToAuthentication);
                    finish();

                    String responseString = readStream(urlConnection.getInputStream());
                    String books = responseString;


                } else {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "\"Sign-up failed. Please try again\"",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;

        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }

}