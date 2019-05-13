package com.polotin.daixu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.polotin.daixu.DaixuMessage;
import com.polotin.daixu.R;
import com.polotin.daixu.entity.User;
import com.polotin.daixu.presenter.IRegisterPresenter;
import com.polotin.daixu.presenter.RegisterPresenter;

import org.apache.commons.lang3.StringUtils;

import androidx.annotation.Nullable;

public class RegisterActivity extends Activity implements IRegisterView, View.OnClickListener {
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;
    private EditText etPhoneNumber;
    private EditText etUserName;
    private EditText etPwd;
    private EditText etPwdConfirm;
    private MaterialButton btnReg;
    private TextView tvLogin;
    private String phoneNumber;
    private String userName;
    private String pwd;
    private String pwdConfirm;
    private IRegisterPresenter iRegisterPresenter;
    private MyHandler myHandler;

    public static RegisterActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LoginActivity.instance != null) {
            LoginActivity.instance.finish();
        }
        setContentView(R.layout.activity_register);
        initView();
        initData();
    }

    void initView() {
        etPhoneNumber = findViewById(R.id.id_et_phone_number_reg);
        etUserName = findViewById(R.id.id_et_username);
        etPwd = findViewById(R.id.id_et_pwd);
        etPwdConfirm = findViewById(R.id.id_et_pwd_confirm);
        btnReg = findViewById(R.id.id_btn_register);
        tvLogin = findViewById(R.id.id_tv_login_link);
    }

    void initData() {
        instance = this;
        iRegisterPresenter = new RegisterPresenter();
        myHandler = new MyHandler(this);
        btnReg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    void processLogin() {
        phoneNumber = etPhoneNumber.getText().toString();
        userName = etUserName.getText().toString();
        pwd = etPwd.getText().toString();
        pwdConfirm = etPwdConfirm.getText().toString();
        if (StringUtils.isBlank(phoneNumber.replace(" ", ""))) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isBlank(userName.replace(" ", ""))) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isBlank(pwd.replace(" ", ""))) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isBlank(pwdConfirm.replace(" ", ""))) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
        } else if (!pwd.equals(pwdConfirm)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            iRegisterPresenter.doRegister(new User(phoneNumber, userName, pwd), myHandler);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_register:
                processLogin();
                break;
            case R.id.id_tv_login_link:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                this.startActivity(intent);
                break;
        }
    }

    public class MyHandler extends Handler {
        private RegisterActivity registerActivity;

        public MyHandler(RegisterActivity registerActivity) {
            this.registerActivity = registerActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DaixuMessage.REGISTER_SUCCESS:
                    Toast.makeText(registerActivity, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(intent);
                    break;
                case DaixuMessage.REGISTER_FAIL:
                    Toast.makeText(registerActivity, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
                case DaixuMessage.PHONE_EXIST:
                    Toast.makeText(registerActivity, "该手机号已被注册", Toast.LENGTH_SHORT).show();
                    break;
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
}
