package com.untitledapps.meetasweedt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by fredr on 2016-09-24.
 */

public class MatchingActivity extends AppCompatActivity{
    GridView gridView;
    static TextView matchProcent;
    static TextView distance;
    static Activity context;
    static View matchingProfileView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MatchingActivity");
        setContentView(R.layout.activity_matching);
        Person p1 = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));
        Person p2 = new Person(false, 20, "Niklas Jonsson", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "speakers", "wasting money", "code", "chillin")));
        Person p3 = new Person(false, 21, "Ajla Cano", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "learning android studio", "code", "unknown")));
        Person p4 = new Person(true, 20, "Fredrik Lindevall", "Syria", 58, 13, 1500, new ArrayList<String>(Arrays.asList("computers", "code", "ida", "stocks", "chillin")));
        Person p5 = new Person(true, 20, "Daniel Hesslow", "usa", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "climbing", "code", "not chilling")));
        Person p6 = new Person(true, 20, "Eric Shao", "russia", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "djing", "code", "unknown")));
        context = this;
        float matchingScore = p1.getMatchScore(p4);
        ArrayList<Person> p = new ArrayList<Person>();
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p5);
        p.add(p6);
        populateMatchingView(p,p1);
        matchingProfileView = getLayoutInflater().inflate(R.layout.activity_matching_profile, null);
        System.out.println("hey " + matchingProfileView.findViewById(R.id.matchProcent));
        System.out.println("hey " + matchingProfileView.findViewById(R.id.matchProcent));
        Log.d("mainactivity oncreate", "matching percentage:" + Float.toString(matchingScore));
    }

    public void populateMatchingView(ArrayList<Person> personArrayList, Person person){
        gridView = (GridView) this.findViewById(R.id.gridView);
        gridView.setAdapter(new MatchViewAdapter(this, this, personArrayList, person));
    }

    public static void viewMatchingProfile(Person person, Person matchingPerson){
        ((Activity) context).setContentView(R.layout.activity_matching_profile);
        //((TextView) findViewById(R.id.matchProcent)).setText(Float.toString(person.getMatchScore(matchingPerson) * 100) + "%");
        //((TextView) findViewById(R.id.distance)).setText(Float.toString(person.getDistanceTo(matchingPerson)) + "Km");
        //((TextView) context.findViewById(R.id.matchProcent)).setText(Float.toString(person.getMatchScore(matchingPerson) * 100) + "%");
        //((TextView) matchingProfileView.findViewById(R.id.distance)).setText(Float.toString(person.getDistanceTo(matchingPerson)) + "Km");
    }



}