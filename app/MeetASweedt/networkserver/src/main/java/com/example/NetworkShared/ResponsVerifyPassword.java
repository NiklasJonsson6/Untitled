package com.example.NetworkShared;

/**
 * Created by Daniel on 28/09/2016.
 */
public class ResponsVerifyPassword extends Response{
    public int user_id;
    public ResponsVerifyPassword(boolean success,int user_id) {
        super(MessageType.VerifyPassword, success);
        this.user_id = user_id;
    }
}
