package com.example.NetworkShared;

public class ResponseUpdateLocation extends Response
{
    public ResponseUpdateLocation(boolean success)
    {
        super(MessageType.UpdateLocation,success);
    }
}
