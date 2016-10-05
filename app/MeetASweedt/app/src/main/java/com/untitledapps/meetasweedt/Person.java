package com.untitledapps.meetasweedt;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.NetworkShared.RequestCreateUser;
import com.example.NetworkShared.RequestVerifyPassword;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class Person {
    private boolean isLearner;

    private String username;
    private int age;
    private String name;
    private String orginCountry;

    private float longitude;
    private float latitude;

    //database request builder

    private ArrayList<String> interests = new ArrayList<>();

    public Person(boolean isLearner, int age, String name, String orginCountry, float longitude, float latitude, ArrayList<String> interests, String username) {
        this.isLearner = isLearner;
        this.age = age;
        this.name = name;
        this.orginCountry = orginCountry;
        this.longitude = longitude;
        this.latitude = latitude;
        this.interests = interests;
        this.username = username;
    }

    public void databaseCreateUser() {
        //TODO Properties of the database person and the person object has to be the same!
        RequestCreateUser req = new RequestCreateUser(this.isLearner, this.age, this.name, this.orginCountry, this.longitude, this.latitude, this.interests, this.username, "password");
        /*RequestBuilder requestBuilder = new RequestBuilder(this, RequestBuilder.Action());
        requestBuilder.addRequest(req);
        try {
            requestBuilder.execute().get();
        } catch (InterruptedException|ExecutionException ex) {
            ex.printStackTrace();
        }*/
    }

    public void setLearner(boolean learner) {
        isLearner = learner;
    }

    public boolean isLearner() {
        return isLearner;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrginCountry() {
        return orginCountry;
    }

    public void setOrginCountry(String orginCountry) {
        this.orginCountry = orginCountry;

        //shoudld we check this?
        //should we accept people from other countries to be "teachers" if they know swedish, Finland for example?
        /*
        if(orginCountry.toLowerCase().equals("sweden")) {
            isLearner = false;
        } else {
            isLearner = true;
        }*/
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    @Override
    public String toString() {
        return "Person{" +
                "isLearner=" + isLearner +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", orginCountry='" + orginCountry + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", interests=" + interests +
                '}';
    }

    //TODO (hardcore) if someone want to be hardcore, include altitude differance: http://en.wikipedia.org/wiki/Vincenty%27s_formulae
    public float getDistanceTo(float lat1, float long1)
    {
        float lat2 = this.getLatitude ();
        float long2 = this.getLongitude ();

        final float DEGREES_TO_RADIANS = 3.14159265f / 180f;

        float deltaLongitude = (long2 - long1); //* DEGREES_TO_RADIANS;
        float deltaLatitude = (lat2 - lat1);// * DEGREES_TO_RADIANS;

        float deltaLatitudeDividedBy2 = deltaLatitude / 2.0f;
        float deltaLongitudeDividedBy2 = deltaLongitude / 2.0f;

        double a = Math.sin((double)deltaLatitudeDividedBy2) * Math.sin(deltaLatitudeDividedBy2) +
                Math.cos(lat1 * DEGREES_TO_RADIANS) * Math.cos(lat2 * DEGREES_TO_RADIANS) * Math.sin(deltaLongitudeDividedBy2) * Math.sin(deltaLongitudeDividedBy2);

        Log.d("personClass", " a: " + a + " sqrt(q)" + Math.sqrt(a));

        double c = 2f * Math.atan2(Math.sqrt((float)a), (float)Math.sqrt(1.0f - a));

        //equator radius sweden = 6362351 metres (59.3293 lattitude for Stockholm):
        //TODO (hardcore) change depending on where you are (error is small but there ex. 300 meter differance Gothenburg, Stockholm ) https://en.wikipedia.org/wiki/Earth_radius
        float distance = 6362351f * (float)c / 100f; //figure out 100 multiplier
        return distance;
    }

    public float getDistanceTo(Person other)
    {
        return getDistanceTo(other.getLatitude(), other.getLongitude());
    }

    public float getMatchScore(Person other){
        final int matchingTests = 2;
        float matchingScore = 0;
        if(other.isLearner() == this.isLearner()){
            return 0;
        } else {

            int interestMatches = 0;
            for(String personsInterest: interests) {
                for(String othersInterest: other.getInterests()) {
                    if(personsInterest.toLowerCase().equals(othersInterest.toLowerCase())){
                        matchingScore += 1f / Math.pow(2, ++interestMatches);
                    }
                }
            }

            //enter preference ?
            matchingScore += 1f - Math.min(1, Math.pow(Math.abs(this.getAge()-other.getAge()), 1.1f)/25);

            //slightly sqrt to bring score up, looks better
            return (float)Math.pow((matchingScore / matchingTests), 1/1.2f);
        }
    }

    public List<String> getCommonInterests(Person other) {
        List<String> commonIntersts = new ArrayList<>();
        for (String personsInterest : interests) {
            for (String othersInterest : other.getInterests()) {
                if (personsInterest.toLowerCase().equals(othersInterest.toLowerCase())) {
                    commonIntersts.add(personsInterest.toLowerCase());
                }
            }
        }
        return commonIntersts;
    }
}
