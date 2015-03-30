package edu.psu.vaultinators.nebulock;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginScreen extends Activity {
    String server = "http://146.186.64.169:6917/bin";
    Boolean login = false;
	private EditText username = null;
	private EditText password = null;
    public final static String LOG_TAG = "nebulock";
	private Button loginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		username = (EditText)findViewById(R.id.editTextEmail);
		password = (EditText)findViewById(R.id.editTextPassword);
		loginButton = (Button)findViewById(R.id.loginButton);
	}
	
	public void login(View view){
        final String userCred = username.getText().toString();
        final String passwordCred = password.getText().toString();
        AsyncTask<String, Void, Void> LoginBackgroundTask = new AsyncTask<String, Void, Void> () {
            protected Void doInBackground(String... urls) {
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new NameValuePair() {
                    @Override
                    public String getName() {
                        return "email";
                    }
                    @Override
                    public String getValue() {
                        return userCred;
                    }
                });
                params.add(new NameValuePair() {
                    @Override
                    public String getName() {
                        return "password";
                    }

                    @Override
                    public String getValue() {
                        return passwordCred;
                    }
                });
                Log.e("URL", urls[0]);
                JSONObject json = JSONParser.makeHttpRequest(urls[0], "GET", params);

                try {
                    Log.e("URL2", urls[0]);
                    JSONArray res = json.getJSONArray("records");
                    if(res.isNull(0)){ //wrong credentials
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginScreen.this, "Incorrect credentials. Please try again.", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else { //correct credentials

                            runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginScreen.this, "Redirecting...", Toast.LENGTH_SHORT).show();
                                login = true;
                                Context context = getApplicationContext();
                                SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(getString(R.string.email_key), userCred);
                                editor.commit();
                                sharedPreferences = context.getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString(getString(R.string.password_key), passwordCred);
                                editor.commit();

                            }
                        });



                    }
                    return null;
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                    return null;
                }
            }
        };
        //TODO: Move this to onPostExecute
        if (login == true) {
            Intent intent = new Intent(this, VaultHomeScreen.class);
            startActivity(intent);
        }
        else {
            Log.e("FAILURE", "The login credentials were incorrect.");
        }
        LoginBackgroundTask.execute(server + "/login", "GET");


            //TODO: Add 1 to the lockdown counter


		}
	
	//Menu probably unneeded for login page
	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
	}*/
	
	public void createAccount(View view){
        Intent intent = new Intent(this,CreateAccount.class);
        startActivity(intent);
	}

	public void forgotPassword(View view){
		//TODO: open dialog box, send email to user, etc..
// get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(LoginScreen.this);
        View promptView = layoutInflater.inflate(R.layout.forgotpasswordprompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreen.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //check if email is in database
                        //if so
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
	}
	
}
