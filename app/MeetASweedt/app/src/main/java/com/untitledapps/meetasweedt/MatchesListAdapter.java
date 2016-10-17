package com.untitledapps.meetasweedt;

import java.util.ArrayList;

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
