package com.untitledapps.meetasweedt;

/**
 * Created by fredr on 2016-09-24.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MatchViewAdapter extends BaseAdapter {

    ArrayList<Person> result;
    Context context;
    ArrayList<Integer> matchProcent;
    Person matchingPerson;
    private static LayoutInflater inflater = null;
    ArrayList<Boolean> isClicked;
    ArrayList<View> rowView;
    ArrayList<Integer> selectedView;

    public MatchViewAdapter(Context matchingActivity, ArrayList<Person> personList, final Person matchingPerson) {
        // TODO Auto-generated constructor stub
        result = personList;
        //Sorting
        Collections.sort(result, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return (int) ((o2.getMatchScore(matchingPerson)-o1.getMatchScore(matchingPerson))*100);
            }
        });

        context = matchingActivity;
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
        holder.distance.setText(Float.toString(Math.round((10f * result.get(position).getDistanceTo(matchingPerson) / 1000)) / 10f) + " Km");


        rowView.get(position).findViewById(R.id.cardMain).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Add show profile
                RelativeLayout temp = (RelativeLayout)inflater.inflate(R.layout.activity_matching_profile, null);
                ((Activity) context).setContentView(temp);
                ((TextView) temp.getChildAt(4)).setText(Integer.toString((int)(result.get(position).getMatchScore(matchingPerson)*100)) + "%");
                ((TextView) temp.getChildAt(1)).setText(Float.toString(Math.round((10f * result.get(position).getDistanceTo(matchingPerson) / 1000)) / 10f) + " Km");
                ((TextView) temp.getChildAt(3)).setText(result.get(position).getName());
                ListView listView = (ListView) temp.getChildAt(2);
                listView.setAdapter(new InterestListAdapter(context, result.get(position).getInterests(), matchingPerson.getInterests()));
            }

        });

        return rowView.get(position);
    }
}
