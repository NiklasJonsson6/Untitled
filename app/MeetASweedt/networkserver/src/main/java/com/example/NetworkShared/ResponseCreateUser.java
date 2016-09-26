package com.example.NetworkShared;

public class ResponseCreateUser extends Response
{
    public ResponseCreateUser (boolean success, int user_id)
    {
        super(MessageType.CreateUser,success);
        this.user_id = user_id;
    }
    public int user_id;
}
