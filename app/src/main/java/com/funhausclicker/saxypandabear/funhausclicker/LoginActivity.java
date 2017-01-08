package com.funhausclicker.saxypandabear.funhausclicker;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// TODO: add sound to when the user logs in successfully
// TODO: popup in app for user when choosing guest - details things mentioned below
// TODO: use alert dialog
//  ** this would mean:
//  1) only local high score.
//  2) no online functionality.
//  3) data lost once app is closed.
// TODO: add "remember me" button or checkbox and functionality
// TODO: add registration so user can register themselves with the app

public class LoginActivity extends AppCompatActivity {
    EditText e1,e2; // used for storing username (e1) and password (e2)
    // Button login; //button used to transition from login activity to main
    // Button register; //registration button
    // Button guest; //play as guest option

    // name for shared preferences
    public static final String login_info = "login_info";
    // static final int YES_NO_CALL = 0, CONFIRM_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: remember me

        // let the user log in.
        e1 = (EditText)findViewById(R.id.username); // user
        e2 = (EditText)findViewById(R.id.password); // pass
    }

    // click listener that informs the user of what playing as a guest entails
    // and allows the user to proceed or decline
    public void guestAccessClick(){
        // Yes/No dialog
        // yes = continue to main
        // no = terminate
        DialogFragment dialog = new GuestAccessDialog();
        dialog.show(getFragmentManager(),"guest_access");
    }
    // listener for after the player chooses to click on guest login the first time
    // triggers guest access method which takes the user to main activity
    public void guestPositiveClick(){
        guestAccess(new View(getApplicationContext()));
    }
    // called when the user confirms their choice to log in as a guest.
    public void guestAccess(View view){
        MainActivity.isGuest = true;
        Intent intent = new Intent(this, MainActivity.class);
        // note: no username or password sent to main activity because this is guest access
        //       only.
        startActivity(intent);
    }

    // called when the user chooses to register their account
    // FILE FORMAT
    // user ____
    // pass ____
    //
    // example:
    //     user foo
    //     pass bar
    public void register(View view){
        // before anything else is done, have to determine if input user and pass combo is valid
        String user = e1.getText().toString();
        String pass = e2.getText().toString();
        boolean isValid = validPassword(pass) && validUsername(user); //make sure input is valid
        if (!isValid){
            // if login credentials as input are not valid, then we display an error
            // and terminate registration attempt.
            // TODO: terminate attempt because of invalid characters used in input
            DialogFragment dialog = new ConfirmDialog();
            Bundle args = new Bundle();
            String title = "Invalid Characters";
            String message = "Invalid character detected. Only letters and numbers are allowed";
            args.putString("title", title);
            args.putString("message", message);
            dialog.setArguments(args);
            dialog.show(getFragmentManager(),"invalid_char");
        }

        // if made it this far in the method, then login credentials are valid.
        // first check if there is already a registered account under that username.
        // if so, print error and tell the user to delete saved data
        // else, check valid user/pass combination and write to file
        boolean accountPresent = true; //flag
        StringBuilder sb = new StringBuilder();
        try {
            // attempt to open local storage of our saved high score
            FileInputStream fis = openFileInput(login_info);
            int c;
            while ((c = fis.read()) != -1) {
                // read until -1, which indicates EOF
                sb.append(Character.toString((char) c));
            }
            fis.close(); // close
        } catch (FileNotFoundException e) {
            // if file is not found, then we do not have an account present
            // set flag and allow user to write to file
            accountPresent = false;
        } catch (IOException e) {
            // we will handle IO exceptions by resetting and forcing account renewal.
            // this will be achieved by making the user register again.
        }
        if (!accountPresent){
            // if there's no account present, then we write to the login file
            FileOutputStream fos;
            try {
                fos = openFileOutput(login_info, MODE_PRIVATE);
                String writeMe = String.format(Locale.US, "user %s\n", user ); // user first
                fos.write(writeMe.getBytes()); // writes the username into local data
                writeMe = String.format(Locale.US, "pass %s", pass); // write password
                fos.write(writeMe.getBytes()); // writes them both into the file before closing
                fos.close(); // close file
            } catch (IOException e) {
                // caught from write()
                // catches FileNotFoundException as well
                // don't need to do anything hopefully
            }
        }
        else {
            // implies that there is an account that was present. parse the information for user/pass
            // note: only need to check username to see if it matches the one on file.
            // if not, display message to user and allow them to overwrite the data with a new user.
            String readUser = sb.toString(); //note: Disallow duplicate username, but allow duplicate passwords
            //file format:
            //    user %s
            //    pass %s
            // parse just past user to get the username of the player
            readUser = readUser.substring(5, readUser.indexOf('\n'));
            // check if input user matches readUser
            // NOTE: THIS ONLY APPLIES TO LOCAL ACCOUNT.
            // -> validUsername() will check for uniqueness in username when online play is implemented.
            if (readUser.equals(e1.getText().toString())){
                // if they match, just print that the username already exists
                // TODO: print that username matches and already exists
                // alert dialog with 1 OKAY button
                DialogFragment dialog = new ConfirmDialog();
                Bundle args = new Bundle();
                String title = "Username Taken";
                String message = String.format(Locale.US,"The username %s has already been taken.",e1.getText().toString());
                args.putString("title",title);
                args.putString("message",message);
                dialog.setArguments(args);
                dialog.show(getFragmentManager(),"username_taken");
            }
            else{
                // else, inform the user that there is already a DIFFERENT account on local data,
                // and then ask if the user wants to delete the save data to start a new account
                // TODO: inform player that an account already exists
                // TODO: allow player to restart with new account credentials
                // alert dialog with Yes/No buttons
                DialogFragment dialog = new OverwriteAccountDailog();
                dialog.show(getFragmentManager(),"account_override");
            }
        }
    }

    // called by dialog fragment when the user chooses to overwrite their account
    public void positiveClick(){
        // in this method, write to file because all of the errors have been accounted for before
        // reaching this method call.
        FileOutputStream fos;
        try {
            fos = openFileOutput(login_info, MODE_PRIVATE);
            String writeMe = String.format(Locale.US, "user %s\n", e1.getText().toString() ); // user first
            fos.write(writeMe.getBytes()); // writes the username into local data
            writeMe = String.format(Locale.US, "pass %s", e2.getText().toString()); // write password
            fos.write(writeMe.getBytes()); // writes them both into the file before closing
            fos.close(); // close file
        } catch (IOException e) {
            // caught from write()
            // catches FileNotFoundException as well
            // don't need to do anything hopefully
        }
    }

    // called from onClick by the login button
    // TODO: add hiding password on screen as it is being typed out
    // keys for putting username and password into intent
    private final static String USERNAME = "USERNAME";
    private final static String PASSWORD = "PASSWORD";
    public void attemptLogin(View view){
        String inputUsername = e1.getText().toString();
        String inputPassword = e2.getText().toString();
        // check if inputs use only valid chars
        if (validUsername(inputUsername) && validPassword(inputPassword)) {
            // now that inputs have been validated, check if it matches
            // stored values for user and pass
            // note: no previously stored data will return true
            if (validLogin(inputUsername, inputPassword)) {
                // if login is successful, set isGuest in main to false
                // and transition to main
                MainActivity.isGuest = false;

                // TODO: transition to main activity
                // https://developer.android.com/training/basics/firstapp/starting-activity.html
                // Use an intent to move to the main activity
                Intent intent = new Intent(this, MainActivity.class);
                // send username and password to main
                intent.putExtra(USERNAME, inputUsername); // at this point, user and pass
                intent.putExtra(PASSWORD, inputPassword); // have been validated by checks
                startActivity(intent);
            }
            else {
                // if invalid login, but valid input for user and pass, give login error
                // and terminate the login attempt
                DialogFragment dialog = new ConfirmDialog();
                Bundle args = new Bundle();
                String title = "Login Failure";
                String message = "Username and/or password do not match account on file"; // the message that is the bane of my existence.
                args.putString("title", title);
                args.putString("message", message);
                dialog.setArguments(args);
                dialog.show(getFragmentManager(),"incorrect_login");
            }
        }
        // if unsuccessful, let the user try again.
        // TODO: error message and handling when invalid login
        else {
            DialogFragment dialog = new ConfirmDialog();
            Bundle args = new Bundle();
            String title = "Invalid Characters";
            String message = "Invalid character detected. Only letters and numbers are allowed";
            args.putString("title", title);
            args.putString("message", message);
            dialog.setArguments(args);
            dialog.show(getFragmentManager(),"invalid_char");
        }
    }

    // Either a valid match of password and username,
    // or the file that stores the data was never written to before
    // if so, it will be valid. write to the file now
    private boolean validLogin(String inputUser, String inputPass){
        // check inputs for validity. if not valid, no point in executing the rest of the code
        // TODO: read from storage what saved username and password are
        // TODO: handle no stored file for user and pass by creating new file
        // TODO: in case of no file, give user option to play as guest or register
        StringBuilder sb = new StringBuilder(); // to accept input from reading file
        try {
            // attempt to open local storage of our saved high score
            FileInputStream fis = openFileInput(login_info);
            int c;
            while ((c = fis.read()) != -1) {
                // read until -1, which indicates EOF
                sb.append(Character.toString((char) c));
            }
            fis.close(); // close after we read everything
        } catch (FileNotFoundException e) {
            // if no file is found, then we have to return false because there is no saved account
            // on file to use.
            return false;
        }
        catch (IOException e) {
            // If there is an IOException that is not a FNFE, then display an error and return false.
            DialogFragment dialog = new ConfirmDialog();
            Bundle args = new Bundle();
            String title = "File Error";
            String message = "Oops! Something bad happened while authenticating. Please try again.";
            args.putString("title", title);
            args.putString("message", message);
            dialog.show(getFragmentManager(), "IOException");
            return false;
        }

        // if no exceptions occurred, parse the file to get the username and password on file
        String readUser = sb.toString().substring(5, sb.toString().indexOf('\n')); // gets the username
        String readPass = sb.toString().substring(sb.toString().indexOf('\n')+6); // skips "\npass "
        return inputUser.equals(readUser) && inputPass.equals(readPass);
    }

    // valid characters for username and password
    // note: alphabet, numbers and underscore are only valid chars
    /*
    private final char[] usableChars = {
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
            '_'
    };
    */
    // now differentiate between numbers and letters
    // public access so other activities and tests can view them.
    public static final char[] letters = {
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z'
    };
    public static final char[] numbers = {
            '0','1','2','3','4','5','6','7','8','9'
    };
    // invalid chars will result in return values of false for both check methods
    public static final char[] invalidChars = {
            '!','#','$','%','^','&','*','(',')','\n','\t',
            '-','=','+','`','~','[','{',']','}','\b','_',
            '\\','|',',','<','.','>','/','?','\'','"',' '
    };

    // checks if a username uses only valid chars.
    // username is NOT case-sensitive in terms of uniqueness
    // only restriction on a username FOR NOW is that the string is at least 4 chars long
    // TODO: implement unique username acquisition
    // TODO: replace dummy array with actual list of used namespaces
    private final static List<String> dummyUsernames = new ArrayList<>();
    // ignore case
    public static boolean validUsername(String username){
        if (noInvalidChar(username)){
            // WILL BE REPLACED AS THE APP DEVELOPS
            return (uniqueUsername(username, dummyUsernames) && username.length() >= 4);
        }
        return false; // return statements if block as well
    }
    // method used when there is a list of usernames that are already taken.
    public static boolean uniqueUsername(String username, List<String> users){
        return !(users.contains(username)); // does contain -> non-unique
    }

    // checks if a password uses valid chars
    // ignore case
    // note: must have at least 1 number and 1 letter,
    // and also must be at least 6 chars long
    // IMPORTANT NOTE: Passwords are case-sensitive, but validity checking isn't
    public static boolean validPassword(String password){
        if (noInvalidChar(password)){
            // password must be 6 chars long
            if (password.length() < 6)
                return false;

            // password must contain at least 1 number and 1 letter.
            boolean foundNumber = false, foundLetter = false; // flags
            // ignore case
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

            return foundLetter && foundNumber; // return flags

        }
        return false; // return statements if block as well

    }
    // helper method for validPassword and validUsername
    public static boolean noInvalidChar(String str){
        // if an input string contains an invalid char, return false
        // else return true
        for (char c : invalidChars){
            if (str.contains(Character.toString(c)))
                return false;
        }
        return true;
    }
}
