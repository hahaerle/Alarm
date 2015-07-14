package com.lenote.alarmstar.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.lenote.alarmstar.tools.Strings;

import java.util.Locale;

/**
 * Created by lenote on 2015/7/13.
 */
public class BaseDao {
    protected static int getColumnIndex(Cursor cursor, String columnName) {
        return cursor.getColumnIndex(columnName);
    }

    protected static String safeGetString(Cursor cursor, String columnName, String defaultValue) {
        return safeGetString(cursor, getColumnIndex(cursor, columnName), defaultValue);
    }


    protected static String safeGetString(Cursor cursor, int index, String defaultValue) {
        try {
            if (cursor.isNull(index)) {
                return defaultValue;
            } else {
                String string = cursor.getString(index);
                string = Strings.emptyToNull(string);
                if (string != null && "null".equals(string.toLowerCase(Locale.getDefault()))) {
                    string = null;
                }
                return string;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected static int safeGetInteger(Cursor cursor, int index, int defaultValue) {
        try {
            if (cursor.isNull(index)) {
                return defaultValue;
            } else {
                return cursor.getInt(index);
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected static int safeGetInteger(Cursor cursor, String columnName, int defaultValue) {
        return safeGetInteger(cursor, getColumnIndex(cursor, columnName), defaultValue);
    }


    protected static double safeGetDouble(Cursor cursor, int index, double defaultValue) {
        try {
            if (cursor.isNull(index)) {
                return defaultValue;
            } else {
                return cursor.getDouble(index);
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected static long safeGetLong(Cursor cursor, int index, long defaultValue) {
        try {
            if (cursor.isNull(index)) {
                return defaultValue;
            } else {
                return cursor.getLong(index);
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected static long safeGetLong(Cursor cursor, String columnName, long defaultValue) {
        return safeGetLong(cursor, getColumnIndex(cursor, columnName), defaultValue);
    }

    protected static double safeGetDouble(Cursor cursor, String columnName, double defaultValue) {
        return safeGetDouble(cursor, getColumnIndex(cursor, columnName), defaultValue);
    }

    protected static void compatibleBeginTransaction(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT >= 11) {
            db.beginTransactionNonExclusive();
        } else {
            db.beginTransaction();
        }
    }

    protected static String SQL_AND(String where1, String where2,String... where3) {
        String params="";
        for(String w:where3){
            params+=" and ( " + w + " )";
        }
        return "( " + where1 + " ) and ( " + where2 + " )"+params;
    }

    protected static String SQL_OR(String where1, String where2,String... where3) {
        String params="";
        for(String w:where3){
            params+=" or ( " + w + " )";
        }
        return "( " + where1 + " ) or ( " + where2 + " )"+params;
    }

    protected static String SQL_EQULES(String where1, int value) {
        return where1 + "  =  " + value;
    }

    protected static String SQL_EQULES(String where1, long value) {
        return where1 + "  =  " + value;
    }

    protected static String SQL_EQULES(String where1, String value) {
        return where1 + "  =  '" + value+ "' ";
    }
}
