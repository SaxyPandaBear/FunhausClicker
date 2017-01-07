package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Andrew on 1/7/2017.
 *
 * used when the user attempts to login as a guest only
 * informs the user that guest access does not allow for saved data
 */

public class GuestAccessDialog extends DialogFragment {
    // http://stackoverflow.com/questions/25793247/simple-example-of-dialogfragment-example-code-not-working-for-me
    // http://www.androidbegin.com/tutorial/android-dialogfragment-tutorial/
    // https://developer.android.com/reference/android/app/DialogFragment.html#AlertDialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Warning!")
                .setCancelable(false)
                .setMessage("Playing as a guest does not save your high score. Are you sure that you want to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ((LoginActivity)getActivity()).guestAccess();
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
