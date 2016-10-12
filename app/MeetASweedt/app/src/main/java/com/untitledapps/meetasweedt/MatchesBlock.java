package com.untitledapps.meetasweedt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by User on 10/11/2016.
 */

public class MatchesBlock extends Activity implements Comparable<MatchesBlock> {
    String mName;
    String mMessage;
    String mTime;
    TextView tName;
    TextView tMessage;
    TextView tTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches);

        tName =(TextView) findViewById(R.id.nameText);
        tMessage =(TextView) findViewById(R.id.recentMessageText);
        tTime =(TextView) findViewById(R.id.timeText);



    }

    public MatchesBlock(String name/*, String message, String time*/){
        mName = name;
        //mMessage = message;
        //mTime = time;
        tName.setText(mName);
        tMessage.setText(mMessage);
        tTime.setText(mTime);

    }


    @Override
    public int compareTo(MatchesBlock o) {
        return 0;
    }
}
