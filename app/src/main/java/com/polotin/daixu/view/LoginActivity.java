package com.polotin.daixu.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.polotin.daixu.CustomView.MyEditTextView;
import com.polotin.daixu.R;
import com.polotin.daixu.presenter.ILoginPresenter;
import com.polotin.daixu.presenter.LoginPresenter;
import com.polotin.daixu.utils.CheckPermisson;
import com.polotin.daixu.utils.TouchEventUtils;
import com.polotin.daixu.values.Constant;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity implements ILoginView, View.OnClickListener {
    public final static String ID_KEY = "key";
    public final static String BUNDLE_KEY = "bundle";
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;

    public static LoginActivity instance;
    ILoginPresenter loginPresenter;
    EditText editTextPhoneNumber;
    MaterialButton btnSendMsg;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;
        CheckPermisson.checkPermission(this);
        findViews();
        initData();
        setListeners();
    }

    public void findViews() {
        btnSendMsg = (MaterialButton) findViewById(R.id.id_btn_send_msg);
        editTextPhoneNumber = (MyEditTextView) findViewById(R.id.id_et_phone_number);
        progressBar = (ProgressBar) findViewById(R.id.id_pb_login);
    }

    public void setListeners() {
        btnSendMsg.setOnClickListener(this);
    }

    public void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_send_msg:
                loginPresenter.sendMsg(editTextPhoneNumber.getText().toString());
                break;
        }
    }

    @Override
    public void sendMessage(){
        btnSendMsg.callOnClick();
    }

    @Override
    public void onCodeValid(){
        Intent intent = new Intent(LoginActivity.this, ValidateActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onValidationCodeReceived(){
        Intent intent = new Intent(LoginActivity.this, ValidateActivity.class);
        startActivity(intent);
    }

    @Override
    public void sendMessageResult(String result) {
        hideProgressBar();
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.CODE_INTERNET) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "获取到了权限");
            } else {
                Log.e(TAG, "没有获取到权限");
                Toast.makeText(this, "没有获取读取手机权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, BACK_PRESSED_TIP, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(TouchEventUtils.isShouldHideKeyboard(v, ev)){
                TouchEventUtils.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
