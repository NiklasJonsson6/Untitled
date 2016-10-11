package com.example.NetworkShared;

/**
 * Created by Niklas on 2016-10-05.
 */

public class RequestGetMessages extends Request<ResponseGetMessages> {
    private int index;
    private String to_id;
    private String from_id;

    /**
     * @param index The number of messages already in view
     * @param to_id Should be person you open chat with
     * @param from_id Should be self
     */
    public RequestGetMessages(String to_id, String from_id, int index) {
        super(MessageType.GetMessages);
        this.to_id = to_id;
        this.from_id = from_id;
        this.index = index;
    }

    public String getTo_id() {
        return to_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public int getIndex() {
        return index;
    }

    public void incIndex() {
        index++;
    }

    @Override
    public String toString() {
        return "RequestGetMessages{" +
                "to_id=" + to_id +
                ", from_id=" + from_id +
                ", index=" + index +
                '}';
    }
}
