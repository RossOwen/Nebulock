package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import edu.psu.vaultinators.nebulock.util.SecureServerRequest;
import edu.psu.vaultinators.nebulock.util.ServerRequest;


public class addEntry extends Activity {
    private EditText entryNameText = null;
    private EditText entryContentsText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        entryNameText = (EditText)findViewById(R.id.addEntryNameEditText);
        entryContentsText = (EditText)findViewById(R.id.addEntryContentsEditText);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNewEntryToVault(View view){

        final String emailCred = (getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE)).getString(getString(R.string.email_key),"");
        final String passwordCred = (getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE)).getString(getString(R.string.password_key),"");
        final String vaultID = (getSharedPreferences(getString(R.string.vault_id_key), Context.MODE_PRIVATE)).getString(getString(R.string.vault_id_key), "-1");
        Log.e("Message", "The submitEntry button has been pressed");
        final String entryName = entryNameText.getText().toString();
        final String entryContents = entryContentsText.getText().toString();
        if (entryName.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter a name for the entry.", Toast.LENGTH_SHORT).show();
        }
        else{
            ServerRequest newVaultRequest = new SecureServerRequest() {

                @Override
                protected void onSuccess(JSONObject data) {
                    Toast.makeText(getApplicationContext(), "Entry added to Vault " + vaultID, Toast.LENGTH_SHORT).show();
                    finish();
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
                    .setPath("bin/doCreateEntry")
                    .setParameter("email", emailCred)
                    .setParameter("password", passwordCred)
                    .setParameter("vaultID", vaultID)
                    .setParameter("entryName", entryName)
                    .setParameter("text", entryContents)
                    .execute();
        }

    }
}
