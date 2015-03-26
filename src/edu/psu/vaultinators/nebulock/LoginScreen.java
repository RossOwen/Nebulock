package edu.psu.vaultinators.nebulock;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
			Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show(); //TODO: Actually login
		}	
		else{
			Toast.makeText(getApplicationContext(), "Wrong Credentials, idiot",
					Toast.LENGTH_SHORT).show();
		}
	
	//Menu probably unneeded for login page
	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
	}*/
	}
	
	public void createAccount(){
		Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
		startActivity(intent);
	}
	
	public void forgotPassword(){
		//TODO: open dialog box, send email to user, etc..
	}
	
}
