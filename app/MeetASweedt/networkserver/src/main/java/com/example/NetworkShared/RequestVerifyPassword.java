package com.example.NetworkShared;

/**
 * Created by Daniel on 28/09/2016.
 */
public class RequestVerifyPassword extends Request<ResponsVerifyPassword>{

    public String password;
    public String user_name;
    public RequestVerifyPassword(String user_name,String password) {
        super(MessageType.VerifyPassword);
        this.password = password;
        this.user_name= user_name;
    }
}
