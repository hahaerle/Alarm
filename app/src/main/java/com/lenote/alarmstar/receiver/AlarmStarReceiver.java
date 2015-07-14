package com.lenote.alarmstar.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lenote.alarmstar.session.AlarmSession;

/**
 * Created by lenote on 2015/7/14.
 */
public class AlarmStarReceiver extends BroadcastReceiver {
    public final static String ACTION_ALARM="action_alarm_com_lenote_alarmstar";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(TextUtils.equals(action,ACTION_ALARM)){
            AlarmSession session=(AlarmSession)intent.getSerializableExtra("alarm_session");
        }else if(TextUtils.equals(action,"android.intent.action.BOOT_COMPLETED")){
            //
        }
    }
}
