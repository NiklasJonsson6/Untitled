package com.example.NetworkShared;

/**
 * Created by Arvid on 2016-10-08.
 */

public class RequestAllPeopleExcludeMatches extends Request<ResponseAllPeople> {
    public RequestAllPeopleExcludeMatches(String username){
        super(MessageType.GetAllPeopleExcludeMatches);
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
