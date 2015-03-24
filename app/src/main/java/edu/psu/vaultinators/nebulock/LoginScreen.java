package edu.psu.vaultinators.nebulock;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends Activity {


	private EditText username = null;
	private EditText password = null;
	private Button loginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		username = (EditText)findViewById(R.id.editTextEmail);
		password = (EditText)findViewById(R.id.editTextPassword);
		loginButton = (Button)findViewById(R.id.loginButton);
	}

	//TODO: Link forgot password and create account textViews to activities
	//TODO: Link loginButton to login Dialog box thingy
	
	public void login(View view){
		if(username.getText().toString().equals("admin") && 
				password.getText().toString().equals("admin")){
			Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VaultHomeScreen.class);
            startActivity(intent);

		}	
		else{
			Toast.makeText(getApplicationContext(), "Wrong Credentials, please try again.",
					Toast.LENGTH_SHORT).show();

            //TODO: Add 1 to lockdown counter


		}
	
	//Menu probably unneeded for login page
	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
	}*/
	}
	
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
