package com.untitledapps.meetasweedt;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;

import java.util.Random;

import static com.untitledapps.meetasweedt.MatchingActivity.context;

/**
 * Created by Shaotime on 10/11/2016.
 */

public class MatchesBlock implements Comparable<MatchesBlock> {
    private String mName;
    private String mMessage;
    private String mTime;
    private Person person;
    private int mresID;

    public MatchesBlock(String message, String time, Person p, int resID){
        System.out.println("MatchesBlock Constructor");
        mName = p.getName();
        mMessage = message;
        mTime = time;
        person = p;
        mresID = resID;
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

    public Person getPerson() {
        return person;
    }

    public int getMresID(){
        return mresID;
    }

}