package com.example.NetworkShared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//CREATE USER
public class RequestCreateUser extends Request<ResponseCreateUser>
{
    public RequestCreateUser(String userName, String firstName, String lastName, String password, boolean isSwedish, String bio)
    {
        super(MessageType.CreateUser);
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isSwedish = isSwedish;
        this.bio = bio;

    }


    @Override
    public String toString()
    {
        return "RequestCreateUser{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", isSwedish=" + isSwedish +
                ", bio='" + bio + '\'' +
                '}';
    }

    public String userName;
    public String firstName;
    public String lastName;
    public String password;
    public boolean isSwedish;
    public String bio;
}
