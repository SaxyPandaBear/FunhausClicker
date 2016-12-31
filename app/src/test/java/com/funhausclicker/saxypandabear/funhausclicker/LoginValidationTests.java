package com.funhausclicker.saxypandabear.funhausclicker;

/**
 * Created by Andrew on 12/30/2016.
 *
 * Unit testing for validation methods.
 */

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class LoginValidationTests {
    private String user, pass;
    private List<String> sampleUsers = Arrays.asList(new String[]{
            "foobar","Andrew","Huynh","abc123","Funhaus2014"
    }); // sample database of users that are already registered.

    // constructor
    public LoginValidationTests(String user, String pass){
        this.user = user;
        this.pass = pass;
    }

    // Data points for data driven test
    // [ Username, Password ]
    @Parameters public static Collection<Object[]> parameters(){
        return Arrays.asList(new Object[][]{
                {"f0oBar","password1"},
                {"Andrew4","Funhaus1"},
                {"BillyBob","creampuff234"},
                {"pokemon","Nintendo2016"},
                {"pokerman","412gib"},
                {"MonkeyBall253","fall2016"},
                {"Prince","early2016"},
                {"MontyOum","rip2015"},
                {"CarrieFisher","notInROGUE1"},
                {"Debbie","Reynolds2016"},
                {"Ruby","Rose2014"},
                {"Destiny","2014Bungie"},
                {"Bungie", "HaloInThe2000s"},
                {"Donald","TweetMasterGeneral1"}
        });
    }

    //tests inputs to see if they contain invalidChars
    @Test
    public void invalidCharsInUser(){
        assertTrue(LoginActivity.noInvalidChar(user));
    }

    @Test
    public void invalidCharsInPass(){
        assertTrue(LoginActivity.noInvalidChar(pass));
    }

    @Test
    public void validateUser(){
        assertTrue(LoginActivity.validUsername(user));
    }

    @Test
    public void uniqueUser(){
        assertTrue(LoginActivity.uniqueUsername(user,sampleUsers));
    }

    @Test
    public void validatePassword(){
        assertTrue(LoginActivity.validPassword(pass));
    }
}
