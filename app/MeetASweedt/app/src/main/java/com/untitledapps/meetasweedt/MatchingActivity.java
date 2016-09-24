package com.untitledapps.meetasweedt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fredr on 2016-09-24.
 */

public class MatchingActivity extends AppCompatActivity {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
    }

    public void populateMatchingView(ArrayList<Person> personArrayList, Person person){
        gridView = (GridView) this.findViewById(R.id.gridLayout);
        gridView.setAdapter(new MatchViewAdapter(this, personArrayList, person));
    }

    public void viewMatchingProfile(Person person, Person matchingPerson){
        setContentView(R.layout.activity_matching_profile);
        ((TextView) findViewById(R.id.matchProcent)).setText(Float.toString(person.getMatchScore(matchingPerson) * 100) + "%");
        ((TextView) findViewById(R.id.distance)).setText(Float.toString(person.getDistanceTo(matchingPerson)) + "Km");
    }
}