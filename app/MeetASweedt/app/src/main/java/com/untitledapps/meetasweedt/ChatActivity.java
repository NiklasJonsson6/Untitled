package com.untitledapps.meetasweedt;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.NetworkShared.RequestSendMessage;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import static com.untitledapps.meetasweedt.ChatService.CHAT_ACTION;


public class ChatActivity extends AppCompatActivity {

    private ArrayList<Message> chatMessages = new ArrayList<>();
    private ArrayAdapter<String> chatAdapter;
    private View activityView;
    private TextView textView;

    private BroadcastReceiver receiver;

    Person p1, p2;
    String from_id, to_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityView = getLayoutInflater().inflate(R.layout.activity_chat, null);

        Intent intent = getIntent();
        //default case if persons are given from intent
        if(intent.getParcelableExtra("p1") != null) {
            p1 = intent.getParcelableExtra("p1");
            p2 = intent.getParcelableExtra("p2");
        } else {
            //if no intent is given, example Persons
            p1 = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")), "qwe", 12);
            p2 = new Person(false, 20, "Fredrik Mast", "sweden", 58, 13, new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")), "NiklasJonsson6", 13);
        }
        from_id = p1.getUsername();
        to_id = p2.getUsername();

        //chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        setContentView(activityView);

        /*
        For receiving chat messages:
         */
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String[] messageContainer = intent.getStringArrayExtra("MessageContainer");
                addMessageFromContainer(messageContainer);
            }
        };

        Intent serviceIntent = new Intent(this, ChatService.class);
        serviceIntent.putExtra("from_id", from_id);
        serviceIntent.putExtra("to_id", to_id);
        serviceIntent.putExtra("index", chatMessages.size()); //if there already are messages in the view, will probably be 0
        startService(serviceIntent);

        textView = (TextView) activityView.findViewById(R.id.chatText);
        //Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(p2.getName());
    }

    @Override
    protected void onPause() {
        //stops polling for messages when chat view is closed
        stopService(new Intent(this, ChatService.class));
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(CHAT_ACTION));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    public void addMessageFromContainer(String[] messageContainer) {
        Message message;
        Calendar c = GregorianCalendar.getInstance();
        if (messageContainer[0].equals(p1.getUsername())) {
            message = new Message(messageContainer[1], p1, c);
            updateChatView(message);
        } else if (messageContainer[0].equals(p2.getUsername())) {
            message = new Message(messageContainer[1], p2, c);
            updateChatView(message);
        }
    }

    public void sendMessage(View v) {
        System.out.println("Message: " + textView.getText().toString());
        Calendar c = GregorianCalendar.getInstance();
        Message message;

        /*
        Test code to send message from either person
         */
        Random r = new Random();
        if(true && r.nextBoolean()){
            message = new Message(textView.getText().toString(), p1, c);
        } else {
            message = new Message(textView.getText().toString(), p2, c);
        }
        //String message = textView.getText().toString();

        reqSendMessage(message);
        updateChatView(message);
    }

    public void updateChatView(Message message) {
        //sets adapter if not already set
        setChatAdapter();
        chatMessages.add(message);

        textView.setText("");
    }

    private void reqSendMessage(Message message) {
        final RequestSendMessage req = new RequestSendMessage(p1.getUsername(), p2.getUsername(), message.getMessage());

        RequestBuilder requestBuilder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (req.was_successfull()) {
                    System.out.println("Message sent");
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
        listView.setSelection(chatMessages.size());
        //}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
