package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        final Button buttonMatches = (Button) findViewById(R.id.buttonMatches);
        final Button buttonChat = (Button) findViewById(R.id.buttonChat);

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

    }

    private void goToMatchingActivity() {
        Intent intent = new Intent(this, MatchingActivity.class);
        startActivity(intent);
    }

    private void goToChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}
