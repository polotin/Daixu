package com.polotin.daixu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.polotin.daixu.CustomView.ValidationCodeView;
import com.polotin.daixu.R;
import com.polotin.daixu.utils.TouchEventUtils;

public class ValidateActivity extends Activity implements IValidateView, View.OnClickListener {

    private ValidationCodeView validationCodeView;

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
    }

    public void initData() {

    }

    public void setListeners() {
        validationCodeView.setInputListener(new ValidationCodeView.MyInputListener() {
            @Override
            public void inputCompleted() {
                Intent intent = new Intent(ValidateActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void invalidCharacter() {
//                Toast.makeText(ValidateActivity.this, "请输入数字验证码", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
