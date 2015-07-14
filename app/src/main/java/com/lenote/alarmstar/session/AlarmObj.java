package com.lenote.alarmstar.session;

/**
 * Created by lenote on 2015/7/14.
 */
public class AlarmObj {
    int id;
    int type;//周期 工作日，休息日，每天，一次，自定义
    long onceTypeDate;//响铃一次的时候，对应的日期
    int[] weeks;//周期显示的时候的周几的数组（工作日，休息日，自定义）
    String ringUri;//铃声Uri 如果为空或不存用默认响铃。
    String alarmName;//闹钟名称
    boolean isNotify;
    long reAlarmInterval;//如果没有关闭闹钟，reAlarmInterval毫秒后重新提醒。

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getOnceTypeDate() {
        return onceTypeDate;
    }

    public void setOnceTypeDate(long onceTypeDate) {
        this.onceTypeDate = onceTypeDate;
    }

    public int[] getWeeks() {
        return weeks;
    }

    public void setWeeks(int[] weeks) {
        this.weeks = weeks;
    }

    public String getRingUri() {
        return ringUri;
    }

    public void setRingUri(String ringUri) {
        this.ringUri = ringUri;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setIsNotify(boolean isNotify) {
        this.isNotify = isNotify;
    }

    public long getReAlarmInterval() {
        return reAlarmInterval;
    }

    public void setReAlarmInterval(long reAlarmInterval) {
        this.reAlarmInterval = reAlarmInterval;
    }

    public String getWeeksString() {
        String info="";
        for(int w:weeks){
            info+=w+",";
        }
        return info.substring(0,info.length()-2);
    }
}
