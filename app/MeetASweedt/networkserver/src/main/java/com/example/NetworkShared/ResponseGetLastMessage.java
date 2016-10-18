package com.example.NetworkShared;

/**
 * Created by Niklas on 2016-10-05.
 */

public class ResponseGetLastMessage extends Response {

    private ResponseGetMessages.Message message;

    public ResponseGetLastMessage(boolean success, ResponseGetMessages.Message message) {
        super(MessageType.GetLastMessages, success);
        this.message= message;
    }
    public ResponseGetMessages.Message getMessage() {
        return message;
    }
}
