package com.example.NetworkShared;


public abstract class SecureRequest<T extends Response> extends Request<T>
{
    public SecureRequest(MessageType type, int user_id, String password)
    {
        super(type);
        this.user_id = user_id;
        this.password = password;
    }
    public int user_id;
    public String password;
}



