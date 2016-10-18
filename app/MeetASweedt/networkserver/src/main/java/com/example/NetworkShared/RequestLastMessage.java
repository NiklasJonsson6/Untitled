package com.example.NetworkShared;

/**
 * Created by Niklas on 2016-10-05.
 */

public class RequestLastMessage extends Request<ResponseGetLastMessage> {
    private String to_id;
    private String from_id;

    public RequestLastMessage(String to_id, String from_id) {
        super(MessageType.GetLastMessages);
        this.to_id = to_id;
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    @Override
    public String toString() {
        return "RequestGetMessages{" +
                "to_id=" + to_id +
                ", from_id=" + from_id +
                '}';
    }
}
