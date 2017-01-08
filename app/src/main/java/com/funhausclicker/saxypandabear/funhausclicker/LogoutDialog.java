package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Andrew on 1/8/2017.
 *
 * confirms that the user wants to log out of their account
 * Yes/No style of dialog fragment
 */

public class LogoutDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Log Out?")
                .setCancelable(false)
                .setMessage("Are you sure that you want to log out of your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ((MainActivity)getActivity()).logoutPositiveClick();
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
