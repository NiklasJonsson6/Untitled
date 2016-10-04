package com.untitledapps.meetasweedt;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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

    //Temp Person, should be currently logged in person?
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
                startService(new Intent(this, ChatService.class));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        System.out.println(new MicrosoftTranslation().TranslateString("Swedish", "English", "Doesn't matter"));
    }

    @Override
    protected void onPause() {
        //stops polling for messages when chat view is closed
        stopService(new Intent(this, ChatService.class));
        super.onPause();
    }

    public void sendMessage(View v) {
        //Name of sender etc., or maybe something more fancy like color coding and centering
        //right, left for sent and received messages?
        TextView textView = (TextView) activityView.findViewById(R.id.chatText);
        //TextView textView = (TextView) getParent().findViewById(R.id.chatText);
        System.out.println("Message: " + textView.getText().toString());
        Calendar c = GregorianCalendar.getInstance();
        Random r = new Random();
        Message message;
        if (r.nextBoolean()) {
            message = new Message(textView.getText().toString(), p1, c);
        } else {
            message = new Message(textView.getText().toString(), p2, c);
        }
        //String message = textView.getText().toString();

        //sets adapter if not already set
        setChatAdapter();
        chatMessages.add(message);
        //chatMessages.add(message);
        //chatAdapter.notifyDataSetChanged();
        textView.setText("");
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
