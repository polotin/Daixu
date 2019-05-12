package com.polotin.daixu.presenter;

import android.os.Handler;

public interface ILoginPresenter {

//    public void sendMsg(String phoneNumber);

    void doLogin(String phoneNumber, String password, final Handler handler);
}
