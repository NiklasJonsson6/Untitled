package com.untitledapps.meetasweedt;

/**
 * Created by Shaotime on 10/11/2016.
 */

public class MatchesBlock implements Comparable<MatchesBlock> {
    String mName;
    String mMessage;
    String mTime;
    Person person;



    public MatchesBlock(String message, String time, Person p){
        System.out.println("MatchesBlock Constructor");
        mName = p.getName();
        mMessage = message;
        mTime = time;
        person = p;
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
}
