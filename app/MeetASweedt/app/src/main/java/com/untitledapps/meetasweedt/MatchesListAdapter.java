package com.untitledapps.meetasweedt;

import android.content.Context;

import com.example.NetworkShared.RequestLastMessage;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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


    public MatchesListAdapter(final ArrayList<Person> matchesList,Context context) {
        MatchesBlockList = new ArrayList<MatchesBlock>();

        Person loggedInPerson= ((MeetASweedt) context.getApplicationContext()).getLoggedInPerson();


        final RequestLastMessage reqs[] = new RequestLastMessage[matchesList.size()];
        RequestBuilder builder = new RequestBuilder(context, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                for(int x = 0; x < matchesList.size(); x++) {
                    if(reqs[x].was_successfull())
                    {
                        MatchesBlockList.add(new MatchesBlock(reqs[x].getResponse().getMessage().body, reqs[x].getResponse().getMessage().time_stamp.toString(), matchesList.get(x)));
                    }
                    else {
                        MatchesBlockList.add(new MatchesBlock("","", matchesList.get(x)));
                    }
                }
            }
        });

        for(int i = 0; i<reqs.length;i++)
        {
            reqs[i] = new RequestLastMessage(loggedInPerson.getUsername(),matchesList.get(i).getUsername());
            builder.addRequest(reqs[i]);
        }

        builder.execute();

    }

    public ArrayList<MatchesBlock> returnList(){
        return MatchesBlockList;
    }
}
