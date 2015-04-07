package edu.psu.vaultinators.nebulock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import edu.psu.vaultinators.nebulock.util.SecureServerRequest;
import edu.psu.vaultinators.nebulock.util.ServerRequest;

/**
 * Created by cgs5144 on 4/7/15.
 */
public class EditEntries extends DialogFragment {
    refreshEntries mCallback;

    private String vaultID;
    private String eCred;
    private String pCred;
    private String name;
    private String desc;
    private String mod;

    public EditEntries() {

    }

    public interface refreshEntries {
        public void refreshEntries(String vaultId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (refreshEntries) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement refreshEntries");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        vaultID = getArguments().getString("vaultID");
        eCred = getArguments().getString("email");
        pCred = getArguments().getString("password");
        name = getArguments().getString("name");
        desc = getArguments().getString("description");
        mod = getArguments().getString("lastModifiedBy");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.editEntryChoice)
                .setNegativeButton(R.string.deleteTheEntry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Deleting " + name);
                        builder.setMessage("Are you sure you want to delete this entry?");
                        builder.setNegativeButton(R.string.label_confirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteTheEntry(vaultID);
                                mCallback.refreshEntries(vaultID);
                            }
                        });
                        builder.setPositiveButton(R.string.label_deny, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        builder.show();
                    }
                })

                .setNeutralButton(R.string.editEntry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                        final View promptView = layoutInflater.inflate(R.layout.editentrydialog, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setView(promptView);
                        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                        final EditText tempEntryDesc = (EditText) promptView.findViewById(R.id.editEntryDescEditText);
                        tempEntryDesc.setText(desc);
                        // setup a dialog window
                        alertDialogBuilder.setCancelable(false)
                                .setTitle("Editing " + name)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String newText = tempEntryDesc.getText().toString();
                                        editTheEntry(vaultID, newText);
                                        mCallback.refreshEntries(vaultID);
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
                .setPositiveButton(R.string.cancelEdit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void deleteTheEntry(String vaultID){
        ServerRequest deleteEntryRequest = new SecureServerRequest() {

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

        deleteEntryRequest
                .setPath("bin/doDeleteEntry")
                .setParameter("email", eCred)
                .setParameter("password", pCred)
                .setParameter("vaultID", vaultID)
                .setParameter("entryName", name)
                .execute();
    }

    public void editTheEntry(String vaultID, String eDesc){
        ServerRequest editEntryRequest = new SecureServerRequest() {

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

        editEntryRequest
                .setPath("bin/doEditEntry")
                .setParameter("email", eCred)
                .setParameter("password", pCred)
                .setParameter("vaultID", vaultID)
                .setParameter("entryName", name)
                .setParameter("text", eDesc)
                .execute();

    }
}
