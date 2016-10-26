package com.untitledapps.meetasweedt;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fredr on 2016-10-18.
 */

public class FragmentMatchingProfileAdapter extends FragmentPagerAdapter {
    static final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Information", "Intressen"};
    private Person person;
    private Person loggedInPerson;

    public FragmentMatchingProfileAdapter(FragmentManager fm, Context context, Person person, Person loggedInPerson) {
        super(fm);
        this.person = person;
        this.loggedInPerson = loggedInPerson;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("Hej jag heter carl");
        if(position == 1){
            return MatchingProfileListFragment.newInstance(position + 1, person.getIntrestAsString(), loggedInPerson.getIntrestAsString());
        } else {
            return MatchingProfileFragment.newInstance(position + 1, "Ålder: " + person.getAge(), "Från: " + person.getOrginCountry());
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

