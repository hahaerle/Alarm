package com.lenote.alarmstar.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.lenote.alarmstar.MyApplication;
import com.lenote.alarmstar.session.AlarmSession;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by lenote on 2015/7/13.
 */

/**
 * RTC_WAKEUP  在指定的时刻（设置Alarm的时候）,唤醒设备来触发Intent
 */
public class MyAlarmManager {

    public static final long TIME_IN_MILLIS_HOUR=1000*60*60*24;
    public static final long TIME_IN_MILLIS_DAY=TIME_IN_MILLIS_HOUR*24;
    public static final long TIME_IN_MILLIS_WEEK=TIME_IN_MILLIS_DAY*7;

    public static final int Sunday=1;//    星期日
    public static final int Monday=2;//    星期一
    public static final int Tuesday=3;//    星期二
    public static final int Wednesday=4;//    星期三
    public static final int Thursday=5;//    星期四
    public static final int Friday=6;//    星期五
    public static final int Saturday=7;//    星期六

    /**
     * cancel(PendingIntent operation)方法将会取消Intent匹配的任何闹钟。
     * 关于Intent的匹配，查看filterEquals(Intent other)方法的说明可知，
     * 两个Intent从intent resolution(filtering)(Intent决议或过滤)的角度来看是一致的，即认为两个Intent相等。
     * 即是说，Intent的action,data,type,class,categories是相同的，其他的数据都不在比较范围之内。
     * @param session
     */
    public static void cancel(AlarmSession session){

    }
    /**
     * 工作日
     * @param hour
     * @param minute
     * @param session
     */
    public static void setAlarmWorkDay(int hour, int minute,AlarmSession session){
        setAlarmRepeating(hour,minute,new int[]{Monday,Tuesday,Wednesday,Thursday,Friday},session);
    }

    /**
     * 休息日
     * @param hour
     * @param minute
     * @param session
     */
    public static void setAlarmRestDay(int hour, int minute,AlarmSession session){
        setAlarmRepeating(hour,minute,new int[]{Sunday,Saturday},session);
    }

    /**
     * 每天
     * @param hour
     * @param minute
     * @param session
     */
    public static void setAlarmEveryDay(int hour, int minute,AlarmSession session){
        Context context= MyApplication.getInstance().getApplicationContext();
        AlarmManager alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        int alarmMinute=hour*60+minute;
        DateTime now = DateTime.now(TimeZone.getDefault());
        int days=0;
        if((now.getHour() * 60 + now.getMinute() > alarmMinute)){
            //如果今天过了，从明天开始
            days=1;
        }
        DateTime alarmTime=new DateTime(now.getYear(),now.getMonth(),now.getDay()+days,hour,minute,0,0);
        //set
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getMilliseconds(TimeZone.getDefault()), TIME_IN_MILLIS_DAY, session.getPendingIntent());
    }


    /**
     * 设置起床闹钟，这个是一个周期闹钟，根据设置的，hour,minute,和 weekdays[i] 计算出最近的闹钟时间
     * 周期为一周 TIME_IN_MILLIS_WEEK
     * example: 若是 String [] weekdays=new String[]{1,3};
     * 那么对周一和周三分别设置一个周期是一周的闹钟
     *
     * @param hour
     * @param minute
     * @param weekdays （1，sunday ，2 monday，）
     * @param session
     */
    public static void setAlarmRepeating(int hour, int minute, int[] weekdays, AlarmSession session){

        Context context= MyApplication.getInstance().getApplicationContext();
        AlarmManager alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        int alarmMinute=hour*60+minute;
        DateTime now = DateTime.now(TimeZone.getDefault());
        for(int i=0;i<weekdays.length;i++){
            int days=weekdays[i]-now.getWeekDay();
            if(days<0||
                    (days==0&&(now.getHour() * 60 + now.getMinute() > alarmMinute))){
                //如果今天过了，从下周开始
                days+=7;
            }
            DateTime alarmTime=new DateTime(now.getYear(),now.getMonth(),now.getDay()+days,hour,minute,0,0);
            //set
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getMilliseconds(TimeZone.getDefault()),TIME_IN_MILLIS_WEEK,session.getPendingIntent());
        }
    }

    /**
     * 设置指定日期时间点，必须是当前时间之后的，如果是之前的，用UI层给出提示，
     * 闹钟时间无效，是否在明天这个时间（hour:minute）响铃。
     * @param timeInMillis
     * @param session
     */
    public static void setAlarmOnce(long timeInMillis, AlarmSession session){
        Context context= MyApplication.getInstance().getApplicationContext();
        AlarmManager alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, timeInMillis, session.getPendingIntent());
    }

    /**
     * 倒计时
     * @param timeInMillis 倒计时的毫秒数
     * @param session
     */
    public static void setAlarmCountDown(long timeInMillis,AlarmSession session){
        Context context= MyApplication.getInstance().getApplicationContext();
        AlarmManager alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeInMillis, session.getPendingIntent());
    }
}
