package com.untitledapps.meetasweedt;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

<<<<<<< HEAD
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
=======
import com.example.NetworkShared.RequestSendMessage;
import com.untitledapps.Client.RequestBuilder;
>>>>>>> 2bf686e2a6d42287c1596b1344757cc6dafca5ee

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;



public class ChatActivity extends AppCompatActivity {

    private ArrayList<Message> chatMessages = new ArrayList<>();
    private ArrayAdapter<String> chatAdapter;
    private View activityView;
    private TextView textView;

    //TODO should be self, person to send message to
    Person p1, p2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityView = getLayoutInflater().inflate(R.layout.activity_chat, null);
        p1 = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));
        p2 = new Person(false, 20, "Fredrik Mast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));
        //chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        setContentView(activityView);

        /*
        For receiving chat messages:
         */
                /*
                startService(new Intent(this, ChatService.class));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        */
        startService(new Intent(this, ChatService.class));

        textView = (TextView) activityView.findViewById(R.id.chatText);

    }

    @Override
    protected void onPause() {
        //stops polling for messages when chat view is closed
        stopService(new Intent(this, ChatService.class));
        super.onPause();
    }

    public void sendMessage(View v) {
        System.out.println("Message: " + textView.getText().toString());
        Calendar c = GregorianCalendar.getInstance();
        Message message;
<<<<<<< HEAD
        if (r.nextBoolean()) {
=======

        /*
        Test code to send message from either person
         */
        Random r = new Random();
        if(r.nextBoolean()){
>>>>>>> 2bf686e2a6d42287c1596b1344757cc6dafca5ee
            message = new Message(textView.getText().toString(), p1, c);
        } else {
            message = new Message(textView.getText().toString(), p2, c);
        }
        //String message = textView.getText().toString();

        //reqSendMessage(message); (does nothing until there is a receiver who can get the message)
        updateChatView(message);
    }

    public void updateChatView(Message message) {
        //sets adapter if not already set
        setChatAdapter();
        chatMessages.add(message);

        textView.setText("");
    }

    private void reqSendMessage(Message message) {
        final RequestSendMessage req = new RequestSendMessage(Integer.parseInt(p1.getName()), Integer.parseInt(p2.getName()), message.getMessage());

        RequestBuilder requestBuilder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (req.was_successfull()) {
                    System.out.println("Message sent");
                } else {
                    System.out.println("Send message failed");
                }
            }
        });

        requestBuilder.addRequest(req);
        requestBuilder.execute();
    }

    public void setChatAdapter() {
        //ListView listView = (ListView) getParent().findViewById(R.id.chatListView);
        ListView listView = (ListView) activityView.findViewById(R.id.chatListView);
        //if(listView.getAdapter() == null) {
        listView.setAdapter(new ChatListAdapter(this, chatMessages, p1));
        //}
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Chat Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
