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
    private transient Response response;
    public void sendRequest(ObjectInputStream ois, ObjectOutputStream oos) throws IOException
    {
        oos.writeObject(this);
        try
        {
            response = (Response)(ois.readObject());
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
            response = null;
        }
    }

    public boolean was_successfull()
    {
        return response != null && response.success;
    }

    public String getError()
    {
        return response.errorMessage;
    }

    public T getResponse()
    {
        return (T)response;
    }

    public boolean majorError()
    {
        //OMG JAVA is this seriously the only way to do it??
        try
        {
            T r = (T)response;
            return false;
        }
        catch (ClassCastException ex)
        {
            return true;
        }
    }

    public void setRespone(Response response)
    {
        this.response = response;
    }
}
