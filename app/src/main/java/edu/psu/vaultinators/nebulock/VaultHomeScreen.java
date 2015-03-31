package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.ArrayList;

import edu.psu.vaultinators.nebulock.util.ServerRequest;

public class VaultHomeScreen extends Activity {

    String server = "http://146.186.64.169:6917/bin";
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<HashMap<String, String>> listAdapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_home);

        listView = (ListView) findViewById(R.id.vaultListView);
        listAdapterData = new ArrayList<HashMap<String, String>>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String email = (getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE)).getString(getString(R.string.email_key),"");
        final String password = (getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE)).getString(getString(R.string.password_key),"");





        //createAccountBackgroundTask.execute(server + "/doGetVaults");

        ServerRequest getVaultsRequest = new ServerRequest() {

            @Override
            protected void onSuccess(JSONObject data) {
                super.onSuccess(data);
                Log.e("Success", "getVaultsRequest success");
                try {
                    Log.e("Data:", data.toString());

                    JSONArray vaults = data.getJSONArray("vaults");
                    populateListViewWithVaults(vaults);


                } catch (org.json.JSONException e){
                    e.printStackTrace();
                }




            }

            @Override
            protected void onFailure(String message, JSONObject data) {
                super.onFailure(message, data);
                Log.e("Failure", "getVaultsRequest fail");
                Toast.makeText(VaultHomeScreen.this, "Failed to authenticate.", Toast.LENGTH_SHORT).show();

            }

            @Override
            protected void onError(String message, Integer code, JSONObject data) {
                super.onError(message, code, data);
            }
        };

        getVaultsRequest
                .setPath("bin/doGetVaults")
                .setParameter("email", email)
                .setParameter("password", password)
                .execute();

    }


    public void populateListViewWithVaults(JSONArray vaults){

        ListView listView = (ListView)findViewById(R.id.vaultListView);

        for(int i = 0; i < vaults.length(); i++){
            HashMap<String, String> vaultInfo = new HashMap<String, String>();
            try {
                vaultInfo.put("vaultName", ((JSONObject) vaults.get(i)).getString("vaultName"));
                vaultInfo.put("vaultID", ((JSONObject) vaults.get(i)).getString("vaultID"));
                vaultInfo.put("vaultDescription", ((JSONObject) vaults.get(i)).getString("vaultDescription"));
                listAdapterData.add(vaultInfo);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(
            VaultHomeScreen.this,
            listAdapterData,
            R.layout.vault_list_item,
            new String[] {"vaultName", "vaultID", "vaultDescription"},
            new int[] {R.id.vaultItemName, R.id.vaultItemId, R.id.vaultItemDescription} );

        listView.setAdapter(adapter);
    }

    public void createNewVault(View view) {
        Intent intent = new Intent(this, NewVault.class);
        startActivity(intent);
    }

}
