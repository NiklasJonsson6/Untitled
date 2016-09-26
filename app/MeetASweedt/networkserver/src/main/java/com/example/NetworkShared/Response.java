package com.example.NetworkShared;

import java.io.Serializable;

public class Response implements Serializable
{
    public MessageType type;
    public boolean success;
    public Response(MessageType type, boolean success)
    {
        this.type = type;
        this.success = success;
    }
}
