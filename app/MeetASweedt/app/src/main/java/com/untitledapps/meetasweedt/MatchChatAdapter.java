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
    private static LayoutInflater inflater = null;
    int resID;
    
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
        createRandomProfilePic();
        holder.img = (ImageView) rowView.findViewById(emojiIcon);
        holder.img.setImageResource(resID); //setting the image

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

    public void createRandomProfilePic(){

            final TypedArray imgs = context.getResources().obtainTypedArray(R.array.arrayyylmao);
            final Random rand = new Random();
            final int rndInt = rand.nextInt(imgs.length());
            resID = imgs.getResourceId(rndInt, 0);
            System.out.println("tits" + resID);


    }
}
