package com.example.aki.javaq.Domain.Helper;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MinaFujisawa on 2017/07/13.
 */

public class TimeUtils {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEK_MILLIS = 7 * DAY_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + "m";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + "h";
        } else if (diff < 7 * DAY_MILLIS) {
            return diff / DAY_MILLIS + "d";
        } else {
            return diff / WEEK_MILLIS + "w";
        }
    }
}
