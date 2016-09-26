package com.untitledapps.Client;

import android.os.AsyncTask;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.NetworkShared.*;
/**
 * Created by Daniel on 26/09/2016.
 */
public class RequestBuilder extends AsyncTask<Void,Void,Void>{
    public List<Request> requests = new ArrayList<>(10);

    @Override
    protected Void doInBackground(Void... params) {
        List<Response> ret=null;
        try
        {
            Socket socket = new Socket("46.239.104.53", 4000);
            ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            for(int i = 0; i<requests.size();i++)
            {
                requests.get(i).sendRequest(ois,oos);
            }
            new RequestConnectionTermination().sendRequest(ois,oos);
            socket.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public void addRequest(Request request)
    {
        requests.add(request);
    }
}
