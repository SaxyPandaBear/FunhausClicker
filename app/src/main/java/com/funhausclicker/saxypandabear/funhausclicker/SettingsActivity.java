package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

// TODO: add toggle buttons for settings
// TODO: add button for deleting account
// TODO: really everything.

public class SettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private static boolean onlineMode; // true means online play, false means not.

    private String username, password; // username and password passed in by main activity
    private ToggleButton toggleOnline; // toggle button for online mode
    private SeekBar volumeControl; // volume control seekbar used in settings activity
    private TextView volumeText; // text that displays current app volume
    private int volume; // volume value set by seekbar

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
                    setOnlineMode(true);
                }
                else{
                    // toggle is disabled.
                    // online mode set to false
                    setOnlineMode(false);
                }
            }
        });

        Bundle extras = getIntent().getExtras(); // try to get username and password
        if (extras == null) {
            // default values
            // this shouldn't happen, as far as I know
            username = ""; password = "";
        }
        else {
            // default empty string values if something goes wrong.
            username = extras.getString(LoginActivity.USERNAME, "");
            password = extras.getString(LoginActivity.PASSWORD, "");
        }

        volumeControl = (SeekBar)findViewById(R.id.volumeControl);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // update the value of volume with i
                volume = i;
                volumeText = (TextView)findViewById(R.id.volumeText);
                volumeText.setText(String.format(Locale.ENGLISH,"Volume :: %d", volume));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // leave as is
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // leave as is
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b){
        if (seekBar == volumeControl){
            // copy from onCreate
            // update the value of volume with i
            volume = i;
            volumeText = (TextView)findViewById(R.id.volumeText);
            volumeText.setText(String.format(Locale.ENGLISH,"Volume :: %d", volume));
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar){
        // don't know what to do here
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar){
        // don't know what to do here
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

    // displays a confirm dialog that tells the user what online mode does
    public void onlineInfo(View view){
        DialogFragment dialog = new ConfirmDialog();
        Bundle args = new Bundle();
        String title = "Online Mode Info";
        String message = "Online mode allows you to upload your score and compare it with the rest of the online users.";
        args.putString("title",title);
        args.putString("message",message);
        dialog.setArguments(args);
        dialog.show(getFragmentManager(),"onlineInfo");
    }

    // method called when the user wants to rate the app.
    // takes the user to the google play store to rate.
    public void rateApp(View view){

    }
    // public accessor and mutator methods for toggleOnline
    public static boolean isOnline() {return onlineMode;}
    public static void setOnlineMode(boolean val) { onlineMode = val; }

}
