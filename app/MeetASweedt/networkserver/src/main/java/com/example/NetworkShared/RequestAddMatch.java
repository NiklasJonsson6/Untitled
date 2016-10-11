package com.example.NetworkShared;

/**
 * Created by Arvid on 2016-10-11.
 */

public class RequestAddMatch extends Request<ResponseAddMatch> {
    public RequestAddMatch(int matchId, int userId) {
        super(MessageType.AddMatch);
        this.matchId = matchId;
        this.userID = userId;
    }

    private int matchId;
    private int userID;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}