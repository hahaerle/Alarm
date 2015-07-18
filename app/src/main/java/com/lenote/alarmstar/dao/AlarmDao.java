package com.lenote.alarmstar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lenote.alarmstar.activity.MainActivity;
import com.lenote.alarmstar.moudle.AlarmObj;
import com.lenote.alarmstar.session.AlarmSession;

import java.util.List;

/**
 * Created by lenote on 2015/7/13.
 */
public class AlarmDao extends BaseDao {
    public static final int TYPE_NOCE=1;
    public static final int TYPE_WORKDAY=2;
    public static final int TYPE_RESTDAY=3;
    public static final int TYPE_EVERYDAY=4;
    public static final int TYPE_USER_DEFINE=5;

    public static final String TABLE_NAME = "t_alarminfo";
    public static final String ID = "id";
    public static final String ALARM_TIME="alarm_time";
    public static final String ALARM_TYPE = "type";
    public static final String WEEKS = "weeks";
    public static final String TYPE_NOCE_DATE = "type_noce_date";
    public static final String RING_URI = "ringUri";
    public static final String ALARM_NAME = "alarmName";
    public static final String IS_NOTIFY = "isNotify";
    public static final String REALARM_INTERVAL = "reAlarmInterval";  //
    public static final String IS_OPEN = "is_open";  //

    private static final String[] COLUMNS = new String[]{
            ID,ALARM_TIME, ALARM_TYPE, WEEKS, TYPE_NOCE_DATE, RING_URI, ALARM_NAME, IS_NOTIFY, REALARM_INTERVAL,IS_OPEN
    };

    /**
     * 创建数据库
     *
     * @param db
     */
    protected static void onCreateTable(SQLiteDatabase db) {
        String sql = "CREATE  TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                "  " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "  " + ALARM_TIME + " LONG ,\n" +
                "  " + ALARM_TYPE + " INTEGER NULL ,\n" +
                "  " + TYPE_NOCE_DATE + " LONG ,\n" +
                "  " + REALARM_INTERVAL + " LONG ,\n" +
                "  " + WEEKS + " VARCHAR(10) NULL ,\n" +
                "  " + RING_URI + " VARCHAR(100) NULL ,\n" +
                "  " + IS_NOTIFY + " INTEGER NULL  ,\n " +
                "  " + IS_OPEN + " INTEGER NULL  ,\n " +
                "  " + ALARM_NAME + " VARCHAR(20))";
        db.execSQL(sql);
    }

    /**
     * 升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    protected static void onUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
//        升级时用
    }

    /**
     * 根据ID 获取闹钟信息
     * @param context
     * @param id
     * @return
     */
    public static AlarmObj getAlarmByID(Context context , int id) {
        AlarmObj alarmObj = null;
        String where=SQL_EQULES(ID, id);
        SQLiteDatabase db = DataBaseHelper.getReadableDatabase(context);
        compatibleBeginTransaction(db);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, where
                , null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst() && !cursor.isAfterLast()) {
                alarmObj = new AlarmBuilder().build(cursor);
            }
            cursor.close();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return alarmObj;
    }

    private static int[] stringToIntArray(String info) {
        String str[] = info.split(",");
        int array[] = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        return array;
    }

    public static List<AlarmObj> getAlarmList(Context context) {
        SQLiteDatabase db = DataBaseHelper.getReadableDatabase(context);
        compatibleBeginTransaction(db);
        db.setTransactionSuccessful();
        db.endTransaction();
        return null;
    }

    public static class AlarmBuilder implements DataBaseBuilder<AlarmObj> {

        @Override
        public AlarmObj build(Cursor cursor) {
            AlarmObj alarmObj = new AlarmObj();
            alarmObj.setId(safeGetInteger(cursor, ID, 0));
            alarmObj.setAlarmName(safeGetString(cursor, ALARM_NAME, ""));
            alarmObj.setIsNotify(safeGetInteger(cursor, IS_NOTIFY, 1) == 1);
            alarmObj.setIsOpen(safeGetInteger(cursor, IS_OPEN, 1) == 1);
            alarmObj.setOnceTypeDate(safeGetLong(cursor, TYPE_NOCE_DATE, 0));
            alarmObj.setReAlarmInterval(safeGetLong(cursor, REALARM_INTERVAL, 0));
            alarmObj.setRingUri(safeGetString(cursor, RING_URI, ""));
            alarmObj.setWeeks(stringToIntArray(safeGetString(cursor, WEEKS, "")));
            alarmObj.setType(safeGetInteger(cursor, ALARM_TYPE, 0));
            alarmObj.setAlarmTime(safeGetLong(cursor, ALARM_TIME, 0));
            return alarmObj;
        }

        @Override
        public ContentValues deconstruct(AlarmObj alarmObj) {
            ContentValues values = new ContentValues();
            values.put(ID, alarmObj.getId());
            values.put(ALARM_TYPE, alarmObj.getType());
            values.put(WEEKS, alarmObj.getWeeksString());
            values.put(TYPE_NOCE_DATE, alarmObj.getOnceTypeDate());
            values.put(RING_URI, alarmObj.getRingUri());
            values.put(ALARM_NAME, alarmObj.getAlarmName());
            values.put(IS_NOTIFY, alarmObj.isNotify());
            values.put(IS_OPEN, alarmObj.isOpen());
            values.put(REALARM_INTERVAL,alarmObj.getReAlarmInterval());
            values.put(ALARM_TIME,alarmObj.getAlarmTime());
            return values;
        }
    }
}
