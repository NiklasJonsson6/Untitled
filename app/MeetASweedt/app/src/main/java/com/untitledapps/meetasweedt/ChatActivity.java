package com.untitledapps.meetasweedt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> chatMessages = new ArrayList<>();
    private ArrayAdapter<String> chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
    }

    public void sendMessage(View v) {
        //Name of sender etc., or maybe something more fancy like color coding and centering
        //right, left for sent and received messages?

        TextView textView = (TextView) getParent().findViewById(R.id.chatText);
        String message = textView.getText().toString();
        textView.setText("");

        //sets adapter if not already set
        setChatAdapter();

        chatMessages.add(message);
        chatAdapter.notifyDataSetChanged();
    }

    public void setChatAdapter() {
        ListView listView = (ListView) getParent().findViewById(R.id.chatListView);
        if(listView.getAdapter() == null) {
            listView.setAdapter(chatAdapter);
        }
    }
}
