package com.example.NetworkShared;

public class RequestUpdateLocation extends Request<ResponseUpdateLocation>
{
    public RequestUpdateLocation(int user_id, float longitude, float latitude)
    {
        super(MessageType.UpdateLocation);
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
    }
    public float longitude;
    public float latitude;
    private int user_id;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
