package com.polotin.daixu.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polotin.daixu.R;
import com.polotin.daixu.presenter.IRegisterPresenter;
import com.polotin.daixu.presenter.RegisterPresenter;
import com.polotin.daixu.utils.MessageUtil;

public class RegisterActivity extends Activity implements IRegisterView, View.OnClickListener {
    final static String BACK_PRESSED_TIP = "再按一次退让程序";
    long exitTime = 0;
    String id = "";
    static RegisterActivity instance;

    IRegisterPresenter iRegisterPresenter;

    EditText idEditText;
    Button verifyBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Bundle bundle = getIntent().getBundleExtra(LoginActivity.BUNDLE_KEY);
        id = bundle.getString(LoginActivity.ID_KEY);
        LoginActivity.instance.finish();

        findViews();
        initData();
        setListeners();
    }

    public void findViews() {
        idEditText = (EditText) findViewById(R.id.id_reg_edit_text_id);
        verifyBtn = (Button) findViewById(R.id.id_btn_verify);
    }

    public void setListeners() {
        verifyBtn.setOnClickListener(this);
    }

    public void initData() {
        instance = this;
        iRegisterPresenter = new RegisterPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_verify:
                iRegisterPresenter.sendMessage(idEditText.getText().toString());
                break;
        }
    }

    @Override
    public void sendMessageResult(String result) {
        Toast.makeText(instance, result, Toast.LENGTH_SHORT).show();
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
