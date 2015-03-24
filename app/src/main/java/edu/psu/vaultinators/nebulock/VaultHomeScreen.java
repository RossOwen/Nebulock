package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VaultHomeScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_home);
    }

    public void createNewVault(View view) {
        Intent intent = new Intent(this, NewVault.class);
        startActivity(intent);
    }
}
