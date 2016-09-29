package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etCountry = (EditText) findViewById(R.id.etCountry);
        final TextView titleMsg = (TextView) findViewById(R.id.twWelcome2);
        final TextView titleMsg2 = (TextView) findViewById(R.id.twWelcome2);

        final Button buttonMatches = (Button) findViewById(R.id.buttonMatches);
        final Button buttonChat = (Button) findViewById(R.id.buttonChat);
        final Button mapButton = (Button) findViewById(R.id.mapButton);

        //////MATCHES
        buttonMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMatchingActivity();
            }
        });

        //////MATCHES
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChatActivity();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMapActivity();
            }
        });

    }

    private void goToMatchingActivity() {
        Intent intent = new Intent(this, MatchingActivity.class);
        startActivity(intent);
    }

    private void goToChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    private void goToMapActivity(){
        Intent intent = new Intent(this, FikaMapActivity.class);
        startActivity(intent);
    }
}
