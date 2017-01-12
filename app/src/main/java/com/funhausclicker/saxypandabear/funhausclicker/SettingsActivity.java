package com.funhausclicker.saxypandabear.funhausclicker;

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
}
