package com.lenote.alarmstar.activity;

import android.widget.Toast;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.view.LogoActionBar;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by shanerle on 2015/7/17.
 */
@EActivity(R.layout.activity_alarm_setting)
public class AlarmSettingActivity extends BaseActivity{

    @Click
    void sound(){

    }
    @Click
    void shake(){

    }
    @Click
    void about(){

    }
    @Click
    void help(){

    }
    @Click
    void other(){

    }
    @Click
    void camera(){

    }

    private boolean isClickUpdate=false;


    void checkUpdate(){
        if (isClickUpdate) return;
        isClickUpdate = true;
        UmengUpdateAgent.forceUpdate(this);
        showLoading("正在检查更新...");
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                isClickUpdate = false;
                dismissLoading();
                switch (updateStatus) {
                    case 0: // has update
                        if (updateInfo != null) {
                            UmengUpdateAgent.showUpdateDialog(AlarmSettingActivity.this, updateInfo);
                        }
                        break;
                    case 1: // has no update
                        Toast.makeText(AlarmSettingActivity.this, "您当前已是最新版本", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // none wifi
                        Toast.makeText(AlarmSettingActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: // time out
                        Toast.makeText(AlarmSettingActivity.this, "网络不给力呦，请稍后再试!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


}
