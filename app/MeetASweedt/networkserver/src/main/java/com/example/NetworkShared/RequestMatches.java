package com.example.NetworkShared;

public class RequestMatches extends SecureRequest<ResponseMatches>
{
    public RequestMatches (int user_id, String password)
    {
        super(MessageType.Matches,user_id,password);
    }
}
