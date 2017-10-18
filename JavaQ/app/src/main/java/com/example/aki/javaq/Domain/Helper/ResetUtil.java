package com.example.aki.javaq.Domain.Helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.aki.javaq.Presentation.Progress.ProgressFragment;
import com.example.aki.javaq.Presentation.Quiz.QuizSectionFragment;

import java.util.Calendar;

import static android.icu.util.Calendar.SUNDAY;


/**
 * Created by AKI on 2017-08-20.
 */

public class ResetUtil {

    public static void setReset(Context context){
        Intent intent = new Intent(context, ResetReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
//        calendar.set(Calendar.DAY_OF_WEEK, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 14);
//        calendar.set(Calendar.MINUTE, 20);
//        calendar.set(Calendar.SECOND, 0);


        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

}
