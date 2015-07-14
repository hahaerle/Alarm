package com.lenote.alarmstar.activity;

import com.lenote.alarmstar.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends BaseActivity {

    @OptionsItem(R.id.action_settings)
    void setting(){

    }
}
