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

public class DrawerAdapter extends BaseAdapter {
        ArrayList<String> result;
        Context context;
        private LayoutInflater inflater = null;
        ArrayList<View> rowView;

        public DrawerAdapter(Context context, ArrayList<String> activityStrings) {
            // TODO Auto-generated constructor stub
            result = activityStrings;
            //Sorting
            this.context = context;
            rowView = new ArrayList<View>();
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

        public static class Holder {
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
                case "Min Profil":
                    holder.navImage.setImageResource(R.mipmap.ic_myprofile);
                    if(context instanceof  ProfileActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;
                case "Meddelanden":
                    holder.navImage.setImageResource(R.mipmap.ic_chat);
                    if(context instanceof  MatchesActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;
                case "Hitta vänner":
                    holder.navImage.setImageResource(R.mipmap.ic_match);
                    if(context instanceof  MatchingActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;
                case "Karta":
                    holder.navImage.setImageResource(R.mipmap.ic_map);
                    if(context instanceof  FikaMapActivity) {
                        rowView.get(position).findViewById(R.id.drawer_item_background).setBackgroundColor(Color.parseColor("#DCDCDC"));
                    }
                    break;
                default:
                    break;

            }


            rowView.get(position).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Add show profile
                    Intent intent = null;
                    Boolean change = false;
                    switch (result.get(position)) {
                        case "Min Profil":
                            if(!(context instanceof  ProfileActivity)) {
                                intent = new Intent(context, ProfileActivity.class);
                                change = true;
                            }
                            break;
                        case "Meddelanden":
                            if(!(context instanceof  MatchesActivity)) {
                                intent = new Intent(context, MatchesActivity.class);
                                change = true;
                            }
                            break;
                        case "Hitta vänner":
                            if(!(context instanceof  MatchingActivity)) {
                                intent = new Intent(context, MatchingActivity.class);
                                change = true;
                            }
                            break;
                        case "Karta":
                            if(!(context instanceof  FikaMapActivity)) {
                                intent = new Intent(context, FikaMapActivity.class);
                                change = true;
                            }
                            break;
                        default:
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
