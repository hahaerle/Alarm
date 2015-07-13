package com.lenote.alarmstar.session;

import android.app.PendingIntent;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by shanerle on 2015/7/13.
 */
public class AlarmSession implements Serializable{

    /**
     * 封装闹钟的行为
     */
    private PendingIntent pendingIntent;

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }
}
