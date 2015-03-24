package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
	}
	
	// Called when user has (or hasn't) entered information
	// into all fields, and presses "Next" button
	public void createAccount(View pew){
		//TODO: check for the following:
		//       email not in system
		//       email and confirm email fields match
		//       password is long enough or meets other requirements
		//       password and confirm password match
		//      then send out that information to server and whatnot
		
		
		//TODO: pop up dialog box, sent email, confirm pin, whatever
		//      THEN go to keyring information activity
		
		Intent intent = new Intent(getApplicationContext(), KeyringInformation.class);
		startActivity(intent); //TODO: destroy activity? shouldn't allow user to go back, right?
	}
}
