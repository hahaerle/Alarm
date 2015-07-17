package com.lenote.alarmstar.activity;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.session.AlarmSession;
import com.lenote.alarmstar.view.TimeSelectWheel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
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
        if(session==null)return;

    }

}
