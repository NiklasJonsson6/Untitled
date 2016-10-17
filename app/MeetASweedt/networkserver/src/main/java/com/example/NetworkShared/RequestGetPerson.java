package com.example.NetworkShared;

/**
 * Created by Niklas on 2016-10-17.
 */

public class RequestGetPerson extends Request<ResponseGetPerson> {
    private String username;

    public RequestGetPerson(String username) {
        super(MessageType.GetPerson);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
