package com.example.aki.javaq;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by MinaFujisawa on 2017/06/14.
 */

public class DayOfWeek {
    private Calendar currentDate;
    private String day;

    public DayOfWeek() {
        currentDate = new GregorianCalendar();
        day = currentDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }
    public String getDay() {
        return day;
    }

    public void setDay(String dayOfWeek) {
        this.day = dayOfWeek;
    }
}
