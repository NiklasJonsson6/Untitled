package com.untitledapps.meetasweedt;

/**
 * Created by fredr on 2016-09-26.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestListAdapter extends BaseAdapter {
    ArrayList<String> interest;
    ArrayList<String> matchingInterest;
    Context context;
    private static LayoutInflater inflater = null;

    public InterestListAdapter(LayoutInflater inflater, ArrayList<String> interest, ArrayList<String> matchingInterest) {
        // TODO Auto-generated constructor stub
        this.interest = interest;
        this.matchingInterest = matchingInterest;
        //context = mainActivity;
        /*this.inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
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

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_interests_list, null);
        holder.tv = (TextView) rowView.findViewById(R.id.interest);
        holder.tv.setText(interest.get(position));
        holder.tv = (TextView) rowView.findViewById(R.id.colourHax);
        if (matchingInterest.contains(interest.get(position))) {
            holder.tv.setBackgroundResource(R.drawable.green_rounded);
        } else {
            holder.tv.setBackgroundResource(R.drawable.red_rounded);
            //holder.tv.setBackgroundColor(RED);
        }
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        return rowView;

    }
}