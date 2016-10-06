package com.example.NetworkShared;

import java.util.ArrayList;

/**
 * Created by Niklas on 2016-10-05.
 */

public class ResponseGetMessages extends Response {
    private String to_id;
    private String from_id;
    private ArrayList<String> messages;

    public ResponseGetMessages(boolean success, String to_id, String from_id, ArrayList<String> messages) {
        super(MessageType.GetMessages, success);
        this.to_id = to_id;
        this.from_id = from_id;
        this.messages = messages;
    }

    public String getTo_id() {
        return to_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
