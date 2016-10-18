package com.example.NetworkShared;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Niklas on 2016-10-05.
 */

public class ResponseGetMessages extends Response {

    public static class Message implements Serializable
    {
        public Message(Timestamp time_stamp, String body,String from_id) {
            this.time_stamp = time_stamp;
            this.body = body;
            this.from_id = from_id;
        }
        public String from_id;
        public Timestamp time_stamp;
        public String body;
    }
    private ArrayList<Message> messageContainer;

    public ResponseGetMessages(boolean success, ArrayList<Message> messageContainer) {
        super(MessageType.GetMessages, success);
        this.messageContainer = messageContainer;
    }

    /**
     * List of size [2] arrays with names and messages
     * @return messageContainer[0] contains senders name, messageContainer[1] contains message
     */
    public ArrayList<Message> getMessageContainer() {
        return messageContainer;
    }
}
