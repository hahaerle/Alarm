package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.tools.LocalPreference;
import com.lenote.alarmstar.tools.Lunar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wxy on 2015/7/18.
 */
@EViewGroup(R.layout.view_actionbar_home)
public class HomeActionBar extends LinearLayout {
    public interface IMenuClickListener{
        void onMenuSetting();
        void onMenuAdd();
    }
    IMenuClickListener listener;
    @ViewById
    TextView day;
    @ViewById
    TextView yearmonth;
    @ViewById
    TextView week;
    @ViewById
    ImageView menuSetting;
    @ViewById
    ImageView menuAdd;

    public void setListener(IMenuClickListener listener) {
        this.listener = listener;
    }

    public HomeActionBar(Context context) {
        super(context);
    }

    public HomeActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HomeActionBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @AfterViews
    void init(){
        // 创建 Calendar 对象
        Calendar calendar = Calendar.getInstance();
        // 初始化 Calendar 对象，但并不必要，除非需要重置时间
        calendar.setTime(new Date());

        // 重置 Calendar 显示当前时间
        calendar.setTime(new Date());
        String str = (new SimpleDateFormat("yyyy年MM月")).format(calendar.getTime());
        Lunar lunar = new Lunar(calendar);

        this.yearmonth.setText(str+ (LocalPreference.Setting.IsShowLunar()?" "+lunar:""));
        // 本月第 N 天

        this.week.setText(getWeekDay(calendar));
    }

    private String getWeekDay(Calendar calendar) {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        this.day.setText(getDay(dayOfMonth));
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK);
        switch (weekDay){
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";
            default:
                return "";
        }
    }

    private String getDay(int day_of_month) {
        if(day_of_month>9){
            return String.valueOf(day_of_month);
        }else {
            return "0"+day_of_month;
        }
    }

    @Click
    void menuSetting(){
        if(listener!=null){
            listener.onMenuSetting();
        }
    }
    @Click
    void menuAdd(){
        if(listener!=null){
            listener.onMenuAdd();
        }
    }
}
