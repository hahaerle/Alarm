package com.lenote.alarmstar.tools;

import android.util.Log;

/**
 * Created by wxy on 2015/7/11.
 */
public class DebugLog {
    public static void i(String s) {
        Log.i("test",s);
    }

    public static void e(String s) {
        Log.e("error",s);
    }
}
