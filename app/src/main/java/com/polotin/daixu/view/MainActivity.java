package com.polotin.daixu.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polotin.daixu.R;
import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.presenter.IMainPresenter;
import com.polotin.daixu.presenter.MainPresenter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener{
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;

    private ListView listView;
    private FloatingActionButton fab;
    private MyAdapter myAdapter;
    private List<Plan> planList;
    private static IMainPresenter iMainPresenter;
    public static MainActivity instance;

    private static String phoneNumber;
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

    public static void refreshList() {
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
                intent.putExtra("opertion", "add");
                this.startActivity(intent);
                break;
        }
    }

    public void editPlan(Plan plan) {
        Intent intent = new Intent(MainActivity.this, AddPlanActivity.class);
        intent.putExtra("plan", plan.toString());
        intent.putExtra("operation", "edit");
        this.startActivity(intent);
    }

    public void startPlan(Plan plan) {
        iMainPresenter.startPlan(plan);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.main_list_item, null);
                holder.tvContent = convertView.findViewById(R.id.id_tv_content);
                holder.tvHours = convertView.findViewById(R.id.id_tv_hours);
                holder.tvLocation = convertView.findViewById(R.id.id_tv_location);
                holder.tvDate = convertView.findViewById(R.id.id_tv_date);
                holder.tvStart = convertView.findViewById(R.id.id_tv_start);
                holder.btnEdit = convertView.findViewById(R.id.id_btn_edit);
                holder.btnStart = convertView.findViewById(R.id.id_btn_start);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            Plan plan = planList.get(position);
            holder.tvContent.setText("内容：" + plan.getContent());
            holder.tvLocation.setText("地点：" + plan.getPosition());
            holder.tvHours.setText("时长：" + plan.getHours());
            String status = "未开始";
            if(plan.isStartFlag()) {
                holder.btnStart.setVisibility(View.INVISIBLE);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try{
                    Date nowDate = df.parse(df.format(new Date()));
                    Date startDate = df.parse(plan.getStartAt());
                    long diff = nowDate.getTime() - startDate.getTime() -(long)(plan.getHours()*60*60*1000) ;
                    if(diff > 0) {
                        status = "已结束";
                    } else {
                        long diff1 = startDate.getTime()+(long)(plan.getHours()*60*60*1000)-nowDate.getTime();
                        long days = diff1 / (1000 * 60 * 60 * 24);
                        long hours = (diff1-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                        long minutes = (diff1-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                        status = "剩余" + days + "天" + hours + "小时" + minutes + "分";
                    }
                }catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                holder.btnStart.setVisibility(View.VISIBLE);
            }
            holder.tvStart.setText("状态：" + status);
            holder.tvDate.setText("创建时间" + planList.get(position).getCreatedAt());
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.instance.editPlan(planList.get(position));
                }
            });
            holder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.instance.startPlan(planList.get(position));                }
            });
            return convertView;
        }

        public final class ViewHolder{
            public TextView tvContent;
            public TextView tvHours;
            public TextView tvLocation;
            public TextView tvDate;
            public TextView tvStart;
            public Button btnEdit;
            public Button btnStart;
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
