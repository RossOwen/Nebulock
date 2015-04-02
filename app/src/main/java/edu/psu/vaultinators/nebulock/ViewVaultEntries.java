package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import edu.psu.vaultinators.nebulock.util.ServerRequest;


public class ViewVaultEntries extends Activity {

    String server = "http://146.186.64.169:6917/bin";
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<HashMap<String, String>> listAdapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_contents);
    }

    @Override
    protected void onResume(){
        super.onResume();
        listView = (ListView) findViewById(R.id.entryListView);
        listAdapterData = new ArrayList<HashMap<String, String>>();
        final String email = (getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE)).getString(getString(R.string.email_key),"");
        final String password = (getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE)).getString(getString(R.string.password_key),"");
        final int vaultId = (getSharedPreferences(getString(R.string.vault_id_key), Context.MODE_PRIVATE)).getInt(getString(R.string.vault_id_key), -1);

        ServerRequest getEntriesRequest = new ServerRequest() {
            @Override
            protected void onSuccess(JSONObject data) {
                super.onSuccess(data);
                Log.e("Success", "getEntriesRequest success");
                try {
                    Log.e("Data:", data.toString());

                    JSONArray entries = data.getJSONArray("entries");
                    populateListViewWithEntries(entries);

                } catch (org.json.JSONException e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void onFailure(String message, JSONObject data) {
                super.onFailure(message, data);
                Log.e("Failure", "getEntriesRequest fail");
                Toast.makeText(ViewVaultEntries.this, "Failed to authenticate.", Toast.LENGTH_SHORT).show();

            }
            @Override
            protected void onError(String message, Integer code, JSONObject data) {
                super.onError(message, code, data);
            }
        };

        getEntriesRequest
                .setPath("bin/doGetEntries")
                .setParameter("vaultID", Integer.toString(vaultId))
                .execute();
    }

    public void populateListViewWithEntries(JSONArray entries){
        final ListView listView = (ListView)findViewById(R.id.entryListView);
        listAdapterData.clear();

        for(int i = 0; i < entries.length(); i++){
            HashMap<String, String> entryInfo = new HashMap<String, String>();
            try {
                entryInfo.put("entryName", ((JSONObject) entries.get(i)).getString("entryName"));
                entryInfo.put("text", ((JSONObject) entries.get(i)).getString("text"));
                entryInfo.put("emailCreatedBy", ((JSONObject) entries.get(i)).getString("emailCreatedBy"));
                listAdapterData.add(entryInfo);
            } catch (JSONException e){
                e.printStackTrace();
            }

        }

        SimpleAdapter adapter = new SimpleAdapter(
                ViewVaultEntries.this,
                listAdapterData,
                R.layout.entries_list_item,
                new String[] {"entryName", "text", "emailCreatedBy"},
                new int[] {R.id.entryItemName, R.id.entryItemText, R.id.entryItemEmailCreatedBy} );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //TODO: add a dialog box - edit, delete, cancel

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_vault_entry, menu);
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
}
