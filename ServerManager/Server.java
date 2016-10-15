package com.company;


import com.sun.corba.se.spi.activation.Server;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

    static boolean debug_mode;

    public static void main(String[] args) {
        debug_mode = args[0].equals("debug");

        if (debug_mode) System.out.println("started in debug mode");
        else { System.out.println("started in normal mode"); };


        byte[] buffer = new byte[256];
        Process server=null;
        ProcessBuilder server_builder = new ProcessBuilder("java","-jar","server_program.jar");
        ServerLogHandler logHandler = new ServerLogHandler();
        LogProcessor processor = new LogProcessor(logHandler);
        try
        {
            server = server_builder.start();
            processor.setIO(server.getInputStream(),server.getErrorStream());
        }
        catch (IOException ex)
        {
            man_log("man: java path variable not set up or server does not exist...");
            man_log("");
            ex.printStackTrace();
        }
        try
        {

            processor.start();
            logHandler.start();

            ServerSocket serverSocket = new ServerSocket(4002);

            while(true)
            {
                try
                {
                    Socket socket = serverSocket.accept();
                    debug_log("socket connected");

                    InputStream in = socket.getInputStream();

                    MessageType messageType = MessageType.from_id(in.read());
                    debug_log("msg of type: "+messageType.toString());
                    switch (messageType)
                    {
                        case send_server:
                        {
                            processor.pause.set(true);
                            debug_log("send server message connected");
                            if(server != null){
                                server.destroyForcibly().waitFor();
                            }
                            man_log("send_server msg received");


                            network_log(logHandler,"send server message recieved");
                            FileOutputStream fos = new FileOutputStream("server_program.jar");
                            int read;
                            while((read = in.read(buffer)) != -1) {
                                fos.write(buffer, 0, read);
                            }
                            server =  server_builder.start();
                            processor.setIO(server.getInputStream(),server.getErrorStream());
                            processor.pause.set(false);
                        }break;
                    }
                    logHandler.addSocket(socket);
                    network_log(logHandler,"New observer joined (now "+logHandler.sockets.size()+")");
                }
                catch (Exception ex)
                {
                    System.out.println("man err: ");
                    ex.printStackTrace();
                }
            }
        }
        catch (IOException ex)
        {
            System.out.println("man: could not open server_manager on port");
            ex.printStackTrace();
        }
    }
    static void debug_log(String s)
    {
        if(debug_mode) System.out.println("debug_mode: "+s);
    }
    static void man_log(String s)
    {

        System.out.println("man: "+s);
    }
    static void network_log(ServerLogHandler handler, String s)
    {
        s+="\n";
        handler.write_to_all_sockets(s.getBytes(),s.length());
    }
}
