package com.polotin.daixu.CustomView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.polotin.daixu.R;
import com.polotin.daixu.presenter.LoginPresenter;
import com.polotin.daixu.values.Msg;

public class MyEditTextView extends TextInputEditText implements View.OnFocusChangeListener, TextWatcher, TextView.OnEditorActionListener {
    //删除按钮
    private Drawable clearBtnDrawable;
    private boolean hasFocus;

    public MyEditTextView(Context context) {
        super(context);
        init();
    }

    public MyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public MyEditTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    private void init() {
        clearBtnDrawable = getCompoundDrawables()[2];
        if (clearBtnDrawable == null) {
            clearBtnDrawable = getResources().getDrawable(R.drawable.btn_clear);
        }
        clearBtnDrawable.setBounds(0, 0, 80, 80);

        setOnFocusChangeListener(this);
        setOnEditorActionListener(this);
        addTextChangedListener(this);
    }

    public void setClearBtnDrawable(boolean visible) {
        Drawable btnDrawable = visible ? clearBtnDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], btnDrawable, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = (event.getX() > (getWidth() - getTotalPaddingRight())) && (event.getX() < (getWidth() - getPaddingRight()));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus) {
            setClearBtnDrawable(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearBtnDrawable(this.getText().length() > 0);
        } else {
            setClearBtnDrawable(false);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || (event != null && event.getAction() == KeyEvent.KEYCODE_ENTER)) {
            LoginPresenter.handler.sendEmptyMessage(Msg.ACTION_SEND_MESSAGE);
            return true;
        }
        return false;
    }
}
