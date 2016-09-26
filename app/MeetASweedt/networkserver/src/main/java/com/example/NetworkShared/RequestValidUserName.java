package com.example.NetworkShared;

// VALID USER NAME
public class RequestValidUserName extends SecureRequest
{
    public RequestValidUserName (int user_id, String password)
    {
        super(MessageType.ValidUserName,user_id,password);
    }
    String user_name;
}
