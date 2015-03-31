package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.psu.vaultinators.nebulock.util.ServerRequest;


public class NewVault extends Activity {
    String server = "http://146.186.64.169:6917/bin";
    private EditText vaultNameText = null;
    private EditText vaultDescriptionText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vault);
        vaultNameText = (EditText)findViewById(R.id.vaultNameText);
        vaultDescriptionText = (EditText)findViewById(R.id.vaultDescriptionText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_vault, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //def for reals
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitTheVault(View view){

        final String emailCred = (getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE)).getString(getString(R.string.email_key),"");
        final String passwordCred = (getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE)).getString(getString(R.string.password_key),"");
        Log.e("Message", "The submitVault button has been pressed");
        final String vaultName = vaultNameText.getText().toString();
        final String vaultDescription = vaultDescriptionText.getText().toString();
        if (vaultName.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter a vault name.", Toast.LENGTH_SHORT).show();
             }
        else{
            ServerRequest newVaultRequest = new ServerRequest() {

                @Override
                protected void onSuccess(JSONObject data) {
                    super.onSuccess(data);

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

            newVaultRequest
                    .setPath("bin/doCreateVault")
                    .setParameter("email", emailCred)
                    .setParameter("password", passwordCred)
                    .setParameter("vaultName", vaultName)
                    .setParameter("vaultDescription", vaultDescription)
                    .execute();
        }
    }
}
