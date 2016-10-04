package com.example.NetworkShared;

import java.util.ArrayList;

//CREATE USER
public class RequestCreateUser extends Request<ResponseCreateUser>
{
    public RequestCreateUser(boolean isLearner, int age, String name, String orginCountry, float longitude, float latitude, ArrayList<String> interests, String password)
    {
        super(MessageType.CreateUser);
        this.isLearner = isLearner;
        this.age = age;
        this.name = name;
        this.orginCountry = orginCountry;
        this.longitude = longitude;
        this.latitude = latitude;
        this.interests = interests;

        //todo
        this.password = password;
    }

    @Override
    public String toString() {
        return "RequestCreateUser{" +
                "isLearner=" + isLearner +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", orginCountry='" + orginCountry + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", interests=" + interests +
                '}';
    }

    private boolean isLearner;

    private int age;

    //// TODO: 2016-10-04 fix password
    private String password;
    private String name;
    private String orginCountry;

    private float longitude;
    private float latitude;

    private ArrayList<String> interests = new ArrayList<>();


    public boolean isLearner() {
        return isLearner;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getOrginCountry() {
        return orginCountry;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }
}
