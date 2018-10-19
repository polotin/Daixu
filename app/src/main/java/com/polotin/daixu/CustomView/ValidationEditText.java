package com.polotin.daixu.CustomView;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ValidationEditText extends AppCompatEditText {
    private long lastTime = 0;

    public ValidationEditText(Context context) {
        super(context);
    }

    public ValidationEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValidationEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        this.setSelection(this.getText().length());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime < 500) {
                    lastTime = currentTime;
                    return true;
                } else {
                    lastTime = currentTime;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
