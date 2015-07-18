package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.moudle.AlarmObj;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by shanerle on 2015/7/16.
 */
@EViewGroup(R.layout.view_alarm_item)
public class AlarmItemView extends LinearLayout {
    @ViewById
    TextView alarmTime;
    @ViewById
    TextView info;
    @ViewById
    CheckBox chkOrderNotify;

    public AlarmItemView(Context context) {
        super(context);
    }

    public AlarmItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlarmItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void bindData(AlarmObj alarmObj){
        alarmTime.setText(getAlarmTime(alarmObj));
        info.setText(getAlarmInfo(alarmObj));
        chkOrderNotify.setChecked(alarmObj.isOpen());
    }

    private int getAlarmInfo(AlarmObj alarmObj) {
        return 0;
    }

    private int getAlarmTime(AlarmObj alarmObj) {
        return 0;
    }
}
