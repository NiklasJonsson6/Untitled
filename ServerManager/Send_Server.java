package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Daniel.
 */
public class Send_Server
{
    public static void main(String[] args)
    {
        boolean running=true;
        OutputStream os;
        InputStream is;
        Socket socket;

        try
        {
            socket = new Socket("46.239.104.53",4002);
            os = socket.getOutputStream();
            is = socket.getInputStream();
            os.write(MessageType.register_log.id);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println("exiting...");
            return;
        }

        while(running)
        {
            try
            {
                Scanner in = new Scanner(System.in);
                if(System.in.available() != 0)
                    switch (in.next())
                    {
                        case "send":
                        case "s":
                        {
                            // we only get to send one message to the server heh...
                            //it's bad but I don't really care...
                            socket.close();
                            socket = new Socket("46.239.104.53",4002);
                            os = socket.getOutputStream();
                            is = socket.getInputStream();
                            os.write(MessageType.send_server.id);

                            FileInputStream fis = new FileInputStream(args[0]);
                            byte buffer[] = new byte[256];
                            int read;
                            while((read = fis.read(buffer)) != -1)
                            {
                                os.write(buffer,0,read);
                            }
                            fis.close();
                            socket.shutdownOutput();
                        }break;
                        case "exit":
                        case "e":
                        {
                            running = false;
                        } break;
                    }
                if(is.available()!= 0)
                {
                    byte buffer[] = new byte[1024];
                    int read = is.read(buffer);
                    System.out.write(buffer,0,read);
                }

            }
            catch (IOException ex)
            {
                System.out.println(ex.toString());
                ex.printStackTrace();
            }

            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException ex)
            {
                //do nothing
            }
        }
    }
}
