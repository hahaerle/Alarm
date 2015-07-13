package com.lenote.alarmstar;

import android.app.Application;

/**
 * Created by shanerle on 2015/7/13.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;
    public static MyApplication getInstance(){
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        init();
    }

    private void init() {
        //系统级别的启动
    }
}
