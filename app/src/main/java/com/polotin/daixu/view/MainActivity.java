package com.polotin.daixu.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.polotin.daixu.R;

public class MainActivity extends AppCompatActivity {
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;

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

    }

    public void initData(){

    }

    public void setListeners(){

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
