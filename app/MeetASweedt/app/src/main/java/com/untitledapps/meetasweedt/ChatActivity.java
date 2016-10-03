package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityView = getLayoutInflater().inflate(R.layout.activity_chat, null);
        p1 = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));
        p2 = new Person(false, 20, "Fredrik Mast", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));
        //chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        setContentView(activityView);

        /*
        For receiving chat messages:
         */
        startService(new Intent(this, ChatService.class));
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
        if(r.nextBoolean()){
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
}
