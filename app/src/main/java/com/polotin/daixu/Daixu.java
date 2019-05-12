package com.polotin.daixu;

import android.app.Application;

import com.xuexiang.xui.XUI;

import cn.bmob.v3.Bmob;

public class Daixu extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initUI();
        initBomb();
    }

    private void initUI() {
        XUI.init(this);
        XUI.debug(BuildConfig.DEBUG);
    }

    private void initBomb(){
        Bmob.initialize(this, "82938cac13f55315f04b3de5cb276d77");
    }
}
