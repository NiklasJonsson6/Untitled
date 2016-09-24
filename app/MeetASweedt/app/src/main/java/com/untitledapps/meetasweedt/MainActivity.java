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
                goToSignInActivity();
            }
        });

        Person p1 = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));
        Person p2 = new Person(false, 20, "Niklas Jonsson", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "speakers", "wasting money", "code", "chillin")));
        Person p3 = new Person(false, 21, "Ajla Cano", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "learning android studio", "code", "unknown")));
        Person p4 = new Person(true, 20, "Fredrik Lindevall", "Syria", 58, 13, 1500, new ArrayList<String>(Arrays.asList("computers", "code", "ida", "stocks", "chillin")));
        Person p5 = new Person(true, 20, "Daniel Hesslow", "usa", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "climbing", "code", "not chilling")));
        Person p6 = new Person(true, 20, "Eric Shao", "russia", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "djing", "code", "unknown")));

        float matchingScore = p1.getMatchScore(p4);

        Log.d("mainactivity oncreate", "matching percentage:" + Float.toString(matchingScore));
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
