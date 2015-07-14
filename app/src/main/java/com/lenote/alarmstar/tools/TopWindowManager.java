package com.lenote.alarmstar.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import com.lenote.alarmstar.MyApplication;
import com.lenote.alarmstar.session.AlarmSession;
import com.lenote.alarmstar.view.AlarmView;

/**
 * Created by lenote on 2015/6/16.
 */
public class TopWindowManager {
    private WindowManager wm=null;
    private WindowManager.LayoutParams wmParams=null;
    private AlarmView mAlarmView;
    private AlarmSession mAlarmSession;
    private boolean isShow=false;

    public interface ITopWindowDismissInterface{
        void onDismiss();

        void onConfirm(Bundle bundle);
    }
    public TopWindowManager(){
        init();
    }
    private void init(){
        //获取WindowManager
        Context context= MyApplication.getInstance();
        wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        wmParams = new WindowManager.LayoutParams();

        wmParams.type= WindowManager.LayoutParams.TYPE_PHONE;   //设置window type
        wmParams.format= PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明
        /*//设置Window flag
        wmParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
*/
        //以屏幕左上角为原点，设置x、y初始值
        //设置悬浮窗口长宽数据
        wmParams.gravity= Gravity.CENTER;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//        topWindowView = AlertTopWindow_.build(context);
    }

    ITopWindowDismissInterface mInterface=new ITopWindowDismissInterface() {
        @Override
        public void onDismiss() {
            clear();
        }

        @Override
        public void onConfirm(Bundle bundle) {

        }
    };


    Handler mHandler = new _Handler(this);

    private static class _Handler extends WeakRefHandler<TopWindowManager> {
        public _Handler(TopWindowManager service) {
            super(service);
        }

        @Override
        protected void handleMessage(TopWindowManager service, Message msg) {
            if(msg.what==1){
                service.rebindTopWindow();
            }
        }

    }

    private void rebindTopWindow() {
        mAlarmView.setInterface(mInterface);
        mAlarmView.bindData(mAlarmSession);
        showWindow();
    }
    private void showWindow() {
        if (mAlarmView!=null){
            if(isShow) {
                wm.removeView(mAlarmView);
            }
            wm.addView(mAlarmView, wmParams);
            isShow=true;
        }
    }

    public void clear(){
        if (mAlarmView!=null && isShow){
            isShow=false;
            wm.removeView(mAlarmView);
        }
    }
}
