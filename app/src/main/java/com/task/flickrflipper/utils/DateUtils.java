package com.task.flickrflipper.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    public static String toStandardDateFormat(int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date(c.getTimeInMillis()));
    }

    public static String formattHour(long timeUs) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Date d = new Date();
        d.setTime(timeUs);
        return dateFormat.format(d);
    }


}
