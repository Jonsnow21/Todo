package com.neeraj.android.todo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by neeraj on 19/2/17.
 */

public class DateUtils {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy",
            Locale.getDefault());

    public static Calendar getCalendar(String textDate) {
        try {
            Date date = SIMPLE_DATE_FORMAT.parse(textDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            return Calendar.getInstance();
        }

    }
}
