package com.example.NetworkShared;

import java.util.ArrayList;

/**
 * Created by Arvid on 2016-10-08.
 */
public class ResponseAllPeople extends Response {
    private ArrayList<String> allPersonString;
    public ResponseAllPeople (boolean success) //csv format
    {
        super(MessageType.GetAllPeople,success);
    }

    public ArrayList<String> getAllPersonString() {
        return allPersonString;
    }

    private ArrayList<Boolean> isLearner = new ArrayList<>();

    private ArrayList<Integer> age = new ArrayList<>();

    private ArrayList<String> username = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> orginCountry = new ArrayList<>();
    private ArrayList<String> InterestsString = new ArrayList<>();

    private ArrayList<Float> longitude = new ArrayList<>();
    private ArrayList<Float> latitude = new ArrayList<>();

    private ArrayList<Integer> user_id = new ArrayList<>();


    public void setAllPersonString(ArrayList<String> allPersonString) {
        this.allPersonString = allPersonString;
    }

    public ArrayList<Boolean> getIsLearner() {
        return isLearner;
    }

    public void setIsLearner(ArrayList<Boolean> isLearner) {
        this.isLearner = isLearner;
    }

    public ArrayList<Integer> getAge() {
        return age;
    }

    public void setAge(ArrayList<Integer> age) {
        this.age = age;
    }

    public ArrayList<String> getUsername() {
        return username;
    }

    public void setUsername(ArrayList<String> username) {
        this.username = username;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getOrginCountry() {
        return orginCountry;
    }

    public void setOrginCountry(ArrayList<String> orginCountry) {
        this.orginCountry = orginCountry;
    }

    public ArrayList<String> getInterestsString() {
        return InterestsString;
    }

    public void setInterestsString(ArrayList<String> interestsString) {
        InterestsString = interestsString;
    }

    public ArrayList<Float> getLongitude() {
        return longitude;
    }

    public void setLongitude(ArrayList<Float> longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Float> getLatitude() {
        return latitude;
    }

    public void setLatitude(ArrayList<Float> latitude) {
        this.latitude = latitude;
    }

    public ArrayList<Integer> getUser_id() {
        return user_id;
    }

    public void setUser_id(ArrayList<Integer> user_id) {
        this.user_id = user_id;
    }
}
