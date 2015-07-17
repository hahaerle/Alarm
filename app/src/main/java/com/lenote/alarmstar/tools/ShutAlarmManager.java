package com.lenote.alarmstar.tools;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.lenote.alarmstar.MyApplication;

/**
 * Created by shanerle on 2015/7/15.
 */
public class ShutAlarmManager implements SensorEventListener{

    private static ShutAlarmManager instance;

    public static ShutAlarmManager getInstance() {
        if(instance==null){
            instance=new ShutAlarmManager();
        }
        return instance;
    }

    public interface ISharkEventListener{
        void OnShark();
    }
    private SensorManager mSensorManager;
//    private Vibrator vibrator;
    ISharkEventListener mListener;
    public ShutAlarmManager(){
        //获取传感器管理服务
        mSensorManager = (SensorManager) MyApplication.getInstance(). getSystemService(Service.SENSOR_SERVICE);
        //震动
//        vibrator = (Vibrator) MyApplication.getInstance().getSystemService(Service.VIBRATOR_SERVICE);
    }
    public void register(ISharkEventListener listener){
        this.mListener=listener;
        mSensorManager.registerListener(this
                ,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
                //根据不同应用，需要的反应速率不同，具体根据实际情况设定
                ,SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void unregister(){
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;

        if(sensorType == Sensor.TYPE_ACCELEROMETER){

          /*因为一般正常情况下，任意轴数值最大就在9.8~10之间，只有在你突然摇动手机
          *的时候，瞬时加速度才会突然增大或减少。
          *所以，经过实际测试，只需监听任一轴的加速度大于14的时候，改变你需要的设置
          *就OK了~~~
          */
            if((Math.abs(values[0])>14||Math.abs(values[1])>14||Math.abs(values[2])>14)){
                if(mListener!=null) {
                    mListener.OnShark();
                }
//                vibrator.vibrate(500);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
