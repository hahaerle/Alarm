package com.lenote.alarmstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lenote.alarmstar.R;
import com.lenote.alarmstar.dao.AlarmDao;
import com.lenote.alarmstar.moudle.AlarmObj;
import com.lenote.alarmstar.view.AlarmItemView;
import com.lenote.alarmstar.view.AlarmItemView_;
import com.lenote.alarmstar.view.CreateAlarmMenu;
import com.lenote.alarmstar.view.HomeActionBar;
import com.umeng.update.UmengUpdateAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends BaseActivity {


    private static final int CREATE_ALARM_SUCCESS = 1001;
    @ViewById
    TextView coundDown;
    @ViewById
    TextView count_down_alarm_name;
    @ViewById
    ListView listview;
    @ViewById
    HomeActionBar action_bar;
    @ViewById
    CreateAlarmMenu menu_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
    }

    AlarmAdapter adapter;
    @AfterViews
    void init(){
        adapter=new AlarmAdapter(this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlarmObj alarmObj = (AlarmObj)view.getTag();
                AlarmEditActivity_.intent(MainActivity.this).alarmObj(alarmObj).startForResult(CREATE_ALARM_SUCCESS);
            }
        });
        action_bar.setListener(new HomeActionBar.IMenuClickListener() {
            @Override
            public void onMenuSetting() {
                AlarmSettingActivity_.intent(MainActivity.this).start();
            }

            @Override
            public void onMenuAdd() {
                menu_bar.setVisibility(View.VISIBLE);
            }
        });
        menu_bar.setListener(new CreateAlarmMenu.IButtonClickListener() {
            @Override
            public void onCreateWakeup() {
                menu_bar.setVisibility(View.GONE);
                AlarmEditActivity_.intent(MainActivity.this).startForResult(CREATE_ALARM_SUCCESS);
            }

            @Override
            public void onCreateNoraml() {
                menu_bar.setVisibility(View.GONE);
                AlarmEditActivity_.intent(MainActivity.this).startForResult(CREATE_ALARM_SUCCESS);
            }

            @Override
            public void onCreateCoundDown() {
                menu_bar.setVisibility(View.GONE);
                AlarmEditActivity_.intent(MainActivity.this).startForResult(CREATE_ALARM_SUCCESS);
            }

            @Override
            public void onClose() {
                menu_bar.setVisibility(View.GONE);
            }
        });
        loadData();
    }
    @OnActivityResult(CREATE_ALARM_SUCCESS)
    void createAlarmSuccess(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            loadData();
        }
    }
    @Background
    void loadData() {
        List<AlarmObj> list=AlarmDao.getAlarmList(this);
        updateUI(list);
    }
    @UiThread
    void updateUI(List<AlarmObj> list) {
        adapter.setData(list);
        count_down_alarm_name.setText(getCountDownName());
        coundDown.setText(getCountDownText());
    }

    private String getCountDownName() {
        return getResources().getString(R.string.home_count_down,"起床");
    }

    private SpannableString  getCountDownText() {
        SpannableString ss = new SpannableString("19时03分");
        ss.setSpan(new RelativeSizeSpan(0.3f), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(0.3f), 5, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @OptionsItem(R.id.action_settings)
    void setting(){

    }

    public class AlarmAdapter extends BaseAdapter{
        private final Context mContext;
        private List<AlarmObj> data=new ArrayList<>();

        public AlarmAdapter(Context context){
            this.mContext=context;
        }

        public void setData(List<AlarmObj> data) {
            this.data.clear();
            if (data!=null) {
                this.data.addAll(data);
            }
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
            itemView.bindData((AlarmObj)getItem(position));
            return itemView;
        }
    }
}
