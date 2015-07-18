package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.lenote.alarmstar.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;

/**
 * Created by wxy on 2015/7/18.
 */
@EViewGroup(R.layout.view_menu_create_alarm)
public class CreateAlarmMenu extends RelativeLayout {
    public interface IButtonClickListener{
        void onCreateWakeup();
        void onCreateNoraml();
        void onCreateCoundDown();
        void onClose();
    }
    IButtonClickListener listener;

    public void setListener(IButtonClickListener listener) {
        this.listener = listener;
    }

    public CreateAlarmMenu(Context context) {
        super(context);
    }

    public CreateAlarmMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CreateAlarmMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CreateAlarmMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Click
    void create_wakeup(){
        if(listener!=null){
            listener.onCreateWakeup();
        }
    }
    @Click
     void create_normal(){
        if(listener!=null){
            listener.onCreateNoraml();
        }
    }
    @Click
     void create_count_down(){
        if(listener!=null){
            listener.onCreateCoundDown();
        }
    }
    @Click
    void close_btn(){
        if(listener!=null){
            listener.onClose();
        }
    }
}
