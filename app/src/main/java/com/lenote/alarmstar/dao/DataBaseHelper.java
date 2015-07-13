package com.lenote.alarmstar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by le_note on 14-11-24.
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "alarm.db";
    public static final int DB_VERSION = 1; //

    private static DataBaseHelper mDataBaseHelper = null;

    /**
     * 获取实例
     *
     * @param context
     * @return
     */
    public static DataBaseHelper getInstance(Context context) {
        if (mDataBaseHelper == null) {
            synchronized (DataBaseHelper.class) {
                mDataBaseHelper = new DataBaseHelper(context);
            }
        }
        return mDataBaseHelper;
    }

    /**
     * @param context
     * @return
     */
    public synchronized static SQLiteDatabase getWritableDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    /**
     * @param context
     * @return
     */
    public synchronized static SQLiteDatabase getReadableDatabase(Context context) {
        return getInstance(context).getReadableDatabase();
    }

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        AlarmDao.onCreateTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        AlarmDao.onUpgradeTable(sqLiteDatabase, oldVersion, newVersion);
    }
}
