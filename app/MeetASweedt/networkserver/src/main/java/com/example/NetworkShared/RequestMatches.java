package com.example.NetworkShared;

public class RequestMatches extends SecureRequest<ResponseMatches>
{
    public RequestMatches (int user_id, String password)
    {
        super(MessageType.GetMatches,user_id,password);
    }
}
