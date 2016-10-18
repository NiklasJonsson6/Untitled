package com.untitledapps.meetasweedt;

import android.content.Context;
import android.content.res.TypedArray;

import com.example.NetworkShared.RequestLastMessage;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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

    int mresID;

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
                        MatchesBlockList.add(new MatchesBlock(reqs[x].getResponse().getMessage().body, reqs[x].getResponse().getMessage().time_stamp.toString(), matchesList.get(x), createRandomProfilePic() ));
                    }
                    else {
                        MatchesBlockList.add(new MatchesBlock("","", matchesList.get(x), createRandomProfilePic()));
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
    public int createRandomProfilePic(){

        final TypedArray imgs = context.getResources().obtainTypedArray(R.array.arrayyylmao);
        final Random rand = new Random();
        final int rndInt = rand.nextInt(imgs.length());
        mresID = imgs.getResourceId(rndInt, 0);
        System.out.println(mresID);
        return mresID;

    }

    public ArrayList<MatchesBlock> returnList(){
        return MatchesBlockList;
    }
}
