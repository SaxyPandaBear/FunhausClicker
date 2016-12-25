package com.funhausclicker.saxypandabear.funhausclicker;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //keys for saved instance
    static final String LOCAL_SESSION = "local_session";
    static final String LOCAL_HIGH_SCORE = "local_high_score";

    //
    private int session_clicks; //how many clicks do they have in this session?
    private static int high_score = 0; //what's the player's high score for clicks?
    TextView leaderboard, current; //initialize outside of onCreate for scope

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            //restore values saved earlier
            session_clicks = savedInstanceState.getInt(LOCAL_SESSION);
            high_score = savedInstanceState.getInt(LOCAL_HIGH_SCORE);
        }
        else {
            //initialize our session
            session_clicks = 0; //starts our game at score = 0
        }
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
        if (session_clicks > high_score) {
            high_score = session_clicks;
            //we'll see what happens if I leave this part out
            //leaderboard.setText(leaderboard_text);
        }
        //let's try without updating the textview
    }
}
