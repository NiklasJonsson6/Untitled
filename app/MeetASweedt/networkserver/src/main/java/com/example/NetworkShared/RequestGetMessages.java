package com.example.NetworkShared;

/**
 * Created by Niklas on 2016-10-05.
 */

public class RequestGetMessages extends Request<ResponseGetMessages> {
    private String to_id;
    private String from_id;

    public RequestGetMessages(String to_id, String from_id) {
        super(MessageType.GetMessages);
        this.to_id = to_id;
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public String getFrom_id() {
        return from_id;
    }
}
