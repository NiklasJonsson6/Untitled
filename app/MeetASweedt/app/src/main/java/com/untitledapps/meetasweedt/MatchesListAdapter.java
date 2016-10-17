package com.untitledapps.meetasweedt;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;
import static android.R.id.message;
import static com.untitledapps.meetasweedt.MatchingActivity.context;

/**
 * Created by User on 10/11/2016.
 */

public class MatchesListAdapter{

    ArrayList<MatchesBlock>MatchesBlockList;

    /*public MatchesListAdapter(ArrayList<Person> matchesList, String message, String time) {
        ArrayList<MatchesBlock>MatchesBlockList = new ArrayList<MatchesBlock>();

        for(int x = 0; x < matchesList.size(); x++) {
            MatchesBlockList.add(new MatchesBlock(matchesList.get(x).getName(), message, time));
        }

    }*/


    public MatchesListAdapter(ArrayList<Person> matchesList) {
        MatchesBlockList = new ArrayList<MatchesBlock>();

        for(int x = 0; x < matchesList.size(); x++) {
            MatchesBlockList.add(new MatchesBlock("Message", "Time", matchesList.get(x)));
        }
    }

    public ArrayList<MatchesBlock> returnList(){
        return MatchesBlockList;


    }
}
