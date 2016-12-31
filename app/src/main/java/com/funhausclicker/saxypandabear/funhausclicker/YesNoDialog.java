package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Andrew on 12/31/2016.
 *
 * Fragment used to display alerts with Yes/No operations
 */

public class YesNoDialog extends DialogFragment {
    private String title; // title of the fragment
    private String message; // message to be displayed

    // empty constructor...?
    // http://stackoverflow.com/questions/7977392/android-dialogfragment-vs-dialog
    public YesNoDialog(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        Bundle args = getArguments();
        String title = args.getString("title", "");
        String message = args.getString("message","");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                    }
                })
                .create();

    }


}
