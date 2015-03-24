package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class NewVault extends Activity {
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitTheVault(){
        String vaultName = vaultNameText.getText().toString();
        String vaultDescription = vaultDescriptionText.getText().toString();
        if (vaultName.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter a vault name.", Toast.LENGTH_SHORT).show();
            //TODO: else: Create the vault
        }
    }
}
