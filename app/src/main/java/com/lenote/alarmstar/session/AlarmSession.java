package com.lenote.alarmstar.session;

import android.app.PendingIntent;

import com.lenote.alarmstar.moudle.AlarmObj;

import java.io.Serializable;

/**
 * Created by lenote on 2015/7/13.
 */
public class AlarmSession implements Serializable{

   AlarmObj alarmObj;

    public AlarmObj getAlarmObj() {
        return alarmObj;
    }

    public void setAlarmObj(AlarmObj alarmObj) {
        this.alarmObj = alarmObj;
    }

    /**
     * 封装闹钟的行为
     */
    private PendingIntent pendingIntent;

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }
}
