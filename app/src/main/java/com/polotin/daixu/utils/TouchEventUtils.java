package com.polotin.daixu.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class TouchEventUtils {
    public static boolean isShouldHideKeyboard(View v, MotionEvent event){
        if(v != null && (v instanceof EditText)){
            int[] outLocation = {0,0};
            v.getLocationInWindow(outLocation);
            int left = outLocation[0];
            int top = outLocation[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }

    public static void hideKeyboard(IBinder token, Activity activity){
        if(token != null){
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
