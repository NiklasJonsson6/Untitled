package com.example.NetworkShared;

//CREATE USER
public class RequestCreateUser extends Request<ResponseCreateUser>
{
    public RequestCreateUser(String userName, String name, String lastName, String password, boolean isSwedish, String bio, float latitude, float longitude)
    {
        super(MessageType.CreateUser);
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.isSwedish = isSwedish;
        this.bio = bio;
        this.latitude= latitude;
        this.longitude = longitude;

    }

    @Override
    public String toString() {
        return "RequestCreateUser{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", isSwedish=" + isSwedish +
                ", bio='" + bio + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String userName;
    public String name;
    public String lastName;
    public String password;
    public boolean isSwedish;
    public String bio;
    public float longitude;
    public float latitude;
}
