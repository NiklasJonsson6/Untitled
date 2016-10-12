package com.untitledapps.meetasweedt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by fredr on 2016-10-12.
 */

public class DrawerAdapter extends BaseAdapter {
        ArrayList<String> result;
        Context context;
        ArrayList<Integer> matchProcent;
        Person matchingPerson;
        private static LayoutInflater inflater = null;
        ArrayList<Boolean> isClicked;
        ArrayList<View> rowView;
        ArrayList<Integer> selectedView;

        public DrawerAdapter(Context context, ArrayList<String> activityStrings) {
            // TODO Auto-generated constructor stub
            result = activityStrings;
            //Sorting
            this.context = context;
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
            ImageView navImage;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder = new Holder();
            View temp = inflater.inflate(R.layout.drawer_item, null);
            rowView.add(temp);
            holder.name = (TextView) rowView.get(position).findViewById(R.id.navName);
            holder.name.setText(result.get(position));
            holder.navImage = (ImageView) rowView.get(position).findViewById(R.id.navImage);
            switch (result.get(position)) {
                case "My Profile":
                    holder.navImage.setImageResource(R.mipmap.ic_myprofile);
                    break;
                case "Chat":
                    holder.navImage.setImageResource(R.mipmap.ic_chat);
                    break;
                case "Match":
                    holder.navImage.setImageResource(R.mipmap.ic_match);
                    break;
                case "Map":
                    holder.navImage.setImageResource(R.mipmap.ic_map);
                    break;

            }


            rowView.get(position).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Add show profile
                    Intent intent = null;
                    switch (result.get(position)) {
                        case "My Profile":
                            intent = new Intent(context, ProfileActivity.class);
                            break;
                        case "Chat":
                            intent = new Intent(context, ChatActivity.class);
                            break;
                        case "Match":
                            intent = new Intent(context, MatchingActivity.class);
                            break;
                        case "Map":
                            intent = new Intent(context, FikaMapActivity.class);
                            break;
                    }
                    context.startActivity(intent);
                }

            });

            return rowView.get(position);
        }
    }
