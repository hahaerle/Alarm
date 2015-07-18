package com.lenote.alarmstar.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.lenote.alarmstar.R;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by wxy on 2015/7/18.
 */
public class BackgroundHelper {
    private static final int HOUR = 60 * 60 * 1000;
    static int[] backgrounds=new int[]{
            R.drawable.bg_morning,
            R.drawable.bg_afternoon,
            R.drawable.bg_night
    };
    private static BackgroundHelper instance;

    public static BackgroundHelper getInstance() {
        if(instance==null){
            instance=new BackgroundHelper();
        }
        return instance;
    }

    public Drawable getBackground(Context context){
        return context.getResources().getDrawable(getIndex());
    }
    private int getIndex() {
        DateTime today = DateTime.now(TimeZone.getDefault());
        int hour=today.getHour();
        int minute=today.getMinute();
        int second=today.getSecond();
        int nanoseconds=today.getNanoseconds();
        long current=((hour*60+minute)*60+second)*1000+nanoseconds/1000000;
        if(current<6*HOUR){
            return backgrounds[2];
        }else if(current<12*HOUR){
            return backgrounds[0];
        }else if(current<18*HOUR){
            return backgrounds[1];
        }else{
            return backgrounds[2];
        }
    }
}
