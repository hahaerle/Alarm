package com.lenote.alarmstar.activity;

import android.os.Bundle;

import com.umeng.update.UmengUpdateAgent;

/**
 * Created by lenote on 2015/7/13.
 */
public class SetupActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity_.intent(this).start();
        finish();
    }
}
