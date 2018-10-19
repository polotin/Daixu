package com.polotin.daixu.CustomView;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.polotin.daixu.R;

public class ValidationCodeView extends ConstraintLayout implements TextWatcher {

    private TextView[] textViews;
    private EditText editText;
    private static int MAX = 6;
    private String inputValidationStr;
    private MyInputListener inputListener;

    public ValidationCodeView(Context context) {
        this(context, null);
    }

    public ValidationCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValidationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = inflate(getContext(), R.layout.view_validate_code, this);
        textViews = new TextView[MAX];
        textViews[0] = (TextView) findViewById(R.id.tv_validation_1);
        textViews[1] = (TextView) findViewById(R.id.tv_validation_2);
        textViews[2] = (TextView) findViewById(R.id.tv_validation_3);
        textViews[3] = (TextView) findViewById(R.id.tv_validation_4);
        textViews[4] = (TextView) findViewById(R.id.tv_validation_5);
        textViews[5] = (TextView) findViewById(R.id.tv_validation_6);
        editText = (EditText) findViewById(R.id.et_validation);

        editText.setCursorVisible(false);
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        inputValidationStr = editText.getText().toString();
        for (int i = 0; i < MAX; i++) {
            if (i < inputValidationStr.length()) {
                textViews[i].setText(String.valueOf(inputValidationStr.charAt(i)));
            } else {
                textViews[i].setText("");
            }
        }

        if (inputListener != null) {
            if (inputValidationStr.length() == MAX) {
                inputListener.inputCompleted();
            }
        }
    }

    public void setInputListener(MyInputListener inputListener) {
        this.inputListener = inputListener;
    }

    public interface MyInputListener {
        void inputCompleted();

        void invalidCharacter();
    }

    public String getInputValidationStr() {
        return inputValidationStr;
    }
}
