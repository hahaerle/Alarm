package com.lenote.alarmstar.activity;

import android.view.View;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.moudle.AlarmObj;
import com.lenote.alarmstar.session.AlarmSession;
import com.lenote.alarmstar.view.TimeSelectWheel;
import com.lenote.alarmstar.view.TimeSelectWheel_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Created by shanerle on 2015/7/16.
 */
@EActivity(R.layout.activity_alarm_edit)
@OptionsMenu(R.menu.save_alarm)
public class AlarmEditActivity extends  BaseActivity {
    @ViewById
    TimeSelectWheel wheelView;
    @Extra
    AlarmSession session;

    @AfterViews
    void udpateUI() {
        long defaultTime=System.currentTimeMillis()+1000*60*10;
        if(session!=null){
            defaultTime=session.getAlarmObj().getAlarmTime();
        }
        TimeSelectWheel wheelView = TimeSelectWheel_.build(this);
        wheelView.prepareData(TimeSelectWheel.TYPE_ALL, defaultTime, true, true);
        wheelView.setTimeWheelListener(new TimeSelectWheel.TimeWheelListener() {
            @Override
            public void onDateTimeSelected(long timeStamp) {
                getSession().getAlarmObj().setAlarmTime(timeStamp);
            }
        });
    }

    private AlarmSession getSession() {
        if(session==null){
            session=new AlarmSession();
            AlarmObj alarmObj=new AlarmObj();
            session.setAlarmObj(alarmObj);
        }
        return session;
    }

    @OptionsItem
    void action_save(){

    }

}
