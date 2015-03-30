package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class CreateAccount extends Activity{
    String server = "http://146.186.64.169:6917/bin";
    private EditText newEmail = null;
    private EditText confirmEmail = null;
    private EditText newPassword = null;
    private EditText confirmPassword = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
        newEmail = (EditText)findViewById(R.id.editTextCreateAccountEmail);
        confirmEmail = (EditText)findViewById(R.id.editTextCreateAccountConfirmEmail);
        newPassword = (EditText)findViewById(R.id.editTextCreateAccountPassword);
        confirmPassword = (EditText)findViewById(R.id.editTextCreateAccountConfirmPassword);

	}
	
	// Called when user has (or hasn't) entered information
	// into all fields, and presses "Next" button
	public void createAccount(View pew){
		final String emailCred = newEmail.getText().toString();
        String confirmEmailCred = confirmEmail.getText().toString();
        final String passwordCred = newPassword.getText().toString();
        String confirmPasswordCred = confirmPassword.getText().toString();
        if(emailCred.equals("")){
            Toast.makeText(getApplicationContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if (passwordCred.equals("")){
            Toast.makeText(getApplicationContext(), "Confirm Email cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidEmailAddress(emailCred)) {
            Toast.makeText(getApplicationContext(), "Must be a valid email address.", Toast.LENGTH_SHORT).show();
        }
        else if (!emailCred.equals(confirmEmailCred)){
            Toast.makeText(getApplicationContext(), "Email and Confirm Email must be identical", Toast.LENGTH_SHORT).show();
        }
        else if (!passwordCred.equals(confirmPasswordCred)) {
            Toast.makeText(getApplicationContext(), "Password and Confirm Password must be identical.", Toast.LENGTH_SHORT).show();
        }
        else{
            AsyncTask<String, Void, Void> createAccountBackgroundTask = new AsyncTask<String, Void, Void> () {

                protected Void doInBackground(String... urls) {
                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new NameValuePair() {
                        @Override
                        public String getName() {
                            return "email";
                        }
                        @Override
                        public String getValue() {
                            return emailCred;
                        }
                    });

                    JSONObject json = JSONParser.makeHttpRequest(urls[0], "GET", params);

                    try {
                        JSONArray res = json.getJSONArray("records");
                        if(!res.isNull(0)){ //found email in the database - don't create the account
                            //if (json.getJSONArray("records").getJSONObject(0).getString("email") != null) {
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(CreateAccount.this, "The email specified is already associated with an account. Please try a different email.", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else { //email not found in database - create the account

                            //save the information the user entered to create the account later
                            Context context = getApplicationContext();
                            SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(getString(R.string.email_key), emailCred);
                            editor.commit();
                            sharedPreferences = context.getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString(getString(R.string.password_key), passwordCred);
                            editor.commit();

                            //TODO: Don't finish creating the user's account until they create security questions, keyring

                            //create the account
                            //TODO: fix the below to take params correctly, by generalizing the above
                            json = JSONParser.makeHttpRequest(server + "/createAccount?email=" + emailCred + "&password=" + passwordCred, "GET", new ArrayList<NameValuePair>());

                            //TODO: test to see if createAccount was a success
                            //TODO: Dialog box saying an account has been created

                            runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(CreateAccount.this, "Your account has been created. Enjoy Nebulock!", Toast.LENGTH_LONG).show();
                                }
                            });

                            finish();



                        }
                        return null;
                    }
                    catch(Exception e){
                        Log.e("ERROR", e.getMessage());
                        return null;
                    }
                }
            };


            createAccountBackgroundTask.execute(server + "/getAccount");

           //String getEmail = server + "/getAccount?email=" + emailCred;
            //JSONObject json = JSONParser.makeHttpRequest(getEmail, "GET", new ArrayList<NameValuePair>());

        }
        /*else {
            Toast.makeText(getApplicationContext(), "You alright.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), KeyringInformation.class);
            startActivity(intent);
            //TODO: destroy activity? shouldn't allow user to go back, right?
        }*/
	}

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }


}
