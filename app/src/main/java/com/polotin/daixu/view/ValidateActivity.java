package com.polotin.daixu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polotin.daixu.CustomView.ValidationCodeView;
import com.polotin.daixu.R;
import com.polotin.daixu.presenter.IValidatePresenter;
import com.polotin.daixu.presenter.ValidatePresenter;
import com.polotin.daixu.utils.CacheUtil;
import com.polotin.daixu.utils.TouchEventUtils;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.values.Msg;

public class ValidateActivity extends Activity implements IValidateView, View.OnClickListener {

    private ValidationCodeView validationCodeView;
    private TextView msgTimeTextView;
    private EditText validationCodeEditText;
    private TextView resendBtn;
    private IValidatePresenter validatePresenter;
    public static ValidateActivity instance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        findViews();
        initData();
        setListeners();
    }

    public void findViews() {
        validationCodeView = (ValidationCodeView) findViewById(R.id.id_validation_code);
        msgTimeTextView = (TextView) findViewById(R.id.id_msg_time);
        validationCodeEditText = (EditText) findViewById(R.id.et_validation);
        resendBtn = (TextView) findViewById(R.id.id_resend_txt);
    }

    public void initData() {
        validatePresenter = new ValidatePresenter(this);
        instance = this;
        countDown(System.currentTimeMillis() - Long.valueOf(CacheUtil.getValidationCode().getCodeTime()));
    }

    public void countDown(long millisInFuture){
        CountDownTimer timer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                msgTimeTextView.setText("" + millisUntilFinished / 1000 + "s后可重新获取");
            }

            @Override
            public void onFinish() {
                msgTimeTextView.setVisibility(View.GONE);
                resendBtn.setVisibility(View.VISIBLE);
            }
        };
    }

    public void setListeners() {
        validationCodeView.setInputListener(new ValidationCodeView.MyInputListener() {
            @Override
            public void inputCompleted() {
                validatePresenter.checkValidationCode(validationCodeEditText.getText().toString());
            }

            @Override
            public void invalidCharacter() {
//                Toast.makeText(ValidateActivity.this, "请输入数字验证码", Toast.LENGTH_SHORT).show();
            }
        });

        resendBtn.setOnClickListener(this);
    }

    @Override
    public void handleMessage(int msg) {
        switch (msg) {
            case Msg.CHECK_VALIDATION_TRUE:
                Intent intent = new Intent(ValidateActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case Msg.CHECK_VALIDATION_FALSE:
                Toast.makeText(ValidateActivity.this, Constant.INVALID_CODE, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void sendMessageResult(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_resend_txt:
                validatePresenter.sendMessage();
                msgTimeTextView.setVisibility(View.VISIBLE);
                resendBtn.setVisibility(View.GONE);
                countDown(System.currentTimeMillis() - Long.valueOf(CacheUtil.getValidationCode().getCodeTime()));
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View v = getCurrentFocus();
            if (TouchEventUtils.isShouldHideKeyboard(v, ev)) {
                TouchEventUtils.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
