package com.company;

/**
 * @author Daniel.
 */
public enum MessageType
{
    register_log(1),
    send_server(2),
    err(4);

    final int id;
    MessageType(int id)
    {
        this.id = id;
    }

    public static MessageType from_id(int id)
    {
        for(MessageType type: values()) if(type.id == id) return type;
        return err;
    }
}
