package com.untitledapps.meetasweedt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ChatService extends Service {
    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public void onCreate() {
        context = this;
        isRunning = false;
        backgroundThread = new Thread(updateChat);
        System.out.println("ChatService created");
    }

    private Runnable updateChat = new Runnable() {
        public void run() {
            while(isRunning) {
                try {
                    //TODO get messages
                    System.out.println("poll");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
