package com.untitledapps.meetasweedt;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.NetworkShared.RequestCreateUser;

import java.util.ArrayList;
import java.util.List;

public class Person implements Parcelable {
    private boolean isLearner;

    private String username;
    private int age;
    private String name;
    private String orginCountry;

    private float longitude;
    private float latitude;

    private int user_id;

    //database request builder

    private ArrayList<String> interests = new ArrayList<>();

    public Person() {}

    public Person(boolean isLearner, int age, String name, String orginCountry, float longitude, float latitude, ArrayList<String> interests, String username, int user_id) {
        this.isLearner = isLearner;
        this.age = age;
        this.name = name;
        this.orginCountry = orginCountry;
        this.longitude = longitude;
        this.latitude = latitude;
        this.interests = interests;
        this.username = username;
        this.user_id = user_id;
    }

    //Parcelable constructor
    private Person(Parcel in) {
        isLearner = (boolean)in.readValue(null);

        int[] integers = new int[2];
        in.readIntArray(integers);
        age = integers[0];
        user_id = integers[1];

        String[] strings = new String[3];
        in.readStringArray(strings);
        username = strings[0];
        name = strings[1];
        orginCountry = strings[2];

        float[] floats = new float[2];
        in.readFloatArray(floats);
        longitude = floats[0];
        latitude = floats[1];

        in.readStringList(interests);
    }
    /*
    Parcelable stuff (used in the intent to start ChatActivity)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isLearner);
        dest.writeIntArray(new int[] {age, user_id});
        dest.writeStringArray(new String[] {username, name, orginCountry});
        dest.writeFloatArray(new float[] {longitude, latitude});
        dest.writeStringList(interests);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    /*public void databaseCreateUser() {
        //TODO Properties of the database person and the person object has to be the same!
        RequestCreateUser req = new RequestCreateUser(this.isLearner, this.age, this.name, this.orginCountry, this.longitude, this.latitude, this.interests, this.username, "password");


        /*RequestBuilder requestBuilder = new RequestBuilder(this, RequestBuilder.Action());
        requestBuilder.addRequest(req);
        try {
            requestBuilder.execute().get();
        } catch (InterruptedException|ExecutionException ex) {
            ex.printStackTrace();
        }*/
    //}

    public Person(String personString) {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
                ", username='" + username + '\'' +
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

        float deltaLongitude = (long2 - long1);
        float deltaLatitude = (lat2 - lat1);

        float deltaLatitudeDividedBy2 = deltaLatitude / 2.0f;
        float deltaLongitudeDividedBy2 = deltaLongitude / 2.0f;

        double a = Math.sin((double)deltaLatitudeDividedBy2) * Math.sin(deltaLatitudeDividedBy2) +
                Math.cos(lat1 * DEGREES_TO_RADIANS) * Math.cos(lat2 * DEGREES_TO_RADIANS) * Math.sin(deltaLongitudeDividedBy2) * Math.sin(deltaLongitudeDividedBy2);

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
        final int matchingTests = 3;
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

            final float CLOSE_RAD = 10000;
            final float FAR_RAD = 30000;

            if(getDistanceTo(other) < FAR_RAD) {
                matchingScore += .5f;

                float distance = getDistanceTo(other);


                matchingScore += distance < CLOSE_RAD? getDistanceTo(other) / (2 * CLOSE_RAD) : 0;
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

    public String getCommonIntrestsAsString(Person other){
        List<String> l = getCommonInterests(other);
        StringBuffer buf = new StringBuffer();
        for(String intrest : l) {
            buf.append(intrest + "/");
        }
        return buf.toString();
    }

    public String getIntrestAsString(){
        List<String> l = getInterests();
        StringBuffer buf = new StringBuffer();
        for(String intrest : l) {
            buf.append(intrest + "/");
        }
        return buf.toString();
    }
}
