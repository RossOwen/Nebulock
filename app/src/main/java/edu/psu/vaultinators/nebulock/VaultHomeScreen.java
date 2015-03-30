package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VaultHomeScreen extends Activity {

    String server = "http://146.186.64.169:6917/bin";
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_home);

        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String email = (getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE)).getString(getString(R.string.email_key),"");
        final String password = (getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE)).getString(getString(R.string.password_key),"");

        AsyncTask<String, Void, Void> createAccountBackgroundTask = new AsyncTask<String, Void, Void> () {

            protected Void doInBackground(String... urls) {
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new NameValuePair() {
                    @Override
                    public String getName() {
                        return "email";
                    }
                    @Override
                    public String getValue() {
                        return email;
                    }
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

                String[] listObjects = new String[json.length()];

                try {
                    String result = json.getString("result");
                    if(result.equals("success")){ //found email in the database - don't create the account

                       final JSONArray vaults = json.getJSONArray("data");

                       for(int i = 0; i < vaults.length(); i++){
                           JSONObject data = vaults.getJSONObject(i);
                           int vaultID = data.getInt("vaultID");
                           String vaultName = data.getString("vaultName");
                           String vaultDescription = data.getString("vaultDescription");
                           listObjects[i] = "test " + vaultID;
                       }

                        setVaults(listObjects);

                    } else if(result.equals("failure")) { //email not found in database - create the account

                        runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    Toast.makeText(VaultHomeScreen.this, json.getString("message"), Toast.LENGTH_LONG).show();
                                }
                                catch(Exception e)
                                {
                                    Log.e("Error", e.getMessage());
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    Toast.makeText(VaultHomeScreen.this, json.getString("message"), Toast.LENGTH_LONG).show();
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


        createAccountBackgroundTask.execute(server + "/doGetVaults");
    }

    public void setVaults(String[] list) {

        Log.e("test", "" + list.length);
        ArrayAdapter<String> vaultAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(vaultAdapter);
    }

    public void createNewVault(View view) {
        Intent intent = new Intent(this, NewVault.class);
        startActivity(intent);
    }
}
