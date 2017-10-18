package com.example.aki.javaq.Domain.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.example.aki.javaq.Presentation.Quiz.QuizResultFragment;


/**
 * Created by AKI on 2017-08-20.
 */

public class ResetReceiver extends BroadcastReceiver {

    private SharedPreferences data;

    @Override
    public void onReceive(Context context, Intent intent) {


        Toast.makeText(context, "okay", Toast.LENGTH_SHORT).show();
        data = context.getSharedPreferences(QuizResultFragment.SHEARED_PREF_PROGRESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.clear().apply();

    }


}
