package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Andrew on 1/1/2017.
 *
 * Alert dialog that displays a message to the user and allows them to confirm that they read it.
 * Is cancelable
 * Button should not do anything as it is just a way to inform the user.
 */

public final class ConfirmDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        Bundle args = getArguments();
        String title = args.getString("title","");
        String message = args.getString("message","");
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setCancelable(true)
                .setMessage(message)
                .setNeutralButton("Okay", null)
                .create();
    }
}
