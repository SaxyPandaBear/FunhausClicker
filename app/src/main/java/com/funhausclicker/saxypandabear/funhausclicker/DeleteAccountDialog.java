package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Andrew on 1/16/2017.
 *
 * warns the user that they are attempting to delete their account
 * Yes/No dialog style
 */

public class DeleteAccountDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Warning!")
                .setCancelable(false)
                .setMessage("You are about to delete your saved data. This will also log you out of the game. Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ((SettingsActivity)getActivity()).deleteDataTrigger();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // on click for this dialog, it will close this fragment.
                        dismiss(); // dismiss the fragment
                    }
                })
                .create();
    }
}
