package com.lenote.alarmstar.dao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by lenote on 2015/7/14.
 */
public interface DataBaseBuilder<T> {
    /**
     * Creates object out of cursor
     *
     * @param cursor
     * @return
     */
    public T build(Cursor cursor);

    public ContentValues deconstruct(T t);
}
