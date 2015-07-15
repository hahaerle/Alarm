package com.lenote.alarmstar.activity;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.adapter.TimeWheelAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends BaseActivity {
    @ViewById
    WheelView wheelView;
    private WheelViewAdapter mAdapter;
    @StringArrayRes(R.array.time_array)
    String[] time_array;

    @OptionsItem(R.id.action_settings)
    void setting(){

    }
    @AfterViews
    void init(){
        wheelView.setVisibleItems(3);
        mAdapter=new TimeWheelAdapter(this, time_array, R.layout.view_time_wheel_item_center);
        wheelView.setViewAdapter(mAdapter);
    }
}
