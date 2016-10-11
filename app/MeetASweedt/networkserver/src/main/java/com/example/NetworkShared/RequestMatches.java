package com.example.NetworkShared;

public class RequestMatches extends Request<ResponseMatches>
{
    public RequestMatches (int user_id)
    {
        super(MessageType.GetMatches);
        this.user_id = user_id;
    }

    private final int user_id;
    private String matchesString = "";

    public int getUser_id() {
        return user_id;
    }
}
