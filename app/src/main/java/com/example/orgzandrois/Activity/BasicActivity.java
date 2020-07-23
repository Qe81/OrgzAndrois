package com.example.orgzandrois.Activity;

import com.example.orgzandrois.R;
import com.example.orgzandrois.Week.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




public class BasicActivity extends BaseActivity {


    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with the events that was added by tapping on empty view.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);



        // Populate the week view with some events.

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 9);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 10);
        endTime.set(Calendar.MINUTE, 0);
        WeekViewEvent event = new WeekViewEvent(10,"Спина Егор К.Кл.", getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);


        return events;
    }

}