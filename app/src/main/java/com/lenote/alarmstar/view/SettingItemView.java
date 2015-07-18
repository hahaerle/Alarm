package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenote.alarmstar.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by shanerle on 2015/7/16.
 */
@EViewGroup(R.layout.view_setting_item)
public class SettingItemView extends LinearLayout {


    @ViewById
    ImageView itemLogo;
    @ViewById
    TextView itemName;
    private Drawable logo;
    private String nameValue;

    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context,attrs);
    }

    @AfterViews
    void init(){
        setName(nameValue);
        setLogo(logo);
    }

    private void setName(String nameValue) {
        this.itemName.setText(nameValue);
    }

    private void setLogo(Drawable logo) {
        itemLogo.setImageDrawable(logo);
    }

    private void initAttr(Context context,AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.SettingItem);
        try {
            if(a.hasValue(R.styleable.SettingItem_setting_name)) {
                nameValue = a.getString(R.styleable.SettingItem_setting_name);
            }
            if(a.hasValue(R.styleable.SettingItem_item_logo)) {
                logo = a.getDrawable(R.styleable.SettingItem_item_logo);
            }
        } finally {
            a.recycle();
        }
    }


}
