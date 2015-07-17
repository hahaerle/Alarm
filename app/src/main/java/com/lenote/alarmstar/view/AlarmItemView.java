package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.session.AlarmSession;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by shanerle on 2015/7/16.
 */
@EViewGroup(R.layout.view_alarm_item)
public class AlarmItemView extends LinearLayout {
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
    public void bindData(AlarmSession session){

    }
}
