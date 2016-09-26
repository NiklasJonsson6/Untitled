package com.example.NetworkShared;

public class ResponseMatches extends Response
{
    public ResponseMatches (boolean success)
    {
        super(MessageType.Matches,success);
    }
}
