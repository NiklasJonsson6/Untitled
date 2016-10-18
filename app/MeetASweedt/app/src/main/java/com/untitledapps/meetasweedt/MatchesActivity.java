package com.untitledapps.meetasweedt;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.NetworkShared.RequestAllPeople;
import com.example.NetworkShared.RequestLastMessage;
import com.example.NetworkShared.RequestMatches;
import com.example.NetworkShared.ResponseAllPeople;
import com.example.NetworkShared.ResponseMatches;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static com.untitledapps.meetasweedt.R.id.drawer_layout;
import static com.untitledapps.meetasweedt.R.id.matchesList;

public class MatchesActivity extends AppCompatActivity {
    ListView listView;
    Context context;

    //Nav variables
    private ListView mDrawerList;
    private DrawerAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    Person user = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")), "qwe", 21);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = ((MeetASweedt) getApplicationContext()).getLoggedInPerson();

        this.context = this;
        this.setContentView(R.layout.activity_matches);
        listView = (ListView) findViewById(matchesList);
        listView.setClickable(true);

        final RequestMatches req = new RequestMatches(user.getUser_id());


        final RequestAllPeople requestAllPeople = new RequestAllPeople(user.getUsername());

        final ArrayList<Person> peopleFromDatabase = new ArrayList<>();
        final Context cc = this;

        RequestBuilder requestBuilder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (requestAllPeople.was_successfull()) {
                    ResponseAllPeople response = requestAllPeople.getResponse();

                    if(response != null) {
                        System.out.println("getpeople request successfull");

                        for(int i = 0; i < response.getIsLearner().size(); i++) {
                            String interestsString = response.getInterestsString().get(i);

                            ArrayList<String> interests = new ArrayList<>();
                            String[] parts = interestsString.split(",");

                            for(String part: parts) {
                                interests.add(part);
                            }

                            Person person = new Person(
                                    response.getIsLearner().get(i),
                                    response.getAge().get(i),
                                    response.getName().get(i),
                                    response.getOrginCountry().get(i),
                                    response.getLongitude().get(i),
                                    response.getLatitude().get(i),
                                    interests,
                                    response.getUsername().get(i),
                                    response.getUser_id().get(i)
                            );
                            peopleFromDatabase.add(person);
                        }
                        //System.out.println("playerStrings(1): " + peopleStrings.get(2).toString());
                    } else {
                        System.out.println("no response when fetching people from database");
                    }

                    //SECTION matchesrequest

                    final ArrayList<Person> matches = new ArrayList<>();

                    RequestBuilder requestBuilderMatches = new RequestBuilder(context, new RequestBuilder.Action() {
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

                                        for(Person person: peopleFromDatabase) {
                                            if(ids[i] == person.getUser_id()){
                                                matches.add(person);
                                            }
                                        }

                                    }


                                    final ArrayList<MatchesBlock> MatchesBlockList;
                                    {
                                        MatchesBlockList = new ArrayList<MatchesBlock>();

                                        Person loggedInPerson= ((MeetASweedt) context.getApplicationContext()).getLoggedInPerson();


                                        final RequestLastMessage reqs[] = new RequestLastMessage[matches.size()];
                                        RequestBuilder builder = new RequestBuilder(context, new RequestBuilder.Action() {
                                            @Override
                                            public void PostExecute() {
                                                for(int x = 0; x < matches.size(); x++) {
                                                    if(reqs[x].was_successfull())
                                                    {
                                                        MatchesBlockList.add(new MatchesBlock(reqs[x].getResponse().getMessage().body, reqs[x].getResponse().getMessage().time_stamp.toString(), matches.get(x)));
                                                    }
                                                    else {
                                                        MatchesBlockList.add(new MatchesBlock("","", matches.get(x)));
                                                    }
                                                }

                                                final MatchChatAdapter arrayAdapter = new MatchChatAdapter(context,MatchesBlockList);
                                                listView.setAdapter(arrayAdapter);
                                            }
                                        });

                                        for(int i = 0; i<reqs.length;i++)
                                        {
                                            reqs[i] = new RequestLastMessage(loggedInPerson.getUsername(),matches.get(i).getUsername());
                                            builder.addRequest(reqs[i]);
                                        }

                                        builder.execute();
                                    }

                                } else {
                                    System.out.println("no response when fetching people from database");
                                }


                                mDrawerList = (ListView)findViewById(R.id.navList);
                                addDrawerItems();

                                mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                                mActivityTitle = getTitle().toString();

                                ((TextView)findViewById(R.id.drawer_person_name)).setText(((MeetASweedt) getApplicationContext()).getLoggedInPerson().getName());
                                //((ImageView)findViewById(R.id.drawer_person_pic)).setImageResource(R.mipmap.ic_launcher);

                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                getSupportActionBar().setHomeButtonEnabled(true);

                                setupDrawer();

                                //previously in postcreate
                                mDrawerToggle.syncState();

                                //why can't i click things tho
                                    /* mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                                        System.out.println("Fail");
                                    }
                                    });*/
                            } else {
                                System.out.println("getpeople request failed");
                            }
                        }
                    });

                    requestBuilderMatches.addRequest(req);
                    requestBuilderMatches.execute();

                    //END of section matchesrequest

                } else {
                    System.out.println("getpeople request failed");
                }
            }
        });


        requestBuilder.addRequest(requestAllPeople);
        requestBuilder.execute();
    }

    //Nav classes
    private void addDrawerItems() {
        ArrayList<String> activities = new ArrayList<String>();
        activities.add("My Profile");
        activities.add("Chat");
        activities.add("Match");
        activities.add("Map");
        mAdapter = new DrawerAdapter(this, activities);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setLogo(R.mipmap.ic_drawericon);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.intent_action) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}