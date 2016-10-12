package com.untitledapps.meetasweedt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.NetworkShared.RequestMatches;
import com.example.NetworkShared.ResponseMatches;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.untitledapps.meetasweedt.MatchingActivity.context;
import static com.untitledapps.meetasweedt.R.id.gridView;
import static com.untitledapps.meetasweedt.R.id.matchesList;

public class MatchesActivity extends AppCompatActivity {
    ListView listView;
    Context context;

    //TODO  get logged in person
    Person user = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")), "qwe", 21);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        this.setContentView(R.layout.activity_matches);
        listView = (ListView) findViewById(matchesList);

        final RequestMatches req = new RequestMatches(user.getUser_id());

        final ArrayList<Person> personsFromDatabase = MatchingActivity.getAllPeopleDb(this, user);

        final ArrayList<Person> matches = new ArrayList<>();

        RequestBuilder requestBuilder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (req.was_successfull()) {
                    ResponseMatches response = req.getResponse();
                    if(response != null) {
                        System.out.println("getpeople request successfull");

                        String matchesString = response.getStringOfMatches();

                        String[] idStrings = matchesString.split(",");

                        int[] ids = new int[idStrings.length];

                        for(int i = 0; i < idStrings.length; i++) {
                            ids[i] = Integer.parseInt(idStrings[i]);

                            for(Person person: personsFromDatabase) {
                                if(ids[i] == person.getUser_id()){
                                    matches.add(person);
                                }
                            }

                        }
                        // MatchesListAdapter MLA = new MatchesListAdapter(matches);
                        final MatchChatAdapter arrayAdapter = new MatchChatAdapter(context, new MatchesListAdapter(matches).returnList());
                        listView.setAdapter(arrayAdapter);

                        /*for(int i = 0; i < response.getIsLearner().size(); i++) {
                            String interestsString = response.getInterestsString().get(i);

                            ArrayList<String> interests = new ArrayList<>();
                            String[] parts = interestsString.split(",");

                            for(String part: parts) {
                                interests.add(part);
                            }
                        }*/

                        //System.out.println("playerStrings(1): " + peopleStrings.get(2).toString());
                    } else {
                        System.out.println("no response when fetching people from database");
                    }
                } else {
                    System.out.println("getpeople request failed");
                }
            }
        });


        requestBuilder.addRequest(req);
        requestBuilder.execute();

        System.out.println("matches:@@@@@@@@@@@@@@@@@@@@");



        /*//// TODO: 2016-10-05 get from server
        Person p2 = new Person(false, 20, "Niklas Jonsson", "sweden", 57.697724f, 11.988327f, new ArrayList<String>(Arrays.asList("computers", "speakers", "wasting money", "code", "chillin")), "nj");
        Person p3 = new Person(false, 21, "Ajla Cano", "sweden", 57.677724f, 11.968327f, new ArrayList<String>(Arrays.asList("computers", "learning android studio", "code", "unknown")), "ac");
        Person p4 = new Person(true, 20, "Fredrik Lindevall", "Syria", 57.72509804f, 11.77373512f, new ArrayList<String>(Arrays.asList("computers", "code", "ida", "stocks", "chillin")), "fli");
        Person p5 = new Person(true, 20, "Daniel Hesslow", "usa", 57.687724f, 11.968327f, new ArrayList<String>(Arrays.asList("computers", "climbing", "code", "not chilling")), "dh");
        Person p6 = new Person(true, 20, "Eric Shao", "russia", 57.697724f, 11.978327f, new ArrayList<String>(Arrays.asList("computers", "djing", "code", "unknown")), "dj");
        context = this;

        matchesList.add(p2);
        matchesList.add(p3);
        matchesList.add(p4);
        matchesList.add(p5);
        matchesList.add(p6);*/

       // MatchesListAdapter MLA = new MatchesListAdapter(matches, message, time);

        ArrayList<Person>tempList = new ArrayList<>();
        ArrayList<String>interests = new ArrayList<>();
        interests.add("soccer");
        Person p1 = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")), "qwe", 12);
        Person p2 = new Person(false, 20, "Fredrik Mast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")), "NiklasJonsson6", 13);
        MatchesBlock kevin = new MatchesBlock("Kevin", "12/4-2016","Håkan");
        MatchesBlock kevin2 = new MatchesBlock("Kevin", "12/4-2016","Håkan");
        tempList.add(p1);
        tempList.add(p2);


    }

}