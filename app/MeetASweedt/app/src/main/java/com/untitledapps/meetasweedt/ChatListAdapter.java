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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

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
        TextView name;
        RelativeLayout rl;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        if(messageList.get(position).getSender() == loggedIn) {
            rowView = inflater.inflate(R.layout.activity_chat_this_message, null);

            holder.tv = (TextView) rowView.findViewById(R.id.chat_this_time);
            System.out.println(TimeZone.getDefault().getRawOffset());
            holder.tv.setText(messageList.get(position).getCalendar().get(Calendar.HOUR_OF_DAY) + ":" +((messageList.get(position).getCalendar().get(Calendar.MINUTE)<10) ? "0" + messageList.get(position).getCalendar().get(Calendar.MINUTE) : messageList.get(position).getCalendar().get(Calendar.MINUTE)));

            holder.tv = (TextView) rowView.findViewById(R.id.chat_this_date);
            holder.tv.setText(messageList.get(position).getCalendar().get(Calendar.DAY_OF_MONTH) + "/" + messageList.get(position).getCalendar().get(Calendar.MONTH) + "-" + messageList.get(position).getCalendar().get(Calendar.YEAR));


            holder.tv = (TextView) rowView.findViewById(R.id.chat_this);
            holder.rl = (RelativeLayout) rowView.findViewById(R.id.chat_this_background);
            if((position < getCount()-1 && position != 0) && (messageList.get(position).getSender() == messageList.get(position-1).getSender()
                    && messageList.get(position).getSender() == messageList.get(position + 1).getSender())){
                holder.rl.setBackgroundResource(R.drawable.chat_background_this_both);
            } else if((position != 0) && (messageList.get(position).getSender() == messageList.get(position-1).getSender())){
                holder.rl.setBackgroundResource(R.drawable.chat_background_this_top);
            } else {
                holder.name = (TextView) rowView.findViewById(R.id.chat_this_name);
                holder.name.setVisibility(View.VISIBLE);
                holder.name.setText(messageList.get(position).getSender().getName());
                holder.rl.setBackgroundResource(R.drawable.chat_background_this);
            }
        } else {
            rowView = inflater.inflate(R.layout.activity_chat_other_message, null);
            holder.tv = (TextView) rowView.findViewById(R.id.chat_other);
        }

        if(messageList.get(position).getMessage() != null && messageList.get(position).getMessage().toString() != "") {
            System.out.println("IN IF: " + messageList.get(position).getMessage());
            holder.tv.setText(messageList.get(position).getMessage());
        } else {
            System.out.printf("IN ELSE");
            holder.tv.setText(" ");
        }
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.findViewById(R.id.chat_this_message_time).getVisibility() == View.VISIBLE) {
                    v.findViewById(R.id.chat_this_message_time).setVisibility(View.INVISIBLE);
                } else {
                    v.findViewById(R.id.chat_this_message_time).setVisibility(View.VISIBLE);
                }
            }
        });
        return rowView;
    }

}
