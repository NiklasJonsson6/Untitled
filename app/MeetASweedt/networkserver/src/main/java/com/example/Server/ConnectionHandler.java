package com.example.Server;


import com.example.NetworkShared.*;

import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.sql.*;
/**
 * @author Daniel.
 */
public class ConnectionHandler implements Runnable
{
    private Socket socket;
    @Override
    public void run()
    {
        try
        {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ResultSet res;

            System.out.println("connection started");

            boolean running = true;
            while (running)
            {
                Request msg = (Request) ois.readObject();
                System.out.println("server got message: " + msg.type);
                switch (msg.type)
                {
                    case CreateUser:
                    {

                        RequestCreateUser createUser = (RequestCreateUser) msg;
                        System.out.println(createUser);

                        String insert_user_sql = "INSERT into user_table "+
                                "(first_name,last_name,hashed_password,user_type,bio,user_name, longitude, latitude)"+
                                " VALUES "+
                                "(?, ?, ?, ?, ?, ?, ?, ?)";


                        String hashed_password;
                        try
                        {
                            hashed_password = PasswordStorage.createHash(createUser.password);
                        }
                        catch (PasswordStorage.CannotPerformOperationException ex)
                        {
                            oos.writeObject(new ResponseCreateUser(false,-1));
                            break;
                        }

                        PreparedStatement preparedStatement = conn.prepareStatement(insert_user_sql);
                        preparedStatement.setString(1,createUser.firstName);
                        preparedStatement.setString(2,createUser.lastName);
                        preparedStatement.setString(3, hashed_password);
                        preparedStatement.setString(4,(createUser.isSwedish ? "swedish_speaker": "swedish_learner"));
                        preparedStatement.setString(5, createUser.bio);
                        preparedStatement.setString(6, createUser.userName);
                        preparedStatement.setInt(7, createUser.longitude);
                        preparedStatement.setInt(8, createUser.latitude);

                        res = preparedStatement.executeQuery();

                        res = conn.createStatement().executeQuery("SELECT @@IDENTITY");
                        res.next();
                        oos.writeObject(new ResponseCreateUser(true,res.getInt(1)));
                    }break;
                    case UpdateLocation:
                    {
                        RequestUpdateLocation updateLocation = (RequestUpdateLocation) msg;

                    }
                    case SendMessage:
                    {
                        RequestSendMessage sendMessage = (RequestSendMessage) msg;
                        String sql = "INSERT INTO message_table (from_id, to_id, message_body) VALUES (?,?,?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setInt(1,sendMessage.from_id);
                        preparedStatement.setInt(2,sendMessage.to_id);
                        preparedStatement.setString(3,sendMessage.message);
                        boolean success = preparedStatement.executeUpdate() == 1;
                        oos.writeObject(new Response(MessageType.SendMessage,success));
                    }break;
                    case TerminateConnection:
                        running = false;
                        break;
                    default:
                    {
                        System.err.println("msg type is not handled " + msg.type);
                    }
                }
            }
            socket.close();
            System.out.println("connection terminated");
        }
        catch (IOException | ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public ConnectionHandler(Socket socket)
    {
        this.socket = socket;
    }
}
