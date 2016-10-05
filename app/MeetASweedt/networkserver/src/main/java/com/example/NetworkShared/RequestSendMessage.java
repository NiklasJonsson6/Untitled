package com.example.NetworkShared;

/**
 * Created by Daniel on 26/09/2016.
 */
public class RequestSendMessage extends Request<Response>{
    public int from_id,to_id;
    public String message;
    public RequestSendMessage(int from_id, int to_id, String message)
    {
        super(MessageType.SendMessage);
        this.from_id = from_id;
        this.to_id = to_id;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestSendMessage{" +
                "message='" + message + '\'' +
                ", to_id=" + to_id +
                ", from_id=" + from_id +
                '}';
    }
}
