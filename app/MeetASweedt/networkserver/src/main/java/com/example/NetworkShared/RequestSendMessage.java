package com.example.NetworkShared;

import java.util.ResourceBundle;

/**
 * Created by Daniel on 26/09/2016.
 */
public class RequestSendMessage extends Request<Response>{
    public String to_id, from_id;
    public String message;
    public RequestSendMessage(String to_id, String from_id, String message)
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
