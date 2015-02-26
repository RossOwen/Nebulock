package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CreateKeyring extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_keyring);
	}
	
	
	// Called when user clicks "Done!" button after entering
	// information into master password and security question fields
	public void createKeyring(){
		//TODO: Check for the following:
		//       Master password matches confirm master password field
		//       No fields are empty
		//       Fields are certain length
		//      Also implement and display password strength indicator
		//
		//      Pop up a dialog box saying thank you!, then navigate to vault home screen
		
		Intent intent = new Intent(getApplicationContext(), VaultHomeScreen.class);
		startActivity(intent);
	}
}
