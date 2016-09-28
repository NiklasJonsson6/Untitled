package com.example.NetworkShared;

import java.io.Serializable;

public class Response implements Serializable
{
    MessageType type;
    boolean success;
    String errorMessage;
    public Response(MessageType type, boolean success)
    {
        this.type = type;
        this.success = success;
    }
    public Response(String errorMessage)
    {
        this.errorMessage = errorMessage;
        this.success = false;
        this.type = MessageType.Error;
    }
    public void setError(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
