package com.lenote.alarmstar.tools;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by lenote on 2015/7/14.
 */
public abstract class WeakRefHandler<T> extends Handler {
    private final WeakReference<T> wr;

    public WeakRefHandler(T t) {
        this.wr = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        T t = wr.get();
        if (t == null){
            return;
        }
        handleMessage(t,msg);
    }

    protected abstract void handleMessage(T t, Message msg);
}