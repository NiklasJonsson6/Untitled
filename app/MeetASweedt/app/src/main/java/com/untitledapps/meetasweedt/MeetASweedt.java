package com.untitledapps.meetasweedt;

import android.app.Application;

/**
 * Created by Niklas on 2016-10-16.
 */

public class MeetASweedt extends Application {
    private Person loggedInPerson;

    public void setLoggedInPerson(Person person) {
        loggedInPerson = person;
    }

    public Person getLoggedInPerson() {
        return loggedInPerson;
    }
}
