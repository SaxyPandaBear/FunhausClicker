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
        //TODO: transition to main activity
    }

    //called from onClick by the login button
    //TODO: add hiding password on screen as it is being typed out
    public void attemptLogin(){
        //TODO: take input username and password strings and place in call to validLogin
        if (validLogin("","")){
            //if login is successful, set isGuest in main to false
            //and transition to main
            MainActivity.isGuest = false;

            //TODO: transition to main activity
        }
        //if unsuccessful, let the user try again.
        //TODO: error message and handling when invalid login
    }

    //Either a valid match of password and username,
    //or the file that stores the data was never written to before
    //if so, it will be valid. write to the file now
    private boolean validLogin(String inputUser, String inputPass){
        //check inputs for validity. if not valid, no point in executing the rest of the code
        String readUser = "", readPass = "";
        //TODO: read from storage what saved username and password are
        //TODO: handle no stored file for user and pass by creating new file
        //TODO: in case of no file, give user option to play as guest or register

        return inputUser.equals(readUser) && inputPass.equals(readPass);
    }

    //valid characters for username and password
    //note: alphabet, numbers and underscore are only valid chars

    private final char[] usableChars = {
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
            '_'
    };

    //now differentiate between numbers and letters
    private final char[] letters = {
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z'
    };
    private final char[] numbers = {
            '0','1','2','3','4','5','6','7','8','9'
    };
    //invalid chars will result in return values of false for both check methods
    private final char[] invalidChars = {
            '!','#','$','%','^','&','*','(',')',
            '-','=','+','`','~','[','{',']','}',
            '\\','|',',','<','.','>','/','?','\'','"',' '
    };

    //checks if a username uses only valid chars.
    //username is NOT case-sensitive in terms of uniqueness
    //only restriction on a username FOR NOW is that the string is at least 4 chars long
    //TODO: implement unique username acquisition
    //ignore case
    private boolean validUsername(String username){
        if (!containsInvalidChar(username)){
            //WILL BE REPLACED AS THE APP DEVELOPS
            return (username.length() >= 4);
        }
        return false; //return statements if block as well
    }

    //checks if a password uses valid chars
    //ignore case
    //note: must have at least 1 number and 1 letter,
    //and also must be at least 6 chars long
    //IMPORTANT NOTE: Passwords are case-sensitive, but validity checking isn't
    private boolean validPassword(String password){
        if (!containsInvalidChar(password)){
            //password must be 6 chars long
            if (password.length() < 6)
                return false;

            //password must contain at least 1 number and 1 letter.
            boolean foundNumber = false, foundLetter = false; //flags
            //ignore case
            String aux = password.toLowerCase();
            for (char c : letters){
                if (aux.contains(Character.toString(c))){
                    foundLetter = true;
                    break;
                }
            }
            for (char c : numbers){
                if (aux.contains(Character.toString(c))){
                    foundNumber = true;
                    break;
                }
            }

            return foundLetter && foundNumber; //return flags

        }
        return false; //return statements if block as well

    }
    //helper method for validPassword and validUsername
    private boolean containsInvalidChar(String str){
        //if an input string contains an invalid char, return false
        //else return true
        for (char c : invalidChars){
            if (str.contains(Character.toString(c)))
                return false;
        }
        return true;
    }
}
