package com.untitledapps.meetasweedt;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestListAdapter extends BaseAdapter {
    ArrayList<String> interest;
    ArrayList<String> matchingInterest;
    private LayoutInflater inflater = null;

    public InterestListAdapter(LayoutInflater inflater, ArrayList<String> interest, ArrayList<String> matchingInterest) {
        // TODO Auto-generated constructor stub
        this.interest = interest;
        this.matchingInterest = matchingInterest;
        this.inflater = inflater;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return interest.size();
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
        View rowView;
        rowView = inflater.inflate(R.layout.activity_interests_list, null);
        tv = (TextView) rowView.findViewById(R.id.interest);
        tv.setText(interest.get(position));
        tv = (TextView) rowView.findViewById(R.id.colourHax);
        if (matchingInterest.contains(interest.get(position))) {
            tv.setBackgroundResource(R.drawable.green_rounded);
        } else {
            tv.setBackgroundResource(R.drawable.red_rounded);
        }
        return rowView;

    }
}