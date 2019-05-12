package com.polotin.daixu.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.polotin.daixu.CustomView.MyEditTextView;
import com.polotin.daixu.DaixuMessage;
import com.polotin.daixu.R;
import com.polotin.daixu.entity.User;
import com.polotin.daixu.presenter.ILoginPresenter;
import com.polotin.daixu.presenter.LoginPresenter;
import com.polotin.daixu.utils.CheckPermisson;
import com.polotin.daixu.utils.TouchEventUtils;
import com.polotin.daixu.values.Constant;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity implements ILoginView, View.OnClickListener {
    //    public final static String ID_KEY = "key";
//    public final static String BUNDLE_KEY = "bundle";
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;

    public static LoginActivity instance;
    ILoginPresenter loginPresenter;
    EditText editTextPhoneNumber;
    EditText editTextPassword;
    MaterialButton btnLogin;
    TextView tvRegisterLink;
    ProgressBar progressBar;
    MyHandler myHandler;

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
        btnLogin =  findViewById(R.id.id_btn_login);
        editTextPhoneNumber =  findViewById(R.id.id_et_phone_number);
        editTextPassword =  findViewById(R.id.id_et_pwd);
        progressBar =  findViewById(R.id.id_pb_login);
        tvRegisterLink = findViewById(R.id.tv_registetr_link);
    }

    public void setListeners() {
        btnLogin.setOnClickListener(this);
    }

    public void initData() {
        loginPresenter = new LoginPresenter(this);
        myHandler = new MyHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_login:
                loginPresenter.doLogin(editTextPhoneNumber.getText().toString(), editTextPassword.getText().toString(), myHandler);
                break;
        }
    }

    public class MyHandler extends Handler {
        LoginActivity loginActivity;

        public MyHandler(LoginActivity loginActivity) {
            this.loginActivity = loginActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DaixuMessage.LOGIN_SUCCESS:
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    break;
                case DaixuMessage.LOGIN_FAILED:
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    break;
                case DaixuMessage.PHONE_OR_PWD_ERROR:
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

//
//    @Override
//    public void sendMessage(){
//        btnLogin.callOnClick();
//    }
//
//    @Override
//    public void onCodeValid(){
//        Intent intent = new Intent(LoginActivity.this, ValidateActivity.class);
//        startActivity(intent);
//    }
//
//    @Override
//    public void showProgressBar() {
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideProgressBar() {
//        progressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onValidationCodeReceived(){
//        Intent intent = new Intent(LoginActivity.this, ValidateActivity.class);
//        startActivity(intent);
//    }
//
//    @Override
//    public void sendMessageResult(String result) {
//        hideProgressBar();
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//    }

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

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(ev.getAction() == MotionEvent.ACTION_DOWN){
//            View v = getCurrentFocus();
//            if(TouchEventUtils.isShouldHideKeyboard(v, ev)){
//                TouchEventUtils.hideKeyboard(v.getWindowToken(), this);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
