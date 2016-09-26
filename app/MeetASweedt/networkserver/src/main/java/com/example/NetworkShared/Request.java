package com.example.NetworkShared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public abstract class Request<T extends Response> implements Serializable
{
    public Request(MessageType type)
    {
        this.type = type;
    }

    public MessageType type;
    public transient T response;

    public void sendRequest(ObjectInputStream ois, ObjectOutputStream oos) throws IOException
    {
        oos.writeObject(this);
        try
        {
           response = (T)ois.readObject();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
            response = null;
        }
    }
}
