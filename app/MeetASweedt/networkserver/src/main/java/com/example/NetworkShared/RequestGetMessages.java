package com.example.NetworkShared;

/**
 * Created by Niklas on 2016-10-05.
 */

public class RequestGetMessages extends Request<ResponseGetMessages> {
    private int to_id;
    private int from_id;

    public RequestGetMessages(int to_id, int from_id) {
        super(MessageType.GetMessages);
        this.to_id = to_id;
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public int getFrom_id() {
        return from_id;
    }
}
