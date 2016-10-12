package com.untitledapps.meetasweedt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Fredrik on 2016-10-12.
 */

public class MatchChatAdapter extends BaseAdapter {
    ArrayList<MatchesBlock> matchesBlock;
    Context context;
    private static LayoutInflater inflater = null;

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

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.matches, null);
        holder.tv = (TextView) rowView.findViewById(R.id.recentMessageText);
        System.out.println(rowView.toString());
        holder.tv.setText(matchesBlock.get(position).getmMessage());
        holder.tv = (TextView) rowView.findViewById(R.id.timeText);
        holder.tv.setText(matchesBlock.get(position).getmTime());
        holder.tv = (TextView) rowView.findViewById(R.id.nameText);
        holder.tv.setText(matchesBlock.get(position).getmName());
        System.out.println("Made it");
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        return rowView;

    }
}
