package com.untitledapps.meetasweedt;

/**
 * Created by fredr on 2016-09-27.
 */

public class Message {
    String message;
    Person sender;

    public Message(String message, Person sender) {
        this.message = message;
        this.sender = sender;
    }

    public Person getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
