package com.funhausclicker.saxypandabear.funhausclicker;

import android.content.Context;
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
    //keys for saved instance
    static final String LOCAL_SESSION = "local_session";
    static final String LOCAL_HIGH_SCORE = "local_high_score";

    //only file that is written to by this app
    static final String filename = "funhaus_wins";

    //vars used throughout the app defined here for scope
    int session_clicks; //how many clicks do they have in this session?
    int high_score; //what's the player's high score for clicks?
    TextView leaderboard, current; //initialize outside of onCreate for scope


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //check for saved instance
        if (savedInstanceState != null){
            //restore values saved earlier
            session_clicks = savedInstanceState.getInt(LOCAL_SESSION);
            high_score = savedInstanceState.getInt(LOCAL_HIGH_SCORE);
        }
        else {
            //initialize our session
            session_clicks = 0; //starts our game at score = 0
        }
        StringBuilder sb = new StringBuilder(); //to accept input from reading file
        try{
            //attempt to open local storage of our saved high score
            FileInputStream fis = openFileInput(filename);
            int c;
            while ((c = fis.read()) != -1){
                //read until -1, which indicates EOF
                sb.append(Character.toString((char)c));
            }
            fis.close(); //close after we
        }catch (FileNotFoundException e){
            high_score = 0;
        }
        catch (IOException e){
            high_score = 0;
        }

        //now we have our file. it should be in the format:
        //"high score = %d"
        //do string manipulation to acquire the high score
        //first check if the string is longer than 2. if not,then high score is 0
        String valString;
        if (sb.toString().length() > 2) {
            valString = sb.toString().substring(sb.indexOf("=") + 2); //+2 skips the = and the whitespace
            high_score = Integer.parseInt(valString);
        }
        else
            high_score = 0;

        //get resources
        Resources res = getResources();
        String current_text = String.format(res.getString(R.string.current), session_clicks);
        String leaderboard_text = String.format(res.getString(R.string.high_score), high_score);
        //creates a text field that displays our current score
        current = (TextView)findViewById(R.id.current_display);
        current.setText(current_text);

        //creates a text field that displays the current high score for this device
        leaderboard = (TextView)findViewById(R.id.high_score);
        leaderboard.setText(leaderboard_text);
    }

    //see here for reference on what to do with all of these methods
    //https://developer.android.com/guide/components/activities/index.html
    //This is a note for myself.
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
        //let's see what happens if I try to write to storage here
        //store in a local file named mHighScore
        FileOutputStream fos;
        try {
            fos = openFileOutput(filename, MODE_PRIVATE);
            String writeMe = String.format(Locale.ENGLISH,"high score = %d",high_score);
            fos.write(writeMe.getBytes()); //writes the high score into local data
            fos.close();
        }
        catch (FileNotFoundException e){
            //caught from openFileOutput()
        }
        catch (IOException e){
            //caught from write()
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    //not sure if this is necessary yet. just practicing
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        //store my data that I need now
        savedInstanceState.putInt(LOCAL_SESSION, session_clicks);
        savedInstanceState.putInt(LOCAL_HIGH_SCORE, high_score); //maybe unnecessary?
        //call super at the end
        super.onSaveInstanceState(savedInstanceState);
    }

    //not sure if this is necessary yet. just practicing
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState); //call super first

        //restore values from saved state
        session_clicks = savedInstanceState.getInt(LOCAL_SESSION);
        high_score = savedInstanceState.getInt(LOCAL_HIGH_SCORE);
    }

    //called when the user clicks on the button
    //extremely primitive functionality
    public void buttonClick(View view){
        session_clicks++;

        Resources res = getResources();
        String current_text = String.format(res.getString(R.string.current), session_clicks);
        current.setText(current_text);
        if (session_clicks > high_score) {
            high_score = session_clicks;
            String leaderboard_text = String.format(res.getString(R.string.high_score), high_score);
            leaderboard.setText(leaderboard_text);
        }
    }
}
