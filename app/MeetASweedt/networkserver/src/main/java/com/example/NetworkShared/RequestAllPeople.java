package com.example.NetworkShared;

import java.util.ArrayList;

/**
 * Created by Arvid on 2016-10-08.
 */

public class RequestAllPeople extends Request<ResponseAllPeople> {
    public RequestAllPeople(String username){
        super(MessageType.GetAllPeople);
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
