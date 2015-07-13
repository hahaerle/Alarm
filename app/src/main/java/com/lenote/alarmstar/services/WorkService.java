package com.lenote.alarmstar.services;

import android.app.IntentService;
import android.content.Intent;

import com.lenote.alarmstar.session.AlarmSession;

import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;

/**
 * Created by shanerle on 2015/7/13.
 */
@EIntentService
public class WorkService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public WorkService() {
        super("WorkService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
    @ServiceAction
    public void setAlarm(AlarmSession session){

    }
}
