package com.untitledapps.meetasweedt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
                    if(context instanceof  ProfileActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;
                case "Chat":
                    holder.navImage.setImageResource(R.mipmap.ic_chat);
                    if(context instanceof  MatchesActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;
                case "Match":
                    holder.navImage.setImageResource(R.mipmap.ic_match);
                    if(context instanceof  MatchingActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;
                case "Map":
                    holder.navImage.setImageResource(R.mipmap.ic_map);
                    if(context instanceof  FikaMapActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;

            }


            rowView.get(position).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Add show profile
                    Intent intent = null;
                    Boolean change = false;
                    switch (result.get(position)) {
                        case "My Profile":
                            if(!(context instanceof  ProfileActivity)) {
                                intent = new Intent(context, ProfileActivity.class);
                                change = true;
                            }
                            break;
                        case "Chat":
                            if(!(context instanceof  MatchesActivity)) {
                                intent = new Intent(context, MatchesActivity.class);
                                change = true;
                            }
                            break;
                        case "Match":
                            if(!(context instanceof  MatchingActivity)) {
                                intent = new Intent(context, MatchingActivity.class);
                                change = true;
                            }
                            break;
                        case "Map":
                            if(!(context instanceof  FikaMapActivity)) {
                                intent = new Intent(context, FikaMapActivity.class);
                                change = true;
                            }
                            break;
                    }
                    if(change) {
                        context.startActivity(intent);
                    }
                }

            });

            return rowView.get(position);
        }
    }
