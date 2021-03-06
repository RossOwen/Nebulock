package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import edu.psu.vaultinators.nebulock.util.SecureServerRequest;
import edu.psu.vaultinators.nebulock.util.ServerRequest;


public class ViewVaultEntries extends Activity implements EditEntries.refreshEntries {

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
        final String vaultId = (getSharedPreferences(getString(R.string.vault_id_key), Context.MODE_PRIVATE)).getString(getString(R.string.vault_id_key), "-1");
        getEntries(vaultId);
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
                entryInfo.put("lastModifiedBy", ((JSONObject) entries.get(i)).getString("lastModifiedBy"));
                listAdapterData.add(entryInfo);
            } catch (JSONException e){
                e.printStackTrace();
            }

        }

        SimpleAdapter adapter = new SimpleAdapter(
                ViewVaultEntries.this,
                listAdapterData,
                R.layout.entries_list_item,
                new String[] {"entryName", "text", "emailCreatedBy", "lastModifiedBy"},
                new int[] {R.id.entryItemName, R.id.entryItemText, R.id.entryItemEmailCreatedBy, R.id.entryItemLastModifiedBy} );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                final String entryName = ((HashMap) (listView.getItemAtPosition(position))).get("entryName").toString();
                final String entryDesc = ((HashMap) (listView.getItemAtPosition(position))).get("text").toString();
                final String lastModifiedBy = ((HashMap) (listView.getItemAtPosition(position))).get("lastModifiedBy").toString();
                final String vaultId = (getSharedPreferences(getString(R.string.vault_id_key), Context.MODE_PRIVATE)).getString(getString(R.string.vault_id_key), "-1");
                final String email = (getSharedPreferences(getString(R.string.email_key), Context.MODE_PRIVATE)).getString(getString(R.string.email_key),"");
                final String password = (getSharedPreferences(getString(R.string.password_key), Context.MODE_PRIVATE)).getString(getString(R.string.password_key),"");

                Context context = getApplicationContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.vault_id_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.vault_id_key), vaultId);
                editor.commit();

                Bundle args = new Bundle();
                args.putString("vaultID", vaultId);
                args.putString("email", email);
                args.putString("password", password);
                args.putString("name", entryName);
                args.putString("description", entryDesc);
                args.putString("lastModifiedBy", lastModifiedBy);
                EditEntries ee = new EditEntries();
                ee.setArguments(args);
                ee.show(getFragmentManager(), "Entry Options");
                getEntries(vaultId);

            }
        });
    }

    public void addEntryToVault(View view){
        String vaultId = (getSharedPreferences(getString(R.string.vault_id_key), Context.MODE_PRIVATE)).getString(getString(R.string.vault_id_key), "-1");
        if(!vaultId.equals("-1")){
            Intent addEntryIntent = new Intent(ViewVaultEntries.this, addEntry.class);
            startActivity(addEntryIntent);
        } else{
            //error
        }
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

    public void getEntries(String vaultId){

        ServerRequest getEntriesRequest = new SecureServerRequest() {
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
                .setParameter("vaultID", vaultId)
                .execute();
    }

    public void refreshEntries(String vaultID) {
        getEntries(vaultID);
    }
}
