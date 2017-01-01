package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Andrew on 1/1/2017.
 *
 * Alert dialog that gives user an option of Yes/No in response to an action.
 * Not cancelable
 */

public final class YesNoDialog extends DialogFragment {
    // http://stackoverflow.com/questions/25793247/simple-example-of-dialogfragment-example-code-not-working-for-me
    // http://www.androidbegin.com/tutorial/android-dialogfragment-tutorial/
    // https://developer.android.com/reference/android/app/DialogFragment.html#AlertDialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Warning!")
                .setCancelable(false)
                .setMessage("Local account already exists. Overwrite?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ((LoginActivity)getActivity()).positiveClick();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((LoginActivity)getActivity()).negativeClick();
                    }
                })
                .create();
    }
}
