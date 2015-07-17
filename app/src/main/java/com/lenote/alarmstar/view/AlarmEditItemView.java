package com.lenote.alarmstar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenote.alarmstar.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by shanerle on 2015/7/16.
 */
@EViewGroup(R.layout.view_edit_alarm_item)
public class AlarmEditItemView extends LinearLayout {

    @ViewById
    TextView name;
    @ViewById
    TextView value;
    @ViewById
    TextView arrow;
    private String itemValue;
    private String itemName;

    public AlarmEditItemView(Context context) {
        super(context);
    }

    public AlarmEditItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public AlarmEditItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlarmEditItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context,attrs);
    }

    private void initAttr(Context context,AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.EditItem);
        try {
            boolean clickable = a.getBoolean(R.styleable.EditItem_item_clickable, true);
            setClickable(clickable);
            if(a.hasValue(R.styleable.EditItem_item_name)) {
                itemName = a.getString(R.styleable.EditItem_item_name);
                setName(itemName);
            }
            if(a.hasValue(R.styleable.EditItem_item_value)) {
                itemValue = a.getString(R.styleable.EditItem_item_value);
                setValue(itemValue);
            }
        } finally {
            a.recycle();
        }
    }
    public void setName(String name) {
        this.name.setText(name);
    }

    public void setValue(String value) {
        this.value.setText(value);
    }
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        resetClickable();
    }
    private void resetClickable() {
        if (arrow != null) {
            if (isClickable()) {
                arrow.setVisibility(VISIBLE);
            } else {
                arrow.setVisibility(GONE);
            }
        }
    }

    public String getItemValue() {
        return itemValue;
    }

    public String getItemName() {
        return itemName;
    }
}
