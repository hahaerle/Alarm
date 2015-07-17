package com.lenote.alarmstar.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.dao.AlarmDao;
import com.lenote.alarmstar.session.AlarmSession;
import com.lenote.alarmstar.view.AlarmItemView;
import com.lenote.alarmstar.view.AlarmItemView_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends BaseActivity {


    @ViewById
    TextView coundDown;
    @ViewById
    ListView listview;
    AlarmAdapter adapter;
    @AfterViews
    void init(){
        adapter=new AlarmAdapter(this);
        listview.setAdapter(adapter);
        loadData();
    }
    @Background
    void loadData() {
        List<AlarmSession> list=AlarmDao.getAlarmList(this);
        updateUI(list);
    }
    @UiThread
    void updateUI(List<AlarmSession> list) {
        adapter.setData(list);
    }

    @OptionsItem(R.id.action_settings)
    void setting(){

    }

    public class AlarmAdapter extends BaseAdapter{
        private final Context mContext;
        private List<AlarmSession> data=new ArrayList<>();

        public AlarmAdapter(Context context){
            this.mContext=context;
        }

        public void setData(List<AlarmSession> data) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlarmItemView itemView;
            if(convertView==null){
                itemView= AlarmItemView_.build(mContext);
            }else{
                itemView=(AlarmItemView)convertView;
            }
            itemView.bindData((AlarmSession)getItem(position));
            return itemView;
        }
    }
}
