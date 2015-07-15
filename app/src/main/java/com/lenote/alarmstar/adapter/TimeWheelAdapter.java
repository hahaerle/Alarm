package com.lenote.alarmstar.adapter;

import android.content.Context;
import com.lenote.alarmstar.R;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * Created by lenote on 2015/7/14.
 */
public class TimeWheelAdapter extends AbstractWheelTextAdapter {
    private final String[] array;

    public TimeWheelAdapter(Context context,String[] array, int layoutId) {
        super(context, layoutId, NO_RESOURCE);
        setItemTextResource(R.id.timeItem);
        this.array = array;
    }

    public String[] getArray() {
        return array;
    }

    @Override
    public int getItemsCount() {
        return array.length;
    }

    @Override
    public CharSequence getItemText(int index) {
        return array[index];
    }
}
