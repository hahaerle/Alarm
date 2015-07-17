package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.adapter.TimeWheelAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.lang.ref.WeakReference;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;

/**
 * Created by shanerle on 2015/7/16.
 */
@EViewGroup(R.layout.view_time_wheel)
public class TimeSelectWheel extends LinearLayout {
    public static final int MSG_WHEEL_CHANGED = 1;
    private boolean minTimeRestriction;
    private boolean selectFromProgram;

    public interface TimeWheelListener {
        void onDateTimeSelected(long timeStamp);
    }

    public static final int TYPE_ALL = 0;
    public static final int TYPE_FULL_DAY = 3;

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

    int type;
    long timeStamp;
    boolean showDate;
    private TimeWheelListener listener;
    private long lastChange;

    @ViewById(R.id.days)
    WheelView daysWheel;
    @ViewById(R.id.hours)
    WheelView hoursWheel;
    @ViewById(R.id.mins)
    WheelView minutesWheel;

    @StringArrayRes(R.array.go_all_hours)
    String[] allHours;
    @StringArrayRes(R.array.full_day_hours)
    String[] fullDayHours;

    @StringArrayRes(R.array.minus)
    String[] minutesArray;

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
        clearWheelShadowAndBg(daysWheel);
        clearWheelShadowAndBg(hoursWheel);
        clearWheelShadowAndBg(minutesWheel);

        daysWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mUIHandler.sendEmptyMessageDelayed(MSG_WHEEL_CHANGED,MIN_SPAN);
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
    }

    private DateTime getWheelSelectionDatetime() {
        int year, month, day, hour, minute;

        DateTime now = DateTime.now(TimeZone.getDefault());
        year = now.getYear();
        month = now.getMonth();
        day = now.getDay();


        if (showDate) {
            try {
                String selectDay = daysAdapter.getItemText(
                        daysWheel.getCurrentItem()).toString();
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

    public void prepareData(int type, long timeStamp, boolean showDate) {
        prepareData(type, timeStamp, showDate, false);
    }


    public void prepareData(int type, long timeStamp, boolean showDate,
                            boolean minTimeRestriction) {
        this.type = type;
        this.timeStamp = timeStamp;
        this.showDate = showDate;
        this.minTimeRestriction = minTimeRestriction;

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
        if (showDate) {
            String[] daysArray = generateDays();
            daysAdapter = new TimeWheelAdapter(getContext(), daysArray,R.layout.view_time_wheel_item_left);
            daysWheel.setViewAdapter(daysAdapter);

            daysWheel.setVisibility(VISIBLE);
        } else {
            daysWheel.setVisibility(GONE);
        }

        String[] hoursArray = getHoursByType();
        int layoutId = showDate? R.layout.view_time_wheel_item_center:R.layout.view_time_wheel_item_left;
        hoursAdapter = new TimeWheelAdapter(getContext(),hoursArray,layoutId);
        hoursWheel.setViewAdapter(hoursAdapter);

        minutesAdapter = new TimeWheelAdapter(getContext(), minutesArray,R.layout.view_time_wheel_item_right);
        minutesWheel.setViewAdapter(minutesAdapter);
    }


    void clearWheelShadowAndBg(WheelView wheelView) {
        wheelView.setDrawShadows(false);
        wheelView.setWheelBackground(R.color.transparent);
//        wheelView.setWheelForeground(R.drawable.time_wheel_fg);
    }

    void ok() {
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

    /**
     * 1.默认值为当前时间的半小时后，取10分钟整（示例：当前时间08:13，则默认值为8:50）
     * <p/>
     * 2.上班用车只允许选择 06:00-12:00，下班用车只允许选择12:10-22:50
     * <p/>
     * 3.当用户选择了不允许的时间范围，则控件自动滚动到未来最近的一个允许时间
     */


    public String[] getHoursByType() {
        String[] hours;
        switch (type) {
            case TYPE_ALL:
            default:
                hours = allHours;
                break;
            case TYPE_FULL_DAY:
                hours = fullDayHours;
        }

        return hours;
    }


    public void setTimeWheelListener(TimeWheelListener listener) {
        this.listener = listener;
    }



    private void setWheelSelection(DateTime dateTime) {
        selectFromProgram = true;
        DateTime now = DateTime.now(TimeZone.getDefault());
        int dx = now.numDaysFrom(dateTime);

        String selectDay;
        if (dx == 0) {
            selectDay = "今天";
        } else if (dx == 1) {
            selectDay = "明天";
        } else {
            selectDay = dateTime.format("MM月DD日");
        }

        String selectHour = dateTime.format("hh时");
        String rawMinute = String.valueOf(dateTime.getMinute());
        String selectMinute;
        if (rawMinute.length()==1){
            selectMinute = "0"+rawMinute+"分";
        }else {
            selectMinute = rawMinute+"分";
        }

        if (showDate) {
            for (int i = 0; i < daysAdapter.getArray().length; i++) {
                if (selectDay.equals(daysAdapter.getArray()[i])) {
                    daysWheel.setCurrentItem(i);
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

        for (int i = 0; i < minutesAdapter.getArray().length; i++) {
            if (selectMinute.equals(minutesAdapter.getArray()[i])) {
                minutesWheel.setCurrentItem(i);
                break;
            }
        }

        selectFromProgram = false;
    }
}
