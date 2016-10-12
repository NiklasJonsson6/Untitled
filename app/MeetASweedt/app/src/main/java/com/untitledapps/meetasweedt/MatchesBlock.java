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

public class MatchesBlock implements Comparable<MatchesBlock> {
    String mName;
    String mMessage;
    String mTime;
    TextView tName;
    TextView tMessage;
    TextView tTime;



    public MatchesBlock(String name , String message, String time){
        System.out.println("MatchesBlock Constructor");
        mName = name;
        mMessage = message;
        mTime = time;

    }


    @Override
    public int compareTo(MatchesBlock o) {
        return 0;
    }

    public String getmMessage() {
        return mMessage;
    }

    public String getmName() {
        return mName;
    }

    public String getmTime() {
        return mTime;
    }
}
