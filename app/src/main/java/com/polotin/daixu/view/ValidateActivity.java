package com.polotin.daixu.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.polotin.daixu.R;
import com.polotin.daixu.utils.TouchEventUtils;

public class ValidateActivity extends Activity implements IValidateView, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
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
