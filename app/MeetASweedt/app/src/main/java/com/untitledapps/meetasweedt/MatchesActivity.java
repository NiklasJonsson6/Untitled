package com.untitledapps.meetasweedt;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import static com.untitledapps.meetasweedt.R.layout.matches;

public class MatchesActivity extends AppCompatActivity {
    ListView listView;
    Context context;
    //Nav variables
    private ListView mDrawerList;
    private DrawerAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    //TODO  get logged in person
    Person user = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")), "qwe", 21);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        this.setContentView(R.layout.activity_matches);
        listView = (ListView) findViewById(matchesList);
        listView.setClickable(true);

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

        //Nav
        mDrawerList = (ListView)findViewById(R.id.navList);
//        addDrawerItems();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        //TODO add the current logged in person name and choosen icon just change the varibles down below
//        ((TextView)findViewById(R.id.drawer_person_name)).setText("Fredrik Dast");
//        ((ImageView)findViewById(R.id.drawer_person_pic)).setImageResource(R.mipmap.ic_launcher);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawer();

        //why can't i click things tho
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {

                System.out.println("fuck");

            }

        });

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
        mDrawerToggle.syncState();
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