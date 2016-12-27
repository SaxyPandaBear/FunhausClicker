package com.funhausclicker.saxypandabear.funhausclicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

//TODO: incorporate "guest" functionality
//TODO: popup in app for user when choosing guest - details things mentioned below
// ** this would mean:
// 1) only local high score.
// 2) no online functionality.
// 3) data lost once app is closed.
//TODO: add "remember me" button and functionality
//TODO: add registration so user can register themselves with the app

public class LoginActivity extends AppCompatActivity {
    EditText e1,e2; //used for storing username (e1) and password (e2)
    Button login; //button used to transition from login activity to main
    Button register; //registration button
    Button guest; //play as guest option

    //name for shared preferences
    public static final String login_info = "login_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //first check to see if the user is already logged in
        //if so, then we don't need to log in again.
        //else we do the standard procedure of logging in.

        //let the user log in.
        e1 = (EditText)findViewById(R.id.username);
        e2 = (EditText)findViewById(R.id.password);
    }

    //called when the user chooses to log in as a guest.
    public void guestAccess(){
        MainActivity.isGuest = true;
    }

    //called from onClick by the login button
    public void attemptLogin(){
        //TODO: take input username and password strings and place in call to validLogin
        if (validLogin("","")){
            //if login is successful, set isGuest in main to false
            //and transition to main
            MainActivity.isGuest = false;

        }
        //if unsuccessful, let the user try again.
        //TODO: error message and handling when invalid login
    }

    //Either a valid match of password and username,
    //or the file that stores the data was never written to before
    //if so, it will be valid. write to the file now
    public boolean validLogin(String inputUser, String inputPass){
        String readUser = "", readPass = "";
        //TODO: read from storage what saved username and password are
        //TODO: handle no stored file for user and pass by creating new file
        //TODO: in case of no file, give user option to play as guest or register

        return inputUser.equals(readUser) && inputPass.equals(readPass);
    }
}
