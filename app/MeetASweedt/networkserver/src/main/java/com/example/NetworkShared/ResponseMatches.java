package com.example.NetworkShared;

public class ResponseMatches extends Response
{
    public ResponseMatches (boolean success)
    {
        super(MessageType.GetMatches,success);
    }

    private String stringOfMatches = "";

    public String getStringOfMatches() {
        return stringOfMatches;
    }

    public void setStringOfMatches(String stringOfMatches) {
        this.stringOfMatches = stringOfMatches;
    }
}