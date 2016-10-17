package com.untitledapps.meetasweedt;

import java.util.Calendar;

/**
 * Created by fredr on 2016-09-27.
 */

public class Message {
    String message;
    Person sender;
    Calendar calendar;

    public Message(String message, Person sender, Calendar calendar) {
        this.message = message;
        this.sender = sender;
        this.calendar = calendar;
    }

    public Person getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String calanderString(){return calendar.get(Calendar.YEAR) + ":" + calendar.get(Calendar.MONTH) + ":" + calendar.get(Calendar.DAY_OF_MONTH) + ":" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);}
}
