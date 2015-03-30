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
        Log.e("Message", "The submitVault button has been pressed");
        final String vaultName = vaultNameText.getText().toString();
        final String vaultDescription = vaultDescriptionText.getText().toString();
        final String email = (getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE)).getString(getString(R.string.email_key),"");
        final String password = (getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE)).getString(getString(R.string.password_key),"");
        if (vaultName.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter a vault name.", Toast.LENGTH_SHORT).show();
             }
        else{
            AsyncTask<String, Void, Void> newVaultBackgroundTask = new AsyncTask<String, Void, Void> () {

                protected Void doInBackground(String... urls) {
                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new NameValuePair() {
                        @Override
                        public String getName() {
                            return "vaultName";
                        }
                        @Override
                        public String getValue() {
                            return vaultName;
                        }
                    });
                    params.add(new NameValuePair() {
                        @Override
                        public String getName() {
                            return "vaultDescription";
                        }
                        @Override
                        public String getValue() {
                            return vaultDescription;
                        }
                    });
                    params.add(new NameValuePair() {
                        @Override
                        public String getName() {
                            return "email";
                        }
                        @Override
                        public String getValue() {return email; }
                    });
                    params.add(new NameValuePair() {
                        @Override
                        public String getName() {
                            return "password";
                        }
                        @Override
                        public String getValue() {
                            return password;
                        }
                    });

                    final JSONObject json = JSONParser.makeHttpRequest(urls[0], "GET", params);

                    try {
                        String status = json.getString("result");

                        Log.e("JSONOUTPUT", json.toString());
                        if(status.equals("success")){ //found email in the database - don't create the account
                            //if (json.getJSONArray("records").getJSONObject(0).getString("email") != null) {
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(NewVault.this, "The Vault has been created", Toast.LENGTH_LONG).show();
                                }
                            });
                            finish();
                        } else if (status.equals("failure")) { //email not found in database - create the account


                            runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        Toast.makeText(NewVault.this, json.getString("data"), Toast.LENGTH_LONG).show();
                                    }
                                    catch(Exception e)
                                    {
                                        Log.e("Error", e.getMessage());
                                    }
                                }
                            });





                        }
                        return null;
                    }
                    catch(Exception e){
                        Log.e("ERROR", e.getMessage());
                        return null;
                    }
                }
            };
            newVaultBackgroundTask.execute(server + "/doCreateVault");


        }
    }
}
