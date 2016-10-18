package com.untitledapps.meetasweedt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fredr on 2016-10-18.
 */

public class MatchingProfileFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_USERNAME = "ARG_USERNAME";
    public static final String ARG_AGE = "ARG_AGE";
    public static final String ARG_FROM = "ARG_FROM";

    private int mPage;
    private String mAge;
    private String mUsername;
    private String mFrom;

    public static MatchingProfileFragment newInstance(int page, String age, String from) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_AGE, age);
        args.putString(ARG_FROM, from);
        MatchingProfileFragment fragment = new MatchingProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mAge = getArguments().getString(ARG_AGE);
        mFrom = getArguments().getString(ARG_FROM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matching_basic_info, container, false);
        TextView textView = (TextView) view.findViewById(R.id.BasicInfoAge);
        textView.setText(mAge);
        textView = (TextView) view.findViewById(R.id.BasicInfoFrom);
        textView.setText(mFrom);
        return view;
    }
}

