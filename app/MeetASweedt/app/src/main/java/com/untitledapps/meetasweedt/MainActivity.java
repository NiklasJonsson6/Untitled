package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    // list of layout ids, found in R.java, after creating layout. Add when new are created!
    final static int[] SCREENS = {
            R.layout.activity_main, R.layout.activity_matching, R.layout.activity_sign_in, R.layout.activity_sign_up
    };

    private int currentScreen = R.layout.activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signup, signin;
        signup = (Button) findViewById(R.id.button);
        signin = (Button) findViewById(R.id.button);

        // signup button and clicklistener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpActivity();
            }
        });

        // signin button and clicklistener
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToSignInActivity();
                goToMatchingActivity();
            }
        });

    };


    //sign up activity
    private void goToSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

    //sign in activity
    private void goToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);

    }

    private void goToMatchingActivity() {
        Intent intent = new Intent(this, MatchingActivity.class);
        startActivity(intent);
    }



    public void switchToScreen(int layoutId) {
        // show screen from arg, hide the rest
        for (int id : SCREENS) {
            if (findViewById(id) != null) {
                findViewById(id).setVisibility(layoutId == id ? View.VISIBLE : View.GONE);
            }
        }
        currentScreen = layoutId;
    }
}
