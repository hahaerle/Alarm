package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.session.AlarmSession;
import com.lenote.alarmstar.tools.ShutAlarmManager;
import com.lenote.alarmstar.tools.TopWindowManager;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by lenote on 2015/7/14.
 */
@EViewGroup(R.layout.view_alarm)
public class AlarmView extends RelativeLayout {
    public AlarmView(Context context) {
        super(context);
        init();
    }

    public AlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlarmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ShutAlarmManager.getInstance().register(new ShutAlarmManager.ISharkEventListener() {
            @Override
            public void OnShark() {
                //TODO UI changed
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ShutAlarmManager.getInstance().unregister();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlarmView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setInterface(TopWindowManager.ITopWindowDismissInterface mInterface) {

    }

    public void bindData(AlarmSession mAlarmSession) {

    }
}
