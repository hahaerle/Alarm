package com.lenote.alarmstar.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.adapter.TimeWheelAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.lang.ref.WeakReference;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

/**
 * Created by lenote on 2015/7/16.
 */
@EViewGroup(R.layout.view_time_wheel)
public class TimeSelectWheel extends LinearLayout {
    public static final int MSG_WHEEL_CHANGED = 1;
    private boolean selectFromProgram;

    public interface TimeWheelListener {
        void onDateTimeSelected(long timeStamp);
    }

    public static final int MIN_SPAN = 500;
    //最小分钟的颗粒度
    private static final int MINUTE_UNIT = 5;

    private TimeWheelAdapter daysAdapter;
    private TimeWheelAdapter hoursAdapter;
    private TimeWheelAdapter minutesAdapter;

    public TimeSelectWheel(Context context) {
        super(context);
    }

    public TimeSelectWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeSelectWheel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    long timeStamp;
    boolean showSecond;
    private TimeWheelListener listener;
    private long lastChange;

    @ViewById(R.id.seconds)
    WheelView secondsWheel;
    @ViewById(R.id.hours)
    WheelView hoursWheel;
    @ViewById(R.id.mins)
    WheelView minutesWheel;

    @StringArrayRes(R.array.full_day_hours)
    String[] allHours;

    @StringArrayRes(R.array.minus)
    String[] minutesArray;
    @StringArrayRes(R.array.second)
    String[] secondArray;

    Handler mUIHandler = new _Handler(this);

    private static final class _Handler extends Handler{
        private final WeakReference<TimeSelectWheel> wr;

        private _Handler(TimeSelectWheel view) {
            this.wr = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            TimeSelectWheel view = wr.get();
            if (view == null){
                return;
            }
            if (msg.what == MSG_WHEEL_CHANGED){
                view.onMsgWheelChanged();
            }
        }
    }

    private void onMsgWheelChanged() {
        if (noChangeInText_InTheLastFewSeconds()) {
            onWheelChanged();
        }else {
            mUIHandler.sendEmptyMessageDelayed(MSG_WHEEL_CHANGED, MIN_SPAN);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mUIHandler.removeMessages(MSG_WHEEL_CHANGED);
    }

    @AfterViews
    void initUI() {
        clearWheelShadowAndBg(secondsWheel);
        clearWheelShadowAndBg(hoursWheel);
        clearWheelShadowAndBg(minutesWheel);

        secondsWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mUIHandler.sendEmptyMessageDelayed(MSG_WHEEL_CHANGED, MIN_SPAN);
                lastChange = System.currentTimeMillis();
            }
        });

        hoursWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mUIHandler.sendEmptyMessageDelayed(MSG_WHEEL_CHANGED,MIN_SPAN);
                lastChange = System.currentTimeMillis();
            }
        });

        minutesWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mUIHandler.sendEmptyMessageDelayed(MSG_WHEEL_CHANGED,MIN_SPAN);
                lastChange = System.currentTimeMillis();
            }
        });
    }

    private boolean noChangeInText_InTheLastFewSeconds() {
        return System.currentTimeMillis() - lastChange >= MIN_SPAN;
    }

    public void onWheelChanged() {
        if (selectFromProgram) {
            return;
        }

        DateTime dateTime = getWheelSelectionDatetime();
        setWheelSelection(dateTime);
        onSelcet();
    }

    private DateTime getWheelSelectionDatetime() {
        int year, month, day, hour, minute;

        DateTime now = DateTime.now(TimeZone.getDefault());
        year = now.getYear();
        month = now.getMonth();
        day = now.getDay();


        if (showSecond) {
            try {
                String selectDay = daysAdapter.getItemText(
                        secondsWheel.getCurrentItem()).toString();
                int plusDay = 0;
                for (String dayString : daysAdapter.getArray()) {
                    if (selectDay.equals(dayString)){
                        break;
                    }
                    plusDay++;
                }
                now =  now.plusDays(plusDay);
                year = now.getYear();
                month = now.getMonth();
                day = now.getDay();
            } catch (Exception ignored) {
            }
        }
        hour = 0;
        try {
            String selectHour = hoursAdapter.getItemText(
                    hoursWheel.getCurrentItem()).toString();
            selectHour = selectHour.substring(0,selectHour.length()-1);
            if (selectHour.startsWith("0")){
                selectHour = selectHour.substring(1,selectHour.length());
            }
            hour = Integer.valueOf(selectHour);
        } catch (Exception ignored) {
        }

        minute = 0;
        try {
            String selectMinute = minutesAdapter.getItemText(
                    minutesWheel.getCurrentItem()).toString();
            selectMinute = selectMinute.substring(0,selectMinute.length()-1);
            if ("00".equals(selectMinute)) {
                minute = 0;
            } else {
                minute = Integer.valueOf(selectMinute);
            }
        } catch (Exception ignored) {
        }


        return new DateTime(year, month, day, hour, minute, 0, 0);
    }

    public void prepareData(long timeStamp, boolean showSecond) {
        this.timeStamp = timeStamp;
        this.showSecond = showSecond;

        fillData();

        DateTime dateTime;
        if (timeStamp == 0) {
            dateTime = DateTime.now(TimeZone.getDefault());
        } else {
            dateTime = DateTime.forInstant(timeStamp, TimeZone.getDefault());
        }

        setWheelSelection(dateTime);
    }


    public void fillData() {
        if (showSecond) {
            daysAdapter = new TimeWheelAdapter(getContext(), secondArray,R.layout.view_time_wheel_item_right);
            secondsWheel.setViewAdapter(daysAdapter);

            secondsWheel.setVisibility(VISIBLE);
        } else {
            secondsWheel.setVisibility(GONE);
        }


        hoursAdapter = new TimeWheelAdapter(getContext(),allHours,R.layout.view_time_wheel_item_left);
        hoursWheel.setViewAdapter(hoursAdapter);
        hoursWheel.setVisibleItems(3);
        minutesWheel.setVisibleItems(3);
        secondsWheel.setVisibleItems(3);
        int layoutId = showSecond ? R.layout.view_time_wheel_item_center:R.layout.view_time_wheel_item_right;
        minutesAdapter = new TimeWheelAdapter(getContext(), minutesArray,layoutId);
//        minutesAdapter = new NumericWheelAdapter(getContext(),0,59);
        minutesWheel.setViewAdapter(minutesAdapter);
    }


    void clearWheelShadowAndBg(WheelView wheelView) {
        wheelView.setDrawShadows(false);
        wheelView.setWheelBackground(R.color.transparent);
        wheelView.setWheelForeground(R.color.transparent);
    }

    void onSelcet() {
        if (listener == null) {
            return;
        }
        DateTime dateTime = getWheelSelectionDatetime();
        listener.onDateTimeSelected(dateTime.getMilliseconds(TimeZone.getDefault()));
    }

    public String[] generateDays() {
        DateTime now = DateTime.now(TimeZone.getDefault());
        String[] days = new String[3];
        days[0] = "今天";
        days[1] = "明天";
        DateTime dateTime = now.plusDays(2);
        days[2] = dateTime.format("MM月|DD日");
        return days;
    }



    public void setTimeWheelListener(TimeWheelListener listener) {
        this.listener = listener;
    }



    private void setWheelSelection(DateTime dateTime) {
        selectFromProgram = true;

        String selectHour = dateTime.format("hh时");
        String selectMinute = dateTime.format("MM分");
        String selectSecond = dateTime.format("ss秒");
/*
        String rawMinute = String.valueOf(dateTime.getMinute());
        if (rawMinute.length()==1){
            selectMinute = "0"+rawMinute+"分";
        }else {
            selectMinute = rawMinute+"分";
        }
*/

        if (showSecond) {
            for (int i = 0; i < daysAdapter.getArray().length; i++) {
                if (selectSecond.equals(daysAdapter.getArray()[i])) {
                    secondsWheel.setCurrentItem(i);
                    break;
                }
            }
        }

        for (int i = 0; i < hoursAdapter.getArray().length; i++) {
            if (selectHour.equals(hoursAdapter.getArray()[i])) {
                hoursWheel.setCurrentItem(i);
                break;
            }
        }

     /*   for (int i = 0; i < minutesAdapter.getArray().length; i++) {
            if (selectMinute.equals(minutesAdapter.getArray()[i])) {
                minutesWheel.setCurrentItem(i);
                break;
            }
        }*/

        selectFromProgram = false;
    }
}
