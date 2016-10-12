package com.example.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main
{
    static AtomicBoolean globalRunning = new AtomicBoolean(true);
    public static void main(String[] args)
    {
        while(true)//aint nobody gonna crash us we're gonna run forever.
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
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

}
