package SportsCalendar;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

import SportsCalendar.R;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //int tomorrow_month = intent.getIntExtra("tomorrow_month",0);
        //int tomorrow_day = intent.getIntExtra("tomorrow_day",0);

        int tomorrow_month = 10;
        int tomorrow_day = 3;

        //Log.d("날짜_리시버",intent.getExtras().toString());

        Log.e("리시버네용.","onReceive 작동됨.");

        Intent alarmIntentService = new Intent(context, AlarmIntentService.class);
        alarmIntentService.putExtra("tomorrow_month",tomorrow_month);
        alarmIntentService.putExtra("tomorrow_day",tomorrow_day);


        context.startService(alarmIntentService);


    }
}


