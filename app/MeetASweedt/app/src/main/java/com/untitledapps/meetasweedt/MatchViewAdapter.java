package com.untitledapps.meetasweedt;

/**
 * Created by fredr on 2016-09-24.
 */

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
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
        this.matchingPerson = matchingPerson;
        //Sorting
        Collections.sort(result, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return (int) ((o2.getMatchScore(matchingPerson)-o1.getMatchScore(matchingPerson))*100);
            }
        });

        context = matchingActivity;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View temp = inflater.inflate(R.layout.activity_matching_card, null);
        rowView.add(temp);
        holder.name = (TextView) rowView.get(position).findViewById(R.id.name);
        holder.matchProcent = (TextView) rowView.get(position).findViewById(R.id.matchProcent);
        holder.distance = (TextView) rowView.get(position).findViewById(R.id.distance);
        if(result.get(position).getName().contains(" ")){
            String[] s = result.get(position).getName().split(" ");
            holder.name.setText(s[0]);
        } else {
            holder.name.setText(result.get(position).getName());
        }
        holder.matchProcent.setText(Integer.toString((int)(result.get(position).getMatchScore(matchingPerson)*100)));
        holder.distance.setText(Float.toString(Math.round((10f * result.get(position).getDistanceTo(matchingPerson) / 1000)) / 10f) + " Km");
        ((ProgressBar) rowView.get(position).findViewById(R.id.progressBarMatch)).setProgress(((int)(result.get(position).getMatchScore(matchingPerson)*100)));
        System.out.println(result.get(position).getName() + ": " + (int)(result.get(position).getMatchScore(matchingPerson)*100));
        System.out.println(result.get(position).getName() + ": " + ((ProgressBar) rowView.get(position).findViewById(R.id.progressBarMatch)).getProgress());


        rowView.get(position).findViewById(R.id.cardMain).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Add show profile
                RelativeLayout temp = (RelativeLayout)inflater.inflate(R.layout.activity_matching_profile, null);
                ((Activity) context).setContentView(temp);
                ((TextView) temp.findViewById(R.id.matchingProcent)).setText(Integer.toString((int)(result.get(position).getMatchScore(matchingPerson)*100)));
                ((TextView) temp.findViewById(R.id.distance)).setText(Float.toString(Math.round((10f * result.get(position).getDistanceTo(matchingPerson) / 1000)) / 10f) + " Km");
                ((TextView) temp.findViewById(R.id.name)).setText(result.get(position).getName());

                if(context instanceof MatchingActivity){
                    ((MatchingActivity)context).setIsUserAtList(false);
                }

                //Purple progressbar
                ProgressBar progressBar = (ProgressBar) temp.findViewById(R.id.progressBarUnder);
                ObjectAnimator animation = ObjectAnimator.ofInt (progressBar, "progress", (int)(result.get(position).getMatchScore(matchingPerson)*100)<20 ? (int)(result.get(position).getMatchScore(matchingPerson)*1000) + 650 : 1000 - (int)(result.get(position).getMatchScore(matchingPerson)*1000), 1000); // see this max value coming back here, we animale towards that value
                animation.setDuration (1000); //in milliseconds
                animation.setInterpolator (new DecelerateInterpolator());
                animation.start ();
                //Grey progressbar
                progressBar = (ProgressBar) temp.findViewById(R.id.progressBarMatch);
                animation = ObjectAnimator.ofInt (progressBar, "progress", 0, (int)(result.get(position).getMatchScore(matchingPerson)*1000)); // see this max value coming back here, we animale towards that value
                animation.setDuration (1000); //in milliseconds
                animation.setInterpolator (new DecelerateInterpolator());
                animation.start ();

                // Get the ViewPager and set it's PagerAdapter so that it can display items
                ViewPager viewPager = (ViewPager) temp.findViewById(R.id.viewpager);
                viewPager.setAdapter(new FragmentMatchingProfileAdapter(((MatchingActivity) context).getSupportFragmentManager(),
                        MatchingActivity.context, result.get(position), matchingPerson));

                // Give the TabLayout the ViewPager
                TabLayout tabLayout = (TabLayout) temp.findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(viewPager);
                
                /*ListView listView = (ListView) temp.findViewById(R.id.intrests);
                listView.setAdapter(new InterestListAdapter(context, result.get(position).getInterests(), matchingPerson.getInterests()));*/
                temp.findViewById(R.id.matchButton).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        MatchingActivity.requestAddMatch(context, result.get(position).getUser_id(), matchingPerson.getUser_id());
                        Toast.makeText(v.getContext(), "You just matched with this person!", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });

        return rowView.get(position);
    }
}
