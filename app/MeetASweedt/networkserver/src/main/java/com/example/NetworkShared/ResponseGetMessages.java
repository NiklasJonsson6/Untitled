package com.example.NetworkShared;

import java.util.ArrayList;

/**
 * Created by Niklas on 2016-10-05.
 */

public class ResponseGetMessages extends Response {

    private ArrayList<String[]> messageContainer;

    public ResponseGetMessages(boolean success, ArrayList<String[]> messageContainer) {
        super(MessageType.GetMessages, success);
        this.messageContainer = messageContainer;
        System.out.println("messagecontainer: length in response: " + messageContainer.size());
    }

    /**
     * List of size [2] arrays with names and messages
     * @return messageContainer[0] contains senders name, messageContainer[1] contains message
     */
    public ArrayList<String[]> getMessageContainer() {
        return messageContainer;
    }
}
