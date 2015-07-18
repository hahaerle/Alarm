package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.tools.Strings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by lenote on 2015/7/18.
 */
@EViewGroup(R.layout.view_actionbar_edit_alarm)
public class LogoActionBar extends RelativeLayout {
    @ViewById
    TextView titleView;
    @ViewById
    TextView menu;
    private String menuName;
    private String titleName;

    public interface IMenuClickListener{
        void onMenuClick();
    }
    IMenuClickListener listener;

    public void setListener(IMenuClickListener listener) {
        this.listener = listener;
    }

    public LogoActionBar(Context context) {
        super(context);
    }

    public LogoActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LogoActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LogoActionBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar);
        try {
            if(a.hasValue(R.styleable.ActionBar_title_name)) {
                titleName = a.getString(R.styleable.ActionBar_title_name);
            }
            if(a.hasValue(R.styleable.ActionBar_menu_name)) {
                menuName = a.getString(R.styleable.ActionBar_menu_name);
            }
        } finally {
            a.recycle();
        }
    }
    @AfterViews
    void init(){
        setTitle(titleName);
        setMenu(menuName);
    }
    void setTitle(String title){
        titleView.setText(title);
    }
    void setMenu(String menu){
        if(!Strings.isNullOrEmpty(menu)) {
            this.menu.setVisibility(VISIBLE);
            this.menu.setText(menu);
        }else {
            this.menu.setVisibility(GONE);
        }
    }
    @Click
    void menu(){
        if(listener!=null){
            listener.onMenuClick();
        }
    }

}
