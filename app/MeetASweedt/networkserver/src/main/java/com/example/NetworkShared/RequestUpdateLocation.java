package com.example.NetworkShared;

public class RequestUpdateLocation extends SecureRequest<ResponseUpdateLocation>
{
    public RequestUpdateLocation(int user_id,String password, int longitude, int latitude)
    {
        super(MessageType.UpdateLocation,user_id, password);
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public int longitude;
    public int latitude;
}
