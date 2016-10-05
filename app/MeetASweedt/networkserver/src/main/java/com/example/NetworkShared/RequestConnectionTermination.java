package com.example.NetworkShared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RequestConnectionTermination extends Request
{
    public RequestConnectionTermination()
    {
        super(MessageType.TerminateConnection);
    }

    @Override
    public void sendRequest(ObjectInputStream ois,ObjectOutputStream oos) throws IOException
    {
        oos.writeObject(this);
    }
}