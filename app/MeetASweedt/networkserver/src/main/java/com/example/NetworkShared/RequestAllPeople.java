package com.example.NetworkShared;

import java.util.ArrayList;

/**
 * Created by Arvid on 2016-10-08.
 */

public class RequestAllPeople extends Request<ResponseAllPeople> {
    public RequestAllPeople(String username){
        super(MessageType.GetAllPeople);
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private final String format = "isLearner, age, name, orginCountry, longitude, latitude, interests, username, user_id";

    //private ArrayList<String> playerStrings = new ArrayList<>();

    //private String allPersonString = "";

    /*private boolean isLearner;

    private ArrayList<Integer> age = new ArrayList<>();

    private ArrayList<String> username = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> orginCountry = new ArrayList<>();
    private ArrayList<String> InterestsString = new ArrayList<>();

    private ArrayList<Float> longitude = new ArrayList<>();
    private ArrayList<Float> latitude = new ArrayList<>();

    public void addAge
    */

    /*public void addPersonString(String personString){
        //System.out.println("\nadding string to personStrings: " + personString);
        //playerStrings.add(personString);
        //System.out.println("playerStrings: " + playerStrings.toString());

        if(personString.equals("")){
            allPersonString += personString;
        } else {
            allPersonString += ";" + personString;
        }
    }*/

    public String getFormat() {
        return format;
    }

    /*public String getAllPersonStrings() {
        return allPersonString;
    }*/
}
