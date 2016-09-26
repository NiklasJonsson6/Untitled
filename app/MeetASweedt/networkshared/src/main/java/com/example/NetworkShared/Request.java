package com.example.NetworkShared;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public abstract class Request<T> implements Serializable
{
    public MessageType type;
    public Request(MessageType type)
    {
        this.type = type;
    }
    public abstract T sendRequest(Socket socket) throws IOException;
}
