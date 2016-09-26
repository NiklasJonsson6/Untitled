package com.example.NetworkShared;

public class ResponseValidUserName extends Response
{
    public ResponseValidUserName (boolean success)
    {
        super(MessageType.ValidUserName,success);
    }
    public boolean is_valid;
}
