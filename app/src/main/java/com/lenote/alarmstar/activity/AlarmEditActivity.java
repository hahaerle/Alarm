package com.lenote.alarmstar.activity;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.moudle.AlarmObj;
import com.lenote.alarmstar.view.LogoActionBar;
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
    @ViewById
    LogoActionBar actionBar;
    @Extra
    AlarmObj alarmObj;

    @AfterViews
    void udpateUI() {
        long defaultTime=System.currentTimeMillis()+1000*60*10;
        if(alarmObj!=null){
            defaultTime=alarmObj.getAlarmTime();
        }
        wheelView.setTimeWheelListener(new TimeSelectWheel.TimeWheelListener() {
            @Override
            public void onDateTimeSelected(long timeStamp) {
                getAlarmObj().setAlarmTime(timeStamp);
            }
        });
        wheelView.prepareData(defaultTime, true);
        actionBar.setListener(new LogoActionBar.IMenuClickListener() {
            @Override
            public void onMenuClick() {
                save();
                finish();
            }
        });
    }

    private void save() {

    }

    private AlarmObj getAlarmObj() {
        if(alarmObj==null){
            alarmObj=new AlarmObj();
        }
        return alarmObj;
    }

    @OptionsItem
    void action_save(){

    }

}
