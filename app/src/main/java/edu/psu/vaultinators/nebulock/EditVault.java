package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
    private String name;
    private String desc;

    refreshVaults mCallback;

    public EditVault(){

    }
    public interface refreshVaults {
        public void refreshVaults(String email, String password);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (refreshVaults) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement refreshVaults");
        }
    }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            vaultID = getArguments().getString("vaultID");
            eCred = getArguments().getString("email");
            pCred = getArguments().getString("password");
            name = getArguments().getString("name");
            desc = getArguments().getString("description");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.editVaultChoice)
                    .setPositiveButton(R.string.deleteTheVault, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Deleting " + name);
                            builder.setMessage("Are you sure you want to delete this vault?");
                            builder.setNegativeButton(R.string.label_confirm, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteTheVault(vaultID);
                                    mCallback.refreshVaults(eCred, pCred);
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
                            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                            final View promptView = layoutInflater.inflate(R.layout.editvaultdialog, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setView(promptView);
                            final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                            final EditText tempVaultName = (EditText) promptView.findViewById(R.id.editVaultNameEditText);
                            final EditText tempVaultDesc = (EditText) promptView.findViewById(R.id.editVaultDescEditText);
                            tempVaultName.setText(name);
                            tempVaultDesc.setText(desc);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setTitle("Editing " + name)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            String newName = tempVaultName.getText().toString();
                                            String newDesc = tempVaultDesc.getText().toString();
                                            editTheVault(vaultID, newName, newDesc);
                                            mCallback.refreshVaults(eCred, pCred);
                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                        }
                    })
                    .setNegativeButton(R.string.openVault, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent openVaultIntent = new Intent(getActivity(), ViewVaultEntries.class);
                            startActivity(openVaultIntent);
                        }
                    });
            // Create the AlertDialoeDescg object and return it
            return builder.create();
        }

        public void deleteTheVault(String id){
            ServerRequest deleteVaultRequest = new SecureServerRequest() {

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

            deleteVaultRequest
                    .setPath("bin/doDeleteVault")
                    .setParameter("email", eCred)
                    .setParameter("password", pCred)
                    .setParameter("vaultID", id)
                    .execute();
        }

    public void editTheVault(String id, String newName, String newDescription){
        ServerRequest editVaultRequest = new SecureServerRequest() {

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

        editVaultRequest
                .setPath("bin/doEditVault")
                .setParameter("email", eCred)
                .setParameter("password", pCred)
                .setParameter("vaultID", id)
                .setParameter("vaultName", newName)
                .setParameter("vaultDescription", newDescription)
                .execute();
    }

    }

