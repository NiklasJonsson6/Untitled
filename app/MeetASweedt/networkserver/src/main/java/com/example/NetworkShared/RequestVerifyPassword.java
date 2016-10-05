package com.example.NetworkShared;

/**
 * Created by Daniel on 28/09/2016.
 */
public class RequestVerifyPassword extends Request<ResponsVerifyPassword>{

    public String password;
    public String username;
    public RequestVerifyPassword(String username,String password) {
        super(MessageType.VerifyPassword);
        this.password = password;
        this.username= username;
    }
}
