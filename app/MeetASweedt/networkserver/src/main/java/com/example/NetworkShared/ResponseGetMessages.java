package com.example.NetworkShared;

import java.util.ArrayList;

/**
 * Created by Niklas on 2016-10-05.
 */

public class ResponseGetMessages extends Response {
    private int to_id;
    private int from_id;
    private ArrayList<String> messages;

    public ResponseGetMessages(boolean success, int to_id, int from_id, ArrayList<String> messages) {
        super(MessageType.GetMessages, success);
        this.to_id = to_id;
        this.from_id = from_id;
        this.messages = messages;
    }

    public int getTo_id() {
        return to_id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
