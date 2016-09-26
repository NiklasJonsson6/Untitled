package com.example.NetworkShared;

public class RequestUpdateLocation extends SecureRequest<ResponseUpdateLocation>
{
    public RequestUpdateLocation(int user_id,String password)
    {
        super(MessageType.UpdateLocation,user_id, password);
    }
    int longitude;
    int latitude;
}
