package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class KeyringInformation extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyring_information);
	}
	
	// User has (or hasn't!) read the explanation of keyrings,
	// and has pressed the button to continue
	public void buttonPressed(){
		Intent intent = new Intent(getApplicationContext(), CreateKeyring.class);
		startActivity(intent);
	}
}
