package com.untitledapps.Client;

import com.example.NetworkShared.Request;
import com.example.NetworkShared.RequestConnectionTermination;
import com.example.NetworkShared.RequestCreateUser;
import com.example.NetworkShared.ResponseCreateUser;

import java.io.*;
import java.net.Socket;


//test usage to create a user.
public class Client
{
    public static void main(String[] args)
    {
        while (true)
        {
            try
            {
                Socket socket = new Socket("46.239.104.53", 4000);

                Request<ResponseCreateUser> req = new RequestCreateUser("DUUUDE","Kalle","Persson","hunter2",true,"I'm a nice person, I promise");
                ResponseCreateUser response = req.sendRequest(socket);

                System.out.println("success: " + response.success + " id=" + response.user_id +"!");
                new RequestConnectionTermination().sendRequest(socket);
                socket.close();
                break;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

    }
}
