package com.untitledapps.meetasweedt;

/**
 * Created by fredr on 2016-09-24.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import java.util.zip.Inflater;

public class MatchViewAdapter extends BaseAdapter {

    ArrayList<Person> result;
    Context context;
    ArrayList<Integer> matchProcent;
    Person matchingPerson;
    private static LayoutInflater inflater = null;
    ArrayList<Boolean> isClicked;
    ArrayList<View> rowView;
    ArrayList<Integer> selectedView;
    Activity activity;

    public MatchViewAdapter(Activity activity, Context matchingActivity, ArrayList<Person> personList, Person matchingPerson) {
        // TODO Auto-generated constructor stub
        result = personList;
        context = matchingActivity;
        this.activity = activity;
        this.matchingPerson = matchingPerson;
        rowView = new ArrayList<View>();
        selectedView = new ArrayList<Integer>();
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
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

    public ArrayList<Integer> getCardIndexList() {
        return selectedView;
    }

    public class Holder {
        TextView name;
        TextView matchProcent;
        TextView distance;
        //RelativeLayout rv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View temp = inflater.inflate(R.layout.activity_matching_card, null);
        rowView.add(temp);
        holder.name = (TextView) rowView.get(position).findViewById(R.id.name);
        holder.matchProcent = (TextView) rowView.get(position).findViewById(R.id.matchProcent);
        holder.distance = (TextView) rowView.get(position).findViewById(R.id.distance);
        holder.name.setText(result.get(position).getName());
        holder.matchProcent.setText(Integer.toString((int)(result.get(position).getMatchScore(matchingPerson)*100)) + "%");
        holder.distance.setText(Float.toString(result.get(position).getDistanceTo(matchingPerson)) + "Km");



        rowView.get(position).findViewById(R.id.cardMain).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Add show profile
                MatchingActivity.viewMatchingProfile(matchingPerson,result.get(position));
            }

        });

        return rowView.get(position);
    }

}
