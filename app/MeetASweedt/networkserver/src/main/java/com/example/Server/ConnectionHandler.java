package com.example.Server;


import com.example.NetworkShared.MessageType;
import com.example.NetworkShared.Request;
import com.example.NetworkShared.RequestAddMatch;
import com.example.NetworkShared.RequestAllPeople;
import com.example.NetworkShared.RequestAllPeopleExcludeMatches;
import com.example.NetworkShared.RequestCreateUser;
import com.example.NetworkShared.RequestGetMessages;
import com.example.NetworkShared.RequestGetPerson;
import com.example.NetworkShared.RequestLastMessage;
import com.example.NetworkShared.RequestMatches;
import com.example.NetworkShared.RequestSendMessage;
import com.example.NetworkShared.RequestUpdateLocation;
import com.example.NetworkShared.RequestVerifyPassword;
import com.example.NetworkShared.ResponsVerifyPassword;
import com.example.NetworkShared.Response;
import com.example.NetworkShared.ResponseAddMatch;
import com.example.NetworkShared.ResponseAllPeople;
import com.example.NetworkShared.ResponseCreateUser;
import com.example.NetworkShared.ResponseGetLastMessage;
import com.example.NetworkShared.ResponseGetMessages;
import com.example.NetworkShared.ResponseGetPerson;
import com.example.NetworkShared.ResponseMatches;
import com.example.NetworkShared.ResponseUpdateLocation;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.sun.corba.se.spi.orbutil.fsm.State;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Daniel.
 */
public class ConnectionHandler implements Runnable
{

    private boolean addMatch(Connection conn, int userId, int matchId) throws SQLException
    {

        String oldMatches = "";
        String query = ("Select matches from user_table where user_id = ?");
        PreparedStatement preparedStatementMatch = conn.prepareStatement(query);
        preparedStatementMatch.setInt(1, userId);

        ResultSet resultSetMatch = preparedStatementMatch.executeQuery();

        System.out.println("request add match user id: " + userId);

        if(resultSetMatch.next()) {
            oldMatches = resultSetMatch.getString(1);
        }
        else return false;


        boolean already_mached = oldMatches.contains(Integer.toString(matchId)); //should really not be displayed...
        if(already_mached) {
            preparedStatementMatch.close();
            return true;
        }
            else{
            String sql = "update user_table set matches=? where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(2, userId);

            if(oldMatches.equals("")){
                preparedStatement.setString(1, oldMatches + matchId); //add old string
            } else {
                preparedStatement.setString(1, oldMatches + "," + matchId); //add old string
            }

            preparedStatementMatch.close();
            return preparedStatement.executeUpdate() == 1;
        }
    }

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

                                boolean isLearner = false;
                                String orginCountry = createUser.getOrginCountry().toLowerCase();

                                if(orginCountry.equals("sweden") || orginCountry.equals("sverige")) {
                                    isLearner = false;
                                } else {
                                    isLearner = true;
                                }

                                preparedStatement.setBoolean(1,isLearner);
                                preparedStatement.setInt(2, createUser.getAge());
                                preparedStatement.setString(3, createUser.getName());
                                preparedStatement.setString(4, createUser.getOrginCountry());
                                preparedStatement.setFloat(5, createUser.getLongitude());
                                preparedStatement.setFloat(6, createUser.getLatitude());

                                /*
                                //arrays not supported by database, java.sql.SQLFeatureNotSupportedException: Not yet supported
                                final String[] interestsData = createUser.getInterests().toArray(new String[createUser.getInterests().size()]);
                                final java.sql.Array sqlArray = conn.createArrayOf("String", interestsData);

                                store as string for now
                               */

                                preparedStatement.setString(7, createUser.getInterestsString());
                                preparedStatement.setString(8, createUser.getUsername());
                                preparedStatement.setString(9, hashed_password);

                                boolean success = 1 == preparedStatement.executeUpdate();


                                Statement tempStatement = conn.createStatement();
                                res = tempStatement.executeQuery("SELECT @@IDENTITY");
                                res.next();
                                if(success)
                                    oos.writeObject(new ResponseCreateUser(success, res.getInt(1)));
                                else
                                {
                                    ResponseCreateUser resp = new ResponseCreateUser(success, -1);
                                    resp.setError("could not insert user, duplicate username?");
                                    oos.writeObject(resp);
                                }
                                res.close();
                                tempStatement.close();
                                preparedStatement.close();
                            }
                            break;

                            case UpdateLocation: {

                                RequestUpdateLocation updateLocation = (RequestUpdateLocation) msg;
                                String sql = "update user_table set longitude=?, latitude=? where user_id = ?";
                                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setFloat(1, updateLocation.getLongitude());
                                preparedStatement.setFloat(2, updateLocation.getLatitude());
                                preparedStatement.setInt(3, updateLocation.getUser_id());
                                boolean success = preparedStatement.executeUpdate() == 1;
                                oos.writeObject(new ResponseUpdateLocation(success));

                                System.out.println("updating user " + updateLocation.getUser_id() +" location, longitude: " + updateLocation.getLongitude() + ", latitude: " + updateLocation.getLatitude());
                                preparedStatement.close();
                            } break;
                            case AddMatch: {
                                RequestAddMatch requestAddMatch = (RequestAddMatch) msg;
                                //symmetry.
                                boolean s1 = addMatch(conn,requestAddMatch.getUserID(),requestAddMatch.getMatchId());
                                boolean s2 = addMatch(conn,requestAddMatch.getMatchId(),requestAddMatch.getUserID());
                                oos.writeObject(new ResponseAddMatch(s1&&s2));

                            } break;
                            case VerifyPassword:
                            {
                                RequestVerifyPassword verifyPassword = (RequestVerifyPassword) msg;
                                String sql = "Select hashed_password, user_id from user_table where username = ?";
                                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setString(1, verifyPassword.username);

                                try {
                                    ResultSet resultSet = preparedStatement.executeQuery();
                                    if(resultSet.next())
                                    {
                                        boolean success = PasswordStorage.verifyPassword( verifyPassword.password,resultSet.getString(1));
                                        System.out.println("pass hash: " + resultSet.getString(1) + "\nprovided:" + verifyPassword.password+success);
                                        oos.writeObject(new ResponsVerifyPassword(success,resultSet.getInt(2)));
                                    }
                                    else
                                    {
                                        oos.writeObject(new ResponsVerifyPassword(false,-1));
                                    }
                                } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
                                    oos.writeObject(new Response(ex.toString()));
                                }
                                preparedStatement.close();
                            }break;

                            case SendMessage: {
                                RequestSendMessage sendMessage = (RequestSendMessage) msg;
                                String sql = "INSERT INTO message_table (from_id, to_id, message_body) VALUES (?,?,?)";
                                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setString(1, sendMessage.to_id);
                                preparedStatement.setString(2, sendMessage.from_id);
                                preparedStatement.setString(3, sendMessage.message);
                                oos.writeObject(new Response(MessageType.SendMessage,1==preparedStatement.executeUpdate()));
                                preparedStatement.close();
                            }
                            break;
                            case TerminateConnection:
                                running = false;
                                break;
                            case GetLastMessages:
                            {
                                RequestLastMessage lastMessage = (RequestLastMessage) msg;
                                PreparedStatement statement;

                                String sql =
                                        "SELECT `message_body`,`date`,`from_id`\n" +
                                        "FROM `message_table`\n" +
                                        "WHERE to_id in (?, ?) \n" +
                                        "AND from_id in (?, ?)\n" +
                                        "ORDER BY `message_id` DESC \n" +
                                        "LIMIT 1;\n";

                                statement = conn.prepareStatement(sql);
                                statement.setString(1,lastMessage.getFrom_id());
                                statement.setString(3,lastMessage.getFrom_id());
                                statement.setString(2,lastMessage.getTo_id());
                                statement.setString(4,lastMessage.getTo_id());

                                ResultSet resultSet = statement.executeQuery();
                                if(resultSet.next())
                                {
                                    ResponseGetLastMessage response = new ResponseGetLastMessage(true, new ResponseGetMessages.Message(
                                            resultSet.getTimestamp("date"),
                                            resultSet.getString("message_body"),
                                            resultSet.getString("from_id")
                                    ));
                                    oos.writeObject(response);
                                }
                                else
                                {
                                    oos.writeObject(new Response("fail"));
                                }


                                statement.close();
                            }break;
                            case GetMessages: {
                                System.out.println("getmessagecase");
                                RequestGetMessages requestMessage = (RequestGetMessages) msg;

                                String to_id = requestMessage.getTo_id();
                                String from_id = requestMessage.getFrom_id();

                                ArrayList<ResponseGetMessages.Message> messageContainer = new ArrayList<>();

                                Statement statement = null;
                                String query = ("Select to_id, from_id, message_body, date from message_table");

                                try {
                                    statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery(query);

                                    int i = 0;
                                    while (resultSet.next()) {
                                        if (resultSet.getString("to_id").equals(to_id) && resultSet.getString("from_id").equals(from_id)) {
                                            //if you're the sender
                                            messageContainer.add(
                                                    new ResponseGetMessages.Message(
                                                            resultSet.getTimestamp("date"),
                                                            resultSet.getString("message_body"),
                                                            from_id
                                                    ));

                                        } else if (resultSet.getString("to_id").equals(from_id) && resultSet.getString("from_id").equals(to_id)) {
                                            //if you're the receiver
                                            messageContainer.add(
                                                    new ResponseGetMessages.Message(
                                                            resultSet.getTimestamp("date"),
                                                            resultSet.getString("message_body"),
                                                            to_id
                                            ));
                                        }
                                    }

                                    ResponseGetMessages response = new ResponseGetMessages(true, messageContainer);

                                    requestMessage.setRespone(response);

                                    oos.writeObject(response);
                                    statement.close();
                                } catch (Exception ex) {
                                    //TODO probably some exception handling I guess
                                    ex.printStackTrace();
                                }
                            } break;
                            case GetAllPeopleExcludeMatches:
                            {
                                RequestAllPeopleExcludeMatches requestAllPeople = (RequestAllPeopleExcludeMatches) msg;

                                String query = "select isLearner, age, name, orginCountry, longitude, latitude, interests, username, user_id " +
                                        "from user_table where not " +
                                        "find_in_set((select user_id from user_table where username = ?),matches)" +
                                        "limit 500";
                                PreparedStatement statement = conn.prepareStatement(query);
                                statement.setString(1,requestAllPeople.getUsername());
                                ResultSet resultSet = statement.executeQuery();

                                ResponseAllPeople response = new ResponseAllPeople(true);
                                int i = 0;
                                int FETCH_LIMIT = 500;
                                while (resultSet.next() && i < FETCH_LIMIT) {

                                    response.getUsername().add(resultSet.getString("username"));

                                    if(!response.getUsername().get(i).equals(requestAllPeople.getUsername())) {
                                        response.getIsLearner().add(resultSet.getBoolean("isLearner"));
                                        response.getAge().add(resultSet.getInt("age"));
                                        response.getName().add(resultSet.getString("name"));
                                        response.getOrginCountry().add(resultSet.getString("orginCountry"));
                                        response.getLongitude().add(resultSet.getFloat("longitude"));
                                        response.getLatitude().add(resultSet.getFloat("latitude"));
                                        response.getInterestsString().add(resultSet.getString("interests"));
                                        response.getUser_id().add(resultSet.getInt("user_id"));
                                        i++;

                                    } else {
                                        response.getUsername().remove(resultSet.getString("username"));
                                    }
                                }

                                requestAllPeople.setRespone(response);
                                oos.writeObject(response);

                                statement.close();
                            }break;
                            case GetAllPeople: {
                                System.out.println("is in get all people case");

                                RequestAllPeople requestAllPeople = (RequestAllPeople) msg;

                                Statement statement;
                                String query = ("select isLearner, age, name, orginCountry, longitude, latitude, interests, username, user_id from user_table");

                                try {
                                    statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery(query);

                                    ResponseAllPeople response = new ResponseAllPeople(true);


                                    int i = 0;

                                    final int FETCH_LIMIT = 500;

                                    while (resultSet.next() && i < FETCH_LIMIT) {

                                        response.getUsername().add(resultSet.getString("username"));

                                        if(!response.getUsername().get(i).equals(requestAllPeople.getUsername())) {
                                            response.getIsLearner().add(resultSet.getBoolean("isLearner"));
                                            response.getAge().add(resultSet.getInt("age"));
                                            response.getName().add(resultSet.getString("name"));
                                            response.getOrginCountry().add(resultSet.getString("orginCountry"));
                                            response.getLongitude().add(resultSet.getFloat("longitude"));
                                            response.getLatitude().add(resultSet.getFloat("latitude"));
                                            response.getInterestsString().add(resultSet.getString("interests"));
                                            response.getUser_id().add(resultSet.getInt("user_id"));
                                            i++;

                                        } else {
                                            response.getUsername().remove(resultSet.getString("username"));
                                        }
                                    }

                                    requestAllPeople.setRespone(response);
                                    oos.writeObject(response);

                                    System.out.println("got all users from db");
                                    statement.close();
                                } catch (Exception ex) {
                                    System.out.println("error in getting all people from db");
                                    //TODO probably some exception handling I guess
                                    ex.printStackTrace();
                                }
                            } break;

                            case GetPerson: {
                                System.out.println("in get person case");
                                RequestGetPerson request = (RequestGetPerson) msg;

                                Statement statement = null;
                                String query = "select isLearner, age, name, orginCountry, longitude, latitude, interests, username, user_id from user_table";

                                try {
                                    statement = conn.createStatement();
                                    ResultSet r = statement.executeQuery(query);
                                    ResponseGetPerson response = null;

                                    while (r.next()) {
                                        if (r.getString("username").equals(request.getUsername())) {
                                            response = new ResponseGetPerson(true,
                                                    r.getBoolean("isLearner"),
                                                    r.getString("username"),
                                                    r.getInt("age"),
                                                    r.getString("name"),
                                                    r.getString("orginCountry"),
                                                    r.getFloat("longitude"),
                                                    r.getFloat("latitude"),
                                                    r.getString("interests"),
                                                    r.getInt("user_id"));

                                            request.setRespone(response);
                                        }
                                    }
                                    if (response == null) {
                                        request.setRespone(new Response(MessageType.GetPerson, true));
                                    }

                                    oos.writeObject(response);
                                    statement.close();
                                } catch (Exception ex) {
                                    System.out.println("error in getting person from db");
                                    ex.printStackTrace();
                                }
                            } break;

                            case GetMatches: {
                                System.out.println("in get matches case");
                                RequestMatches requestMatches = (RequestMatches) msg;

                                //TODO secure? req pass?

                                Statement statement = null;


                                //String sql = "Select hashed_password, user_id from user_table where username = ?";


                                //String sql = "Select hashed_password, user_id from user_table where user_id = ?";
                                //PreparedStatement preparedStatement = conn.prepareStatement(query);
                                //preparedStatement.setInt(1, requestMatches.getUser_id());


                                try {

                                    String query = "Select matches from user_table where user_id = ?";
                                    System.out.println("userid: " + requestMatches.getUser_id());


                                    //String sql = "update user_table set longitude=?, latitude=? where user_id = ?";
                                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                                    preparedStatement.setInt(1,requestMatches.getUser_id());



                                    //boolean success = preparedStatement.executeUpdate() == 1;
                                    //oos.writeObject(new ResponseUpdateLocation(success));



                                    //statement = conn.createStatement();
                                    System.out.println("b4 q");
                                    //res = conn.createStatement().executeQuery("SELECT @@IDENTITY");

                                    ResultSet resultSet = preparedStatement.executeQuery();
                                    System.out.println("after q");

                                    ResponseMatches response = new ResponseMatches(true);
                                    //ResponseMatches response = new ResponseMatches(1==preparedStatement.executeUpdate());
                                    resultSet.next();

                                    response.setStringOfMatches(resultSet.getString("matches"));

                                    //res = conn.createStatement().executeQuery("SELECT @@IDENTITY");
                                    //res.next();


                                    requestMatches.setRespone(response);
                                    oos.writeObject(response);
                                    preparedStatement.close();
                                    //oos.writeObject(new ResponseAllPeople(true, requestAllPeople.getAllPersonStrings()));

                                    System.out.println("got all matchesssssssssssssssssssssss from db");
                                } catch (Exception ex) {
                                    System.out.println("error in getting matches from db");
                                    //TODO probably some exception handling I guess
                                    ex.printStackTrace();
                                }


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
            catch (SQLException|RuntimeException|ClassNotFoundException ex)
            {
                if (oos!=null)
                    oos.writeObject(new Response(ex.toString()));
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
