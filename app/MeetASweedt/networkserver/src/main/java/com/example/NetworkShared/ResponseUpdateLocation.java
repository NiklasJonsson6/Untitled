package com.example.NetworkShared;

public class ResponseUpdateLocation extends Response
{
    int longitude;
    int latitude;
    public ResponseUpdateLocation(boolean success, int longitude, int latitude)
    {
        super(MessageType.UpdateLocation,success);
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
