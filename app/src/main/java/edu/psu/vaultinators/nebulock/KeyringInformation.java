package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class KeyringInformation extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyring_information);
	}
	
	// User has (or hasn't!) read the explanation of keyrings,
	// and has pressed the button to continue
	public void acceptKeyring(View view){
		Intent intent = new Intent(getApplicationContext(), CreateKeyring.class);
		startActivity(intent);
	}
}
