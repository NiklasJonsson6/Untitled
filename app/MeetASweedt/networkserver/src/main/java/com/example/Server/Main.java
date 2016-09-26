package com.example.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main
{
    static AtomicBoolean globalRunning = new AtomicBoolean(true);
    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(4000);
            while (globalRunning.get())
            {
                Socket socket = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(socket);
                handler.run();
            }
        }
        catch(java.io.IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
