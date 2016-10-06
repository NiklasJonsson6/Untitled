package com.untitledapps.meetasweedt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.NetworkShared.RequestGetMessages;
import com.untitledapps.Client.RequestBuilder;

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

        req = new RequestGetMessages(to_id, from_id, index);
        builder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (req.was_successfull()) {
                    for (String[] messageContainer: req.getResponse().getMessageContainer()) {
                        sendResult(messageContainer);
                    }
                }
            }
        });
    }

    private Runnable updateChat = new Runnable() {
        public void run() {
            while(isRunning) {
                try {
                    //TODO get messages
                    builder.addRequest(req);
                    builder.execute();

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        to_id = intent.getStringExtra("to_id");
        from_id = intent.getStringExtra("from_id");
        index = intent.getIntExtra("index", 0);

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
