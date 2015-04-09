package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


import edu.psu.vaultinators.nebulock.util.SecureServerRequest;
import edu.psu.vaultinators.nebulock.util.ServerRequest;

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
        else if (!checkPassword(passwordCred)){
            Toast.makeText(CreateAccount.this, "Password must contain at least seven characters, at least one number, one uppercase and one lowercase digit, and no whitespace. Please try another password.", Toast.LENGTH_LONG).show();
        }
        else if (!passwordCred.equals(confirmPasswordCred)) {
            Toast.makeText(getApplicationContext(), "Password and Confirm Password must be identical.", Toast.LENGTH_SHORT).show();
        }
        else{

            ServerRequest createAccountRequest = new SecureServerRequest() {

                @Override
                protected void onSuccess(JSONObject data) {

                    // Inform the user that they've logged in
                    Toast.makeText(CreateAccount.this, "Your account has been created. Enjoy Nebulock!", Toast.LENGTH_LONG).show();

                    // Finish Activity
                    finish();

                }

                @Override
                protected void onFailure(String message, JSONObject data) {
                    super.onFailure(message, data);
                }

                @Override
                protected void onError(String message, Integer code, JSONObject data) {
                    super.onError(message, code, data);
                }
            };

            createAccountRequest
                .setPath("bin/createAccount")
                .setParameter("email", emailCred)
                .setParameter("password", passwordCred)
                .execute();

        }
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

    public boolean checkPassword(String password){
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // length between 7 and 30 characters
                new LengthRule(7, 30),

                // at least one upper-case character
                new UppercaseCharacterRule(1),

                // at least one lower-case character
                new LowercaseCharacterRule(1),

                // at least one digit character
                new DigitCharacterRule(1),

                // no whitespace
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }

}
