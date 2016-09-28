package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import com.example.NetworkShared.RequestCreateUser;
import com.example.NetworkShared.RequestVerifyPassword;
import com.untitledapps.Client.RequestBuilder;

import java.util.concurrent.ExecutionException;


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

        /*
               How to request for stuff.
        RequestBuilder requestBuilder = new RequestBuilder();
        RequestCreateUser req = new RequestCreateUser("asdasdlkjb", "Daniel", "Hesslow", "hunter2", true, "I'm cool yoo. And I've got this sick bio.",20,21);
        RequestVerifyPassword req_v = new RequestVerifyPassword("asdasdlkjb","hunter2");
        requestBuilder.addRequest(req);
        requestBuilder.addRequest(req_v);
        try
        {
            // NOTE this blocks thread
            // You can also continue do stuff and check in if it's completed
            requestBuilder.execute().get();
            if(req_v.was_successfull())
                System.out.println(" user_id = "+req_v.getResponse().user_id);
            else
                System.out.println("invalid user_name and stuff");
        }
        catch (InterruptedException|ExecutionException ex)
        {
            ex.printStackTrace();
        }
        */

        Button signup, signin;
        signup = (Button) findViewById(R.id.buttonSignUp);
        signin = (Button) findViewById(R.id.buttonSignIn);

        // signup button and clicklistener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToSignUpActivity();
            }
        });

        // signin button and clicklistener
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignInActivity();
                //goToMatchingActivity();
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
