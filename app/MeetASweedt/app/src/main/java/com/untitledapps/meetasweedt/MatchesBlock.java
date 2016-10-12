package com.untitledapps.meetasweedt;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import static com.untitledapps.meetasweedt.MatchingActivity.context;
import static com.untitledapps.meetasweedt.R.id.entryContainer;
import static com.untitledapps.meetasweedt.R.layout.matches;

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

       // View container = matches.findViewById(R.id.entryContainer);


        //tName =(TextView) entryContainer.findViewById(R.id.nameText);
        tMessage =(TextView) findViewById(R.id.recentMessageText);
        System.out.println("onCreate MatchesBlock");
        tTime =(TextView) findViewById(R.id.timeText);

    }



    public MatchesBlock(String name /*, String message, String time*/){
        System.out.println("MatchesBlock Constructor");
        mName = name;
        //mMessage = message;
        //mTime = time;
       // tMessage.setText(mMessage);
      //  tTime.setText(mTime);

    }

    public void setName(){
        System.out.println(tName);
      //  tName.setText(mName);

    }


    @Override
    public int compareTo(MatchesBlock o) {
        return 0;
    }
}
