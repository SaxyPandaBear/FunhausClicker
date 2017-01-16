package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// TODO: add toggle buttons for settings
// TODO: add button for deleting account
// TODO: really everything.

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onBackPressed(){
        // listener for android back button hardware
        // calls back() method which returns to main activity from settings activity
        back(new View(getApplicationContext()));
    }

    // button in settings activity that returns to main activity
    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // listener method for when delete data button is clicked on
    // brings up alert dialog warning user of consequences
    public void deleteDataListener(View view){
        DialogFragment dialog = new DeleteAccountDialog();
        dialog.show(getFragmentManager(),"delete_data");
    }

    // listener for DeleteAccountDialog that calls delete data
    public void deleteDataTrigger(){
        deleteData(new View(getApplicationContext()));
    }

    // method called when the user confirms that they want to delete their saved data
    public void deleteData(View view){
        
    }
}
