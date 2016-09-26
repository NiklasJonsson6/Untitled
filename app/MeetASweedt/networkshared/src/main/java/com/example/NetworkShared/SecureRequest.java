package com.example.NetworkShared;

import java.io.Serializable;


public abstract class SecureRequest implements Serializable
{
    public MessageType type;
    public SecureRequest(MessageType type, int user_id, String password)
    {
        this.type = type;
        this.user_id = user_id;
        this.password = password;
    }
    int user_id;
    String password;
}



