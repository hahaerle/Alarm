package com.lenote.alarmstar.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.lenote.alarmstar.MyApplication;


/**
 * Created by le_note on 2015/7/3.
 */
public class LocalPreference {

    private static final String CONFIG_SETTING = "alarm_setting";

    private static String getString(String configFile, String key, String defaultValue) {
        MyApplication instance = MyApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    private static void commitString(String configFile, String key, String value) {
        MyApplication instance = MyApplication.getInstance();
        instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE).edit()
                .putString(key, value)
                .commit();
    }
    private static Boolean getBoolean(String configFile, String key, boolean defaultValue) {
        MyApplication instance = MyApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    private static void commitBoolean(String configFile, String key, boolean value) {
        MyApplication instance = MyApplication.getInstance();
        instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE).edit()
                .putBoolean(key, value)
                .commit();
    }
    private static int getInt(String configFile, String key, int defaultValue) {
        MyApplication instance = MyApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    private static void commitInt(String configFile, String key, int value) {
        MyApplication instance = MyApplication.getInstance();
        instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE).edit()
                .putInt(key, value)
                .commit();
    }
    private static long getLong(String configFile, String key, long defaultValue) {
        MyApplication instance = MyApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    private static void commitLong(String configFile, String key, long value) {
        MyApplication instance = MyApplication.getInstance();
        instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE).edit()
                .putLong(key, value)
                .commit();
    }
    private static void remove(String configFile, String key) {
        MyApplication instance = MyApplication.getInstance();
        instance.getSharedPreferences(configFile,
                Context.MODE_PRIVATE).edit()
                .remove(key)
                .commit();
    }
    public static class Setting{

        public static final String IS_SHOW_LUNAR="is_show_lunar";
        private static String getConfigFile() {
            return CONFIG_SETTING;
        }
        public static boolean IsShowLunar(){
           return getBoolean(getConfigFile(),IS_SHOW_LUNAR,true);
        }
        public static void setIsShowLunar(boolean value){
            commitBoolean(getConfigFile(),IS_SHOW_LUNAR,value);
        }
    }

}
