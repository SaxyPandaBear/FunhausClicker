package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

// TODO: add toggle buttons for settings
// TODO: add button for deleting account
// TODO: really everything.

public class SettingsActivity extends AppCompatActivity {
    private static boolean onlineMode; // true means online play, false means not.

    ToggleButton toggleOnline; // toggle button for online mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // create listener for toggle button
        toggleOnline = (ToggleButton)findViewById(R.id.toggleOnline);
        toggleOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    // toggle is enabled.
                    // online mode set to true
                    onlineMode = true;
                }
                else{
                    // toggle is disabled.
                    // online mode set to false
                    onlineMode = false;
                }
            }
        });
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

    // TODO: delete private files
    // method called when the user confirms that they want to delete their saved data
    public void deleteData(View view){

    }

    // public accessor and mutator methods for toggleOnline
    public static boolean isOnline() {return onlineMode;}
    public static void setOnlineMode(boolean val) { onlineMode = val; }

}
