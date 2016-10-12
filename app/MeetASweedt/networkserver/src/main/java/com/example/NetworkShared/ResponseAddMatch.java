package com.example.NetworkShared;

/**
 * Created by Arvid on 2016-10-11.
 */

public class ResponseAddMatch extends Response {
    public ResponseAddMatch(boolean success) {
        super(MessageType.AddMatch, success);
    }
}
