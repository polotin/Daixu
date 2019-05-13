package com.polotin.daixu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.polotin.daixu.R;
import com.polotin.daixu.entity.Plan;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;

    private ListView listView;
    private MyAdapter myAdapter;
    private List<Plan> planList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginActivity.instance.finish();
        ValidateActivity.instance.finish();

        bindViews();
        initData();
        setListeners();
    }

    public void bindViews(){
        listView = findViewById(R.id.id_main_list);
    }

    public void initData(){
    }

    public void setListeners(){

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
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
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
