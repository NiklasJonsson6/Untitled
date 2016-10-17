package com.example.NetworkShared;

import java.util.ArrayList;

/**
 * Created by Niklas on 2016-10-17.
 */

public class ResponseGetPerson extends Response {
    private boolean isLearner;
    private String username;
    private int age;
    private String name;
    private String originCountry;
    private float longitude;
    private float latitude;
    private String interests;
    private int id;

    public ResponseGetPerson(boolean success, boolean isLearner, String username, int age, String name, String originCountry, float longitude, float latitude, String interests, int id) {
        super(MessageType.GetPerson, success);
        this.isLearner = isLearner;
        this.username = username;
        this.age = age;
        this.name = name;
        this.originCountry = originCountry;
        this.longitude = longitude;
        this.latitude = latitude;
        this.interests = interests;
        this.id = id;
    }

    public boolean getIsLearner() {
        return isLearner;
    }
    public String getUsername() {
        return username;
    }
    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
    public String getOriginCountry() {
        return originCountry;
    }
    public float getLongitude() {
        return longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public String getInterests() {
        return interests;
    }
    public int getId() {
        return id;
    }
}
