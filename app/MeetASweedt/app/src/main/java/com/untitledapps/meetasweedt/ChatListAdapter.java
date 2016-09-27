package com.untitledapps.meetasweedt;

/**
 * Created by fredr on 2016-09-26.
 */

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class ChatListAdapter extends BaseAdapter {
    ArrayList<Message> messageList;
    Person loggedIn;
    Context context;
    private static LayoutInflater inflater = null;

    public ChatListAdapter(Context mainActivity, ArrayList<Message> messageList, Person loggedIn) {
        // TODO Auto-generated constructor stub
        this.messageList = messageList;
        this.loggedIn = loggedIn;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        if(messageList.get(position).getSender() == loggedIn) {
            rowView = inflater.inflate(R.layout.activity_chat_this_message, null);
            holder.tv = (TextView) rowView.findViewById(R.id.chat_this);
        } else {
            rowView = inflater.inflate(R.layout.activity_chat_other_message, null);
            holder.tv = (TextView) rowView.findViewById(R.id.chat_other);
        }


        holder.tv.setText(messageList.get(position).getMessage());
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        return rowView;
    }

}
