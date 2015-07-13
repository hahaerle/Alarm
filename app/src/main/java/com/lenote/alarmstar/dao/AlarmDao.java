package com.lenote.alarmstar.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanerle on 2015/7/13.
 */
public class AlarmDao extends BaseDao {
    /**
     * 创建数据库
     *
     * @param db
     */
    protected static void onCreateTable(SQLiteDatabase db) {
        /*String sql = "CREATE  TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                "  " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "  " + TO_USER_ID + " INTEGER NULL ,\n" +
                "  " + OWN_USER_ID + " INTEGER NULL ,\n" +
                "  " + TO_USER_NAME + " VARCHAR(10) NULL ,\n" +
                "  " + TO_USER_AVATAR + " VARCHAR(100) NULL ,\n" +
                "  " + UNREAD_NUM + " INTEGER NULL  ,\n " +
                "  " + MOBILE + " VARCHAR(20) NULL , \n" +
                "  " + IS_CLOSE + " INTEGER DEFAULT 0 , \n" +
                "  " + CHAT_TYPE + " INTEGER DEFAULT 0 , \n" +
                "  " + LAST_MSG + " TEXT NULL , \n" +
                "  " + UPDATE_TIME + " LONG)";
        db.execSQL(sql);*/
    }

    /**
     * 升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    protected static void onUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*int version = db.getVersion();
        DebugLog.i("dbVersion:" + version);
        //在已存在的表中新增字段时并给字段设置默认值，该默认值必须是确切的值。
        if (oldVersion < 3) { //V1.3 版本添加update_time 、is_close 字段

            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD " + UPDATE_TIME + " LONG ;");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD " + IS_CLOSE + " INTEGER DEFAULT 0 ;");
            ContentValues contentValues = new ContentValues();
            contentValues.put(UPDATE_TIME, System.currentTimeMillis());
            db.update(TABLE_NAME, contentValues, null, null);
        }

        if(oldVersion<6){
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD " + CHAT_TYPE + " INTEGER DEFAULT 0 ;");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD " + LAST_MSG + " TEXT NULL ;");
            if(ConfigManager.User.isLogin()){
                insertDefaultData(db);
            }
        }*/
    }
}
