package com.example.Server;


import com.example.NetworkShared.*;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

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
            ObjectOutputStream oos = null;
            ObjectInputStream ois =null;

            try
            {
                Class.forName("org.mariadb.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");

                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                ResultSet res;

                System.out.println("connection started");

                boolean running = true;
                while (running)
                {
                    try {
                        Request msg = (Request) ois.readObject();

                        System.out.println("server got message: " + msg.type);
                        switch (msg.type) {
                            case CreateUser: {

                                RequestCreateUser createUser = (RequestCreateUser) msg;
                                System.out.println(createUser);

                                String insert_user_sql = "INSERT IGNORE into user_table " +
                                        "(isLearner,age,name,orginCountry,longitude,latitude,interests,username,hashed_password)" +
                                        " VALUES " +
                                        "(?, ?, ?, ?, ?, ?, ?, ?, ?)";


                                String hashed_password;
                                try {
                                    hashed_password = PasswordStorage.createHash(createUser.getPassword());
                                } catch (PasswordStorage.CannotPerformOperationException ex) {
                                    oos.writeObject(new ResponseCreateUser(false, -1));
                                    break;
                                }

                                PreparedStatement preparedStatement = conn.prepareStatement(insert_user_sql);
                                preparedStatement.setBoolean(1, createUser.isLearner());
                                preparedStatement.setInt(2, createUser.getAge());
                                preparedStatement.setString(3, createUser.getName());
                                preparedStatement.setString(4, createUser.getOrginCountry());
                                preparedStatement.setFloat(5, createUser.getLongitude());
                                preparedStatement.setFloat(6, createUser.getLatitude());


                                /*
                                arrays not supported by datagbase, java.sql.SQLFeatureNotSupportedException: Not yet supported
                                final String[] interestsData = createUser.getInterests().toArray(new String[createUser.getInterests().size()]);
                                final java.sql.Array sqlArray = conn.createArrayOf("String", interestsData);

                                store as string for now
                               */

                                String interestsString = "";
                                for(String interest: createUser.getInterests()) {
                                    interestsString = interestsString + "," + interest;
                                }

                                preparedStatement.setString(7, interestsString);
                                preparedStatement.setString(8, createUser.getUsername());
                                preparedStatement.setString(9, hashed_password);

                                boolean success = 1 == preparedStatement.executeUpdate();

                                res = conn.createStatement().executeQuery("SELECT @@IDENTITY");
                                res.next();
                                if(success)
                                    oos.writeObject(new ResponseCreateUser(success, res.getInt(1)));
                                else
                                {
                                    ResponseCreateUser resp = new ResponseCreateUser(success, -1);
                                    resp.setError("could not insert user, duplicate username?");
                                    oos.writeObject(resp);
                                }
                            }
                            break;

                            case UpdateLocation: {
                                RequestUpdateLocation updateLocation = (RequestUpdateLocation) msg;
                                String sql = "update user_table set longitude=?, latitude=? where user_id = ?";
                                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setInt(1,updateLocation.longitude);
                                preparedStatement.setInt(2,updateLocation.latitude);
                                preparedStatement.setInt(3,updateLocation.user_id);
                                boolean success = preparedStatement.executeUpdate() == 1;
                                oos.writeObject(new ResponseUpdateLocation(success));
                            } break;
                            case VerifyPassword:
                            {
                                RequestVerifyPassword verifyPassword = (RequestVerifyPassword) msg;
                                //String sql = "Select hashed_password, user_id from user_table where user_name = ?";
                                String sql = "Select hashed_password, username from user_table";
                                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setString(1, verifyPassword.username);

                                try
                                {
                                    ResultSet resultSet = preparedStatement.executeQuery();
                                    if(resultSet.next())
                                    {
                                        boolean success = PasswordStorage.verifyPassword(verifyPassword.password,resultSet.getString(1));
                                        System.out.println("pass hash: " + resultSet.getString(1) + "provided:" + verifyPassword.password+success);
                                        oos.writeObject(new ResponsVerifyPassword(success,resultSet.getInt(2)));
                                    }
                                    else
                                    {
                                        oos.writeObject(new ResponsVerifyPassword(false,-1));
                                    }
                                }
                                catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex)
                                {
                                    oos.writeObject(new Response(ex.toString()));
                                }
                            }break;

                            case SendMessage: {
                                RequestSendMessage sendMessage = (RequestSendMessage) msg;
                                String sql = "INSERT INTO message_table (from_id, to_id, message_body) VALUES (?,?,?)";
                                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setString(1, sendMessage.to_id);
                                preparedStatement.setString(2, sendMessage.from_id);
                                preparedStatement.setString(3, sendMessage.message);
                                oos.writeObject(new Response(MessageType.SendMessage,1==preparedStatement.executeUpdate()));
                            }
                            break;
                            case TerminateConnection:
                                running = false;
                                break;

                            case GetMessages: {
                                /*
                                TODO finish and test this shit,
                                how do the messages, both to and from requester, end up in the correct order?
                                 */
                                String to_id;
                                String from_id;
                                ArrayList<String> messages;
                                Statement statement = null;
                                String query = ("select to_id, from_id, message_body from message_table");

                                try {
                                    statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery(query);
                                    while (resultSet.next()) {

                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                                //oos.writeObject(new ResponseGetMessages(success, to_id, from_id, messages));
                            } break;
                            default: {
                                System.err.println("msg type is not handled " + msg.type);
                            }
                        }
                    }
                    catch (SQLException ex)
                    {
                        oos.writeObject(new Response(ex.toString()));
                        ex.printStackTrace();
                    }
                }

                socket.close();
                System.out.println("connection terminated");
            }
            catch (ClassNotFoundException |SQLException ex)
            {
                if (oos!=null)
                    new Response(ex.toString());
                socket.close();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ConnectionHandler(Socket socket)
    {
        this.socket = socket;
    }
}
