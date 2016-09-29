package com.untitledapps.meetasweedt;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MatchingActivity extends AppCompatActivity {
    GridView gridView;
    static TextView matchProcent;
    static TextView distance;
    static Activity context;
    static View matchingProfileView;

    Person user = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, 1500, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));

    ArrayList<Person> matchesList = new ArrayList<Person>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MatchingActivity");
        this.setContentView(R.layout.activity_matching);
        Person p2 = new Person(false, 20, "Niklas Jonsson", "sweden", 57.697724f, 11.988327f, 1500, new ArrayList<String>(Arrays.asList("computers", "speakers", "wasting money", "code", "chillin")));
        Person p3 = new Person(false, 21, "Ajla Cano", "sweden", 57.677724f, 11.968327f, 1500, new ArrayList<String>(Arrays.asList("computers", "learning android studio", "code", "unknown")));
        Person p4 = new Person(true, 20, "Fredrik Lindevall", "Syria", 57.72509804f, 11.77373512f, 1500, new ArrayList<String>(Arrays.asList("computers", "code", "ida", "stocks", "chillin")));
        Person p5 = new Person(true, 20, "Daniel Hesslow", "usa", 57.687724f, 11.968327f, 1500, new ArrayList<String>(Arrays.asList("computers", "climbing", "code", "not chilling")));
        Person p6 = new Person(true, 20, "Eric Shao", "russia", 57.697724f, 11.978327f, 1500, new ArrayList<String>(Arrays.asList("computers", "djing", "code", "unknown")));
        context = this;

        matchesList.add(p2);
        matchesList.add(p3);
        matchesList.add(p4);
        matchesList.add(p5);
        matchesList.add(p6);

        populateMatchingView(matchesList, user);

        matchingProfileView = getLayoutInflater().inflate(R.layout.activity_matching_profile, null);
        System.out.println("hey " + matchingProfileView.findViewById(R.id.matchProcent));
        System.out.println("hey " + matchingProfileView.findViewById(R.id.matchProcent));

        initiateLocationServices(user);
    }

    public void populateMatchingView(ArrayList<Person> personArrayList, Person user) {
        gridView = (GridView) this.findViewById(R.id.gridView);
        gridView.setAdapter(new MatchViewAdapter(this, personArrayList, user));
    }

    public static void viewMatchingProfile(Person person, Person matchingPerson) {
        ((Activity) context).setContentView(R.layout.activity_matching_profile);
        //((TextView) findViewById(R.id.matchProcent)).setText(Float.toString(person.getMatchScore(matchingPerson) * 100) + "%");
        //((TextView) findViewById(R.id.distance)).setText(Float.toString(person.getDistanceTo(matchingPerson)) + "Km");
        //((TextView) context.findViewById(R.id.matchProcent)).setText(Float.toString(person.getMatchScore(matchingPerson) * 100) + "%");
        //((TextView) matchingProfileView.findViewById(R.id.distance)).setText(Float.toString(person.getDistanceTo(matchingPerson)) + "Km");
    }

    public void initiateLocationServices(final Person person) {

        LocationManager locationManager;
        LocationListener locationListener;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("matchingact", "long: " + location.getLongitude() + " lat: " + location.getLatitude());

                person.setLatitude((float)location.getLongitude());
                person.setLongitude((float)location.getLatitude());

                //update gui after gps coordinates are updated (to update distance)
                //assums matchesList is already populated
                populateMatchingView(matchesList, user);
            }

            @Override
            public void onProviderDisabled(String s) {
                //TODO notify
                //Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); ??
                //startActivity(i);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //TODO maybe
            }

            @Override
            public void onProviderEnabled(String s) {
                //TODO maybe
            }


        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //TODO lower update freq after working 100%
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

        //locationManager.removeUpdates(locationListener);
    }
}