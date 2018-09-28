package com.polotin.daixu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.polotin.daixu.R;
import com.polotin.daixu.presenter.ILoginPresenter;
import com.polotin.daixu.presenter.LoginPresenter;
import com.polotin.daixu.values.Constant;

public class LoginActivity extends Activity implements ILoginView, View.OnClickListener {
    public final static String ID_KEY = "key";
    public final static String BUNDLE_KEY = "bundle";
    public final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;

    static LoginActivity instance;
    ILoginPresenter loginPresenter;
    Button loginBtn;
    EditText editTextId;
    EditText editTextPwd;
    TextView regLink;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViews();
        initData();
        setListeners();
    }

    public void findViews() {
        loginBtn = (Button) findViewById(R.id.id_btn_login);
        editTextId = (EditText) findViewById(R.id.id_edit_text_id);
        editTextPwd = (EditText) findViewById(R.id.id_edit_txt_pwd);
        regLink = (TextView) findViewById(R.id.id_link_reg);
        progressBar = (ProgressBar) findViewById(R.id.id_pb_login);
    }

    public void setListeners() {
        loginBtn.setOnClickListener(this);
        regLink.setOnClickListener(this);
    }

    public void initData() {
        instance = this;
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_login:
                loginPresenter.login(editTextId.getText().toString(), editTextPwd.getText().toString());
                break;
            case R.id.id_link_reg:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ID_KEY, editTextId.getText().toString());
                intent.putExtra(BUNDLE_KEY, bundle);
                startActivity(intent);
                break;
        }
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
    public void onLoginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEmptyId() {
        Toast.makeText(this, Constant.MSG_EMPTY_ID, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEmptyPassord() {
        Toast.makeText(this, Constant.MSG_EMPTY_PASSWORD, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, BACK_PRESSED_TIP , Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
