package com.geekykelvin.taskplanner.reminder;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by Geeky Kelvin on 11/7/2018.
 * Email: Kelvinator4leo@gmail.com
 */

public class TaskManagerProvider {
    private static final String TAG = TaskManagerProvider.class.getSimpleName();
    private static AlarmManager sAlarmManager;
    public static synchronized void injectAlarmManager(AlarmManager alarmManager) {
        if (sAlarmManager != null) {
            throw new IllegalStateException("Alarm Manager Already Set");
        }
        sAlarmManager = alarmManager;
    }
    /*package*/ static synchronized AlarmManager getAlarmManager(Context context) {
        if (sAlarmManager == null) {
            sAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return sAlarmManager;
    }
}
