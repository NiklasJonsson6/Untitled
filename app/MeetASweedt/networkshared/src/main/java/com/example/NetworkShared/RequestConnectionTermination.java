package com.example.NetworkShared;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestConnectionTermination extends Request
{
    public RequestConnectionTermination()
    {
        super(MessageType.TerminateConnection);
    }

    @Override
    public Object sendRequest(Socket socket) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(this);
        return null;
    }
}