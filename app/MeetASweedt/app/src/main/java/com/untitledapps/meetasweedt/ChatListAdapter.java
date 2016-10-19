package com.untitledapps.meetasweedt;

/**
 * Created by fredr on 2016-09-26.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ChatListAdapter extends BaseAdapter {
    ArrayList<Message> messageList;
    ArrayList<String> translatedText;
    ArrayList<View> translateView;
    int translationIndex;
    Spinner spinner;
    Person loggedIn;
    Context context;
    View lastClickedView;
    TextView textView;
    private static LayoutInflater inflater = null;

    public ChatListAdapter(Context mainActivity, ArrayList<Message> messageList, Person loggedIn) {
        // TODO Auto-generated constructor stub
        this.messageList = messageList;
        this.loggedIn = loggedIn;
        translationIndex = 0;
        translatedText = new ArrayList<String>();
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return messageList.size();
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
        TextView name;
        RelativeLayout rl;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        if(messageList.get(position).getSender() == loggedIn) {
            rowView = inflater.inflate(R.layout.activity_chat_this_message, null);

            holder.tv = (TextView) rowView.findViewById(R.id.chat_this_time);
            System.out.println(TimeZone.getDefault().getRawOffset());
            holder.tv.setText(messageList.get(position).getCalendar().get(Calendar.HOUR_OF_DAY) + ":" +((messageList.get(position).getCalendar().get(Calendar.MINUTE)<10) ? "0" + messageList.get(position).getCalendar().get(Calendar.MINUTE) : messageList.get(position).getCalendar().get(Calendar.MINUTE)));

            holder.tv = (TextView) rowView.findViewById(R.id.chat_this_date);
            holder.tv.setText(messageList.get(position).getCalendar().get(Calendar.DAY_OF_MONTH) + "/" + (messageList.get(position).getCalendar().get(Calendar.MONTH)+1) + "-" + messageList.get(position).getCalendar().get(Calendar.YEAR));


            holder.tv = (TextView) rowView.findViewById(R.id.chat_this);
            holder.rl = (RelativeLayout) rowView.findViewById(R.id.chat_this_background);
            if((position < getCount()-1 && position != 0) && (messageList.get(position).getSender() == messageList.get(position-1).getSender()
                    && messageList.get(position).getSender() == messageList.get(position + 1).getSender())){
                holder.rl.setBackgroundResource(R.drawable.chat_background_this_both);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rowView.findViewById(R.id.chat_this_main).getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                ((RelativeLayout)rowView.findViewById(R.id.chat_this_main)).setLayoutParams(layoutParams);
            } else if((position != 0) && (messageList.get(position).getSender() == messageList.get(position-1).getSender())){
                holder.rl.setBackgroundResource(R.drawable.chat_background_this_top);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rowView.findViewById(R.id.chat_this_main).getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                ((RelativeLayout)rowView.findViewById(R.id.chat_this_main)).setLayoutParams(layoutParams);
            } else {
                holder.name = (TextView) rowView.findViewById(R.id.chat_this_name);
                holder.name.setVisibility(View.VISIBLE);
                holder.name.setText(messageList.get(position).getSender().getName());
                holder.rl.setBackgroundResource(R.drawable.chat_background_this);
            }
            //Spinner content
            Spinner spinner = (Spinner) rowView.findViewById(R.id.chat_this_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.language_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } else {
            rowView = inflater.inflate(R.layout.activity_chat_other_message, null);

            holder.tv = (TextView) rowView.findViewById(R.id.chat_other_time);
            System.out.println(TimeZone.getDefault().getRawOffset());
            holder.tv.setText(messageList.get(position).getCalendar().get(Calendar.HOUR_OF_DAY) + ":" +((messageList.get(position).getCalendar().get(Calendar.MINUTE)<10) ? "0" + messageList.get(position).getCalendar().get(Calendar.MINUTE) : messageList.get(position).getCalendar().get(Calendar.MINUTE)));

            holder.tv = (TextView) rowView.findViewById(R.id.chat_other_date);
            holder.tv.setText(messageList.get(position).getCalendar().get(Calendar.DAY_OF_MONTH) + "/" + messageList.get(position).getCalendar().get(Calendar.MONTH) + "-" + messageList.get(position).getCalendar().get(Calendar.YEAR));


            holder.tv = (TextView) rowView.findViewById(R.id.chat_other);
            holder.rl = (RelativeLayout) rowView.findViewById(R.id.chat_other_background);
            if((position < getCount()-1 && position != 0) && (messageList.get(position).getSender() == messageList.get(position-1).getSender()
                    && messageList.get(position).getSender() == messageList.get(position + 1).getSender())){
                holder.rl.setBackgroundResource(R.drawable.chat_background_other_both);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rowView.findViewById(R.id.chat_other_main).getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                ((RelativeLayout)rowView.findViewById(R.id.chat_other_main)).setLayoutParams(layoutParams);
            } else if((position != 0) && (messageList.get(position).getSender() == messageList.get(position-1).getSender())){
                holder.rl.setBackgroundResource(R.drawable.chat_background_other_top);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rowView.findViewById(R.id.chat_other_main).getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                ((RelativeLayout)rowView.findViewById(R.id.chat_other_main)).setLayoutParams(layoutParams);
            } else {
                holder.name = (TextView) rowView.findViewById(R.id.chat_other_name);
                holder.name.setVisibility(View.VISIBLE);
                holder.name.setText(messageList.get(position).getSender().getName());
                holder.rl.setBackgroundResource(R.drawable.chat_background_other);
            }
            //Spinner content
            Spinner spinner = (Spinner) rowView.findViewById(R.id.chat_other_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.language_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
        if(messageList.get(position).getMessage() != null && messageList.get(position).getMessage().toString() != "") {
            System.out.println("IN IF: " + messageList.get(position).getMessage());
            holder.tv.setText(messageList.get(position).getMessage());
        } else {
            System.out.printf("IN ELSE");
            holder.tv.setText(" ");
        }
        if(messageList.get(position).getSender() == loggedIn) {
            rowView.findViewById(R.id.chat_this_translate_button).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyAsyncTask() {
                        protected void onPostExecute(String result) {
                            textView.setText(result);
                        }
                    }.execute(new TranslationRequest(spinner.getSelectedItemPosition(),messageList.get(position).getMessage()));
                }
            });
        } else {
            rowView.findViewById(R.id.chat_other_translate_button).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyAsyncTask() {
                        protected void onPostExecute(String result) {
                            textView.setText(result);
                        }
                    }.execute(new TranslationRequest(spinner.getSelectedItemPosition(),messageList.get(position).getMessage()));
                }
            });
        }
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageList.get(position).getSender() == loggedIn) {
                    textView = ((TextView) v.findViewById(R.id.chat_this));
                    spinner = ((Spinner) v.findViewById(R.id.chat_this_spinner));
                    if (v.findViewById(R.id.chat_this_message_time).getVisibility() == View.VISIBLE) {
                        v.findViewById(R.id.chat_this_message_time).setVisibility(View.INVISIBLE);
                        v.findViewById(R.id.chat_this_translate_layout).setVisibility(View.GONE);
                    } else {
                        v.findViewById(R.id.chat_this_message_time).setVisibility(View.VISIBLE);
                        v.findViewById(R.id.chat_this_translate_layout).setVisibility(View.VISIBLE);
                    }
                } else {
                    textView = ((TextView) v.findViewById(R.id.chat_other));
                    spinner = ((Spinner) v.findViewById(R.id.chat_other_spinner));
                    if (v.findViewById(R.id.chat_other_message_time).getVisibility() == View.VISIBLE) {
                        v.findViewById(R.id.chat_other_message_time).setVisibility(View.INVISIBLE);
                        v.findViewById(R.id.chat_other_translate_layout).setVisibility(View.GONE);
                    } else {
                        v.findViewById(R.id.chat_other_message_time).setVisibility(View.VISIBLE);
                        v.findViewById(R.id.chat_other_translate_layout).setVisibility(View.VISIBLE);
                    }
                }
                if(lastClickedView != v) {
                    if (lastClickedView != null) {
                        if (lastClickedView.findViewById(R.id.chat_this_message_time) != null) {
                            lastClickedView.findViewById(R.id.chat_this_message_time).setVisibility(View.INVISIBLE);
                            lastClickedView.findViewById(R.id.chat_this_translate_layout).setVisibility(View.GONE);
                        } else {
                            lastClickedView.findViewById(R.id.chat_other_message_time).setVisibility(View.INVISIBLE);
                            lastClickedView.findViewById(R.id.chat_other_translate_layout).setVisibility(View.GONE);
                        }
                    }
                    lastClickedView = v;
                }
                /*new MyAsyncTask() {
                    protected void onPostExecute(String result) {
                        textView.setText(result);
                    }
                }.execute(messageList.get(position).getMessage());*/
            }
        });
        return rowView;
    }

      class MyAsyncTask extends AsyncTask<TranslationRequest, Integer, String> {
        @Override
        protected String doInBackground(TranslationRequest... arg0) {
            Translate.setClientId("MeetASweedt");
            Translate.setClientSecret("aF+QBqtp8FANWemB7hqvkYPWrUbVwl85aih3n1vtDsc=");
            String s;
            try {
                s = Translate.execute(arg0[0].getMessage(), Language.AUTO_DETECT, arg0[0].getLanguage());
            } catch(Exception e) {
                s = e.toString();
            }
            return s;
        }
    }

    class TranslationRequest{
        Language language;
        String message;
        TranslationRequest(int to, String message){
            System.out.println("In translation");
            switch (to){
                case 0:
                    language = Language.ARABIC;
                    break;
                case 1:
                    language = Language.ENGLISH;
                    break;
                case 2:
                    System.out.println("FRENCH");
                    language = Language.FRENCH;
                    break;
                case 3:
                    language = Language.HEBREW;
                    break;
                case 4:
                    System.out.println("Japanska");
                    language = Language.JAPANESE;
                    break;
                case 5:
                    language = Language.CHINESE_TRADITIONAL;
                    break;
                case 6:
                    language = Language.KOREAN;
                    break;
                case 7:
                    language = Language.PERSIAN;
                    break;
                case 8:
                    language = Language.TURKISH;
                    break;
                case 9:
                    System.out.println("SWEDISH");
                    language = Language.SWEDISH;
                    break;
            }
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public Language getLanguage() {
            return language;
        }
    }

}
