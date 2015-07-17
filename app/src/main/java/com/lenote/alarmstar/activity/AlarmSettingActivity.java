package com.lenote.alarmstar.activity;

import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import org.androidannotations.annotations.Click;

/**
 * Created by shanerle on 2015/7/17.
 */
public class AlarmSettingActivity extends BaseActivity{
    private boolean isClickUpdate=false;

    @Click
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
