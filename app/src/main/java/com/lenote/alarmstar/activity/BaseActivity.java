package com.lenote.alarmstar.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.lenote.alarmstar.tools.BackgroundHelper;

/**
 * Created by lenote on 2015/7/13.
 */
public class BaseActivity extends Activity {
    private ProgressDialog mLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(BackgroundHelper.getInstance().getBackground(this));
        initActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
    protected void showLoading(String msg) {

        if (mLoading == null) {
            mLoading = ProgressDialog.show(this, msg, null, true, true);
            mLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mLoading = null;
                }
            });
            mLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mLoading = null;
                }
            });
        }
    }

    protected void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
            mLoading = null;
        }
    }

}
