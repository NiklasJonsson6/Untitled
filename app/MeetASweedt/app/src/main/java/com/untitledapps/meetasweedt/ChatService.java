package com.untitledapps.meetasweedt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.NetworkShared.Request;
import com.example.NetworkShared.RequestConnectionTermination;
import com.example.NetworkShared.RequestGetMessages;
import com.example.NetworkShared.Response;
import com.untitledapps.Client.RequestBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatService extends Service {
    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private LocalBroadcastManager broadcaster;
    private RequestBuilder builder;
    private RequestGetMessages req;

    private String to_id, from_id;
    private int index;

    final static String CHAT_ACTION = "CHAT_ACTION";

    @Override
    public void onCreate() {
        context = this;
        isRunning = false;
        backgroundThread = new Thread(updateChat);
        System.out.println("ChatService created");

        broadcaster = LocalBroadcastManager.getInstance(this);

        /*builder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (req.was_successfull()) {
                    for (String[] messageContainer: req.getResponse().getMessageContainer()) {
                        sendResult(messageContainer);
                    }
                }
            }
        });*/
    }

    private Runnable updateChat = new Runnable() {
        public void run() {
            while(isRunning) {
                try {
                    //TODO get messages
                    //builder.addRequest(req);
                    //builder.execute();

                    //doInBackground
                    try {
                        Socket socket = new Socket("46.239.104.53", 4000);
                        ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        req.sendRequest(ois,oos);

                        new RequestConnectionTermination().sendRequest(ois,oos);
                        socket.close();
                    } catch (IOException ex) {
                        if(req.majorError()) {
                            req.setRespone(new Response("clientside: "+ex.toString()));
                        }
                        ex.printStackTrace();
                    }

                    //PostExecute
                    if (req.was_successfull()) {
                        System.out.println("successfull");

                        int i = 0;
                        for (String[] messageContainer: req.getResponse().getMessageContainer()) {
                            if (i < index) {
                                i++;
                            } else {
                                sendResult(messageContainer);
                                index++;
                            }
                        }

                    } else {
                        System.out.println("get message request failed");
                    }

                    System.out.println("poll");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void sendResult(String[] messageContainer) {
        Intent intent = new Intent(CHAT_ACTION);
        intent.putExtra("MessageContainer", messageContainer);
        broadcaster.sendBroadcast(intent);
        System.out.println("Broadcaster sent: " + messageContainer[1]);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        to_id = intent.getStringExtra("to_id");
        from_id = intent.getStringExtra("from_id");
        index = intent.getIntExtra("index", 0);

        req = new RequestGetMessages(to_id, from_id, index);

        if(!isRunning) {
            isRunning = true;
            backgroundThread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
