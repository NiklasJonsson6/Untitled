package com.untitledapps.meetasweedt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import static com.untitledapps.meetasweedt.MatchingActivity.context;


/**
 * Created by fredr on 2016-10-18.
 */

public class MatchingProfileListFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_INTREST = "ARG_INTREST";
    public static final String ARG_OWN_INTREST = "ARG_OWN_INTREST";

    private int mPage;
    private ArrayList<String> mIntrests;
    private ArrayList<String> mOwnIntrests;

    public static MatchingProfileListFragment newInstance(int page, String intrests, String ownIntrests) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_INTREST, intrests);
        args.putString(ARG_OWN_INTREST, ownIntrests);
        MatchingProfileListFragment fragment = new MatchingProfileListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Was in on create");
        mPage = getArguments().getInt(ARG_PAGE);
        mIntrests = new ArrayList<String>(Arrays.asList(getArguments().getString(ARG_INTREST).split("/")));
        mOwnIntrests = new ArrayList<String>(Arrays.asList(getArguments().getString(ARG_OWN_INTREST).split("/")));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matching_list, container, false);
        System.out.println(view.findViewById(R.id.intrests));
        System.out.println(mIntrests.size());
        System.out.println(mOwnIntrests.size());
        ListView listView = (ListView) view.findViewById(R.id.intrests);
        System.out.println(inflater);
        listView.setAdapter(new InterestListAdapter(inflater, mIntrests, mOwnIntrests));
        return view;
    }
}

