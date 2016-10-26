package com.untitledapps.meetasweedt;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static com.untitledapps.meetasweedt.R.id.emojiIcon;

/**
 * Created by Fredrik on 2016-10-12.
 */

public class MatchChatAdapter extends BaseAdapter {
    ArrayList<MatchesBlock> matchesBlock;
    Context context;
    private LayoutInflater inflater = null;
    int token = 0;
    public MatchChatAdapter(Context mainActivity, ArrayList<MatchesBlock> matchesBlock) {
        // TODO Auto-generated constructor stub
        this.matchesBlock = matchesBlock;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return matchesBlock.size();
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


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView tv;
        ImageView img;
        View rowView;
        rowView = inflater.inflate(R.layout.matches, null);
        tv = (TextView) rowView.findViewById(R.id.recentMessageText);
        System.out.println(rowView.toString());
        tv.setText(matchesBlock.get(position).getmMessage());
        tv = (TextView) rowView.findViewById(R.id.timeText);
        tv.setText(matchesBlock.get(position).getmTime());
        tv = (TextView) rowView.findViewById(R.id.nameText);
        tv.setText(matchesBlock.get(position).getmName());
        //img = (ImageView) rowView.findViewById(emojiIcon);
        if(token != 1) {
        //    holder.img.setImageResource(matchesBlock.get(position).getMresID()); //setting the image
            token++;
        }
        System.out.println("Made it");
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: start a single chat for each person
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("p1", ((MeetASweedt) context.getApplicationContext()).getLoggedInPerson());
                intent.putExtra("p2", matchesBlock.get(position).getPerson());
                context.startActivity(intent);
            }
        });
        return rowView;
    }

}
