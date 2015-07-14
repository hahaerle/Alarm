package com.lenote.alarmstar.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Parcelable;

import java.io.Serializable;

import hirondelle.date4j.DateTime;

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
