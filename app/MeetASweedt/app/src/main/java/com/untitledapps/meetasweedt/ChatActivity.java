package com.untitledapps.meetasweedt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    private ArrayList<Message> chatMessages = new ArrayList<>();
    private ArrayAdapter<String> chatAdapter;
    private View activityView;

    //Temp Person
    Person p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityView = getLayoutInflater().inflate(R.layout.activity_chat, null);
        Person p1 = new Person(false, 19, "Arvid Hast", "sweden", 58, 13, 1500,  new ArrayList<String>(Arrays.asList("computers", "staring into the abyss", "code", "stocks", "not chilling")));
        //chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        setContentView(activityView);
    }

    public void sendMessage(View v) {
        //Name of sender etc., or maybe something more fancy like color coding and centering
        //right, left for sent and received messages?
        TextView textView = (TextView) activityView.findViewById(R.id.chatText);
        //TextView textView = (TextView) getParent().findViewById(R.id.chatText);
        System.out.println("Message: " + textView.getText().toString());
        Message message = new Message(textView.getText().toString(), p1);
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
