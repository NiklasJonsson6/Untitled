package com.untitledapps.Client;

import android.app.ProgressDialog;
import android.content.Context;
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

    public interface Action
    {
        void PostExecute();
    }
    private Action action;
    Context context;
    public RequestBuilder(Context context, Action action)
    {
        this.action = action;
        this.context = context;
        if(context!=null)
            dialog = new ProgressDialog(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try
        {
            Socket socket = new Socket("46.239.104.53", 4000);
            ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            for(Request request : requests)
                request.sendRequest(ois,oos);

            new RequestConnectionTermination().sendRequest(ois,oos);
            socket.close();
        }
        catch (IOException ex)
        {
            for(Request request : requests)
            {
                if(request.majorError())
                {
                    request.setRespone(new Response("clientside: "+ex.toString()));
                }
            }
            ex.printStackTrace();
        }
        return null;
    }
    private ProgressDialog dialog;

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(dialog!=null&&dialog.isShowing())dialog.dismiss();
        if(action!=null)
            action.PostExecute();
    }

    @Override
    protected void onPreExecute() {
        if(dialog != null)dialog.show();
    }
    public void addRequest(Request request)
    {
        requests.add(request);
    }
}
