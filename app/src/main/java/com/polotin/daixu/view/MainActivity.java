package com.polotin.daixu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polotin.daixu.R;
import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.presenter.IMainPresenter;
import com.polotin.daixu.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener{
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;

    private ListView listView;
    private FloatingActionButton fab;
    private MyAdapter myAdapter;
    private List<Plan> planList;
    private IMainPresenter iMainPresenter;
    public static MainActivity instance;

    private String phoneNumber;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        LoginActivity.instance.finish();
//        ValidateActivity.instance.finish();

        bindViews();
        initData();
        setListeners();
    }

    public void bindViews(){
        fab = findViewById(R.id.id_fab_edit);
        listView = findViewById(R.id.id_main_list);
    }

    public void refreshList() {
        iMainPresenter.getPlanListByPhoneNumber(phoneNumber);
    }

    public void initData(){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO", 0);

        phoneNumber = sharedPreferences.getString("phoneNumber", null);
        userName = sharedPreferences.getString("userName", null);

        iMainPresenter = new MainPresenter(this);
        iMainPresenter.getPlanListByPhoneNumber(phoneNumber);
    }

    public void setListeners(){
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_fab_edit:
                Intent intent = new Intent(MainActivity.this, AddPlanActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                this.startActivity(intent);
                break;
        }
    }

    @Override
    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
        myAdapter = new MyAdapter(this, this.planList);
        listView.setAdapter(myAdapter);
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<Plan> planList;

        public MyAdapter(Context context, List<Plan> planList) {
            super();
            mInflater = LayoutInflater.from(context);
            this.planList = planList;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty();
        }

        @Override
        public int getCount() {
            return planList.size();
        }

        @Override
        public Object getItem(int position) {
            return planList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.main_list_item, null);
                holder.tvContent = convertView.findViewById(R.id.id_tv_content);
                holder.tvHours = convertView.findViewById(R.id.id_tv_hours);
                holder.tvLocation = convertView.findViewById(R.id.id_tv_location);
                holder.tvDate = convertView.findViewById(R.id.id_tv_date);
                holder.tvStart = convertView.findViewById(R.id.id_tv_start);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvContent.setText("内容：" + planList.get(position).getContent());
            holder.tvLocation.setText("地点：" + planList.get(position).getPosition());
            holder.tvHours.setText("时长：" + planList.get(position).getHours());
            holder.tvStart.setText("状态：" + (planList.get(position).isStartFlag() ? "已完成" : "未完成"));
            holder.tvDate.setText("创建时间" + planList.get(position).getCreatedAt());
            return convertView;
        }

        public final class ViewHolder{
            public TextView tvContent;
            public TextView tvHours;
            public TextView tvLocation;
            public TextView tvDate;
            public TextView tvStart;
        }
    }



















    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis() - exitTime > 2000){
                Toast.makeText(this, BACK_PRESSED_TIP, Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
