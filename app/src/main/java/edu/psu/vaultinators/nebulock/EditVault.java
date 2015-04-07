package edu.psu.vaultinators.nebulock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.content.SharedPreferences;

import org.json.JSONObject;

import edu.psu.vaultinators.nebulock.util.SecureServerRequest;
import edu.psu.vaultinators.nebulock.util.ServerRequest;

/**
 * Created by cgs5144 on 4/6/15.
 */
public class EditVault extends DialogFragment {
    private String vaultID;
    private String eCred;
    private String pCred;

    public EditVault(){

    }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            vaultID = getArguments().getString("vaultID");
            eCred = getArguments().getString("email");
            pCred = getArguments().getString("password");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.editVaultChoice)
                    .setPositiveButton(R.string.deleteTheVault, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Deleting Vault " + vaultID);
                            builder.setMessage("Are you sure you want to delete this vault?");
                            builder.setNegativeButton(R.string.label_confirm, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteTheVault(vaultID);

                                }
                            });
                            builder.setPositiveButton(R.string.label_deny, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            builder.show();
                        }
                    })

                    .setNeutralButton(R.string.editVault, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //
                        }
                    })
                    .setNegativeButton(R.string.openVault, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "Opening Vault " + vaultID + "...", Toast.LENGTH_SHORT).show();
                            Intent openVaultIntent = new Intent(getActivity(), ViewVaultEntries.class);
                            startActivity(openVaultIntent);
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

        public void deleteTheVault(String id){
            ServerRequest deleteVaultRequest = new SecureServerRequest() {

                @Override
                protected void onSuccess(JSONObject data) {
                    Toast.makeText(getActivity(), "Vault " + vaultID + " has been deleted.", Toast.LENGTH_SHORT).show();
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

            deleteVaultRequest
                    .setPath("bin/doDeleteVault")
                    .setParameter("email", eCred)
                    .setParameter("password", pCred)
                    .setParameter("vaultID", id)
                    .execute();
        }

    }

