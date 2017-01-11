package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // keys for saved instance
    public static final String LOCAL_SESSION = "local_session";
    public static final String LOCAL_HIGH_SCORE = "local_high_score";

    // data that is written by this activity goes to the file name below
    static final String filename = "funhaus_wins";

    // vars used throughout the main activity defined here for scope
    static boolean isGuest; // set when the user logs in through login activity <-- accessed outside of this class
    // boolean hasLogged; // for notification alerts?
    int session_clicks; // how many clicks do they have in this session?
    int high_score; // what's the player's high score for clicks?
    String username, password;
    TextView leaderboard, current; // declare outside of onCreate for scope

    // TODO: Figure out how to integrate SQL and network connection into this app.
    // TODO: When network connectivity achieved, add connect to web server button
    // note: may just be transition to online leaderboard screen, with option to upload data
    // note: only accessible if not guest
    // note: will merge log out into settings button
    // TODO: add push notifications
    //  - push after a certain amount of time away from the app?
    //  - push after your high score has been surpassed (the first time) -> network and database access
    // TODO: add a store for microtransactions
    // TODO: in app billing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // first check if the player is a guest
        if (isGuest){
            // if so, we don't need to check for local data. there won't be any
            // set values and move on.
            high_score = 0;
            session_clicks = 0;
            username = ""; password = ""; // username and password not used in guest function
            // Make sure that all other functions that use username and password check
            // if(isGuest) evaluates as true before continuing.
        }
        else{

            // check for saved instance
            if (savedInstanceState != null) {
                // restore values saved earlier
                session_clicks = savedInstanceState.getInt(LOCAL_SESSION);
                high_score = savedInstanceState.getInt(LOCAL_HIGH_SCORE);

                // if saved instance NOT equal to null, then we get user and pass in this manner
                username = savedInstanceState.getString(LoginActivity.USERNAME, ""); // default empty string
                password = savedInstanceState.getString(LoginActivity.PASSWORD, ""); // default empty string
            } else {
                // initialize our session
                session_clicks = 0; // starts our game at score = 0

                // if no saved instance, then retrieved this way
                Bundle extras = getIntent().getExtras();
                if (extras == null) {
                    // default values
                    username = ""; password = "";
                }
                else {
                    username = extras.getString(LoginActivity.USERNAME, "");
                    password = extras.getString(LoginActivity.PASSWORD, "");
                }
            }
            // read in local scores from file
            StringBuilder sb = new StringBuilder(); // to accept input from reading file
            try {
                // attempt to open local storage of our saved high score
                FileInputStream fis = openFileInput(filename);
                int c;
                while ((c = fis.read()) != -1) {
                    // read until -1, which indicates EOF
                    sb.append(Character.toString((char) c));
                }
                fis.close(); // close after we read everything
            } catch (IOException e) {
                // encapsulates FileNotFoundException due to being a subtype of IOException
                high_score = 0;
            }


            // now we have our file. it should be in the format:
            // "high score = %d"
            // do string manipulation to acquire the high score
            // first check if the string is longer than 2. if not,then high score is 0
            String valString;
            if (sb.toString().length() > 2) {
                valString = sb.toString().substring(sb.indexOf("=") + 2); // +2 skips the "= "
                high_score = Integer.parseInt(valString);
            } else
                high_score = 0;
        }

        // get resources
        Resources res = getResources();
        String current_text = String.format(res.getString(R.string.current), session_clicks);
        String leaderboard_text = String.format(res.getString(R.string.high_score), high_score);
        // creates a text field that displays our current score
        current = (TextView)findViewById(R.id.current_display);
        current.setText(current_text);

        // creates a text field that displays the current high score for this device
        leaderboard = (TextView)findViewById(R.id.high_score);
        leaderboard.setText(leaderboard_text);
    }

    // see here for reference on what to do with all of these methods for activity life cycles
    // https://developer.android.com/guide/components/activities/index.html
    // This is a note for myself.
    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        // only store data if the player is NOT a guest
        if (!isGuest) {
            // store in a local private file
            FileOutputStream fos;
            try {
                fos = openFileOutput(filename, MODE_PRIVATE);
                String writeMe = String.format(Locale.US, "high score = %d", high_score);
                fos.write(writeMe.getBytes()); // writes the high score into local data
                fos.close();
            } catch (IOException e) {
                // caught from write()
                // catches FileNotFoundException as well
                // don't need to do anything hopefully
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    // not sure if this is necessary yet. just practicing
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        // store my data that I need now
        savedInstanceState.putInt(LOCAL_SESSION, session_clicks);
        savedInstanceState.putInt(LOCAL_HIGH_SCORE, high_score); // maybe unnecessary?
        // call super at the end
        super.onSaveInstanceState(savedInstanceState);
    }

    // not sure if this is necessary yet. just practicing
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState); // call super first

        if (!isGuest) {
            // restore values from saved state
            session_clicks = savedInstanceState.getInt(LOCAL_SESSION);
            high_score = savedInstanceState.getInt(LOCAL_HIGH_SCORE);
        }
        else {
            // if guest, nothing is stored
            session_clicks = 0;
            high_score = 0;
        }
    }

    // called when the user clicks on the main play button
    // extremely primitive functionality
    public void play(View view){
        session_clicks++; // increment local score

        Resources res = getResources();
        String current_text = String.format(res.getString(R.string.current), session_clicks);
        current.setText(current_text);
        if (session_clicks > high_score) {
            high_score = session_clicks;
            String leaderboard_text = String.format(res.getString(R.string.high_score), high_score);
            leaderboard.setText(leaderboard_text);
        }
    }

    // called when the user chooses to log out
    public void logout(View view){
        // go back to login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    // listener for when the user clicks on the logout button
    public void logoutListener(View view){
        // informs the user that they are attempting to logout
        // creates a logout dialog
        DialogFragment dialog = new LogoutDialog();
        dialog.show(getFragmentManager(), "logout");
    }
    // called when the user confirms that they want to log out.
    // triggers logout method
    public void logoutPositiveClick(){
        logout(new View(getApplicationContext()));
    }

    // called when the user wants to access settings
    // transitions to settings activity
    public void settings(View view){
        // no need to putExtra() so far
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
