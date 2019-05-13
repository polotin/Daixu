package com.polotin.daixu.model;

import android.os.Handler;

import com.polotin.daixu.entity.User;
import com.polotin.daixu.listener.OnLoginFinishedListener;
import com.polotin.daixu.view.ILoginView;

public interface IUserModel {
    void validLogin(String id, String pwd, OnLoginFinishedListener listener);

    void sendMsg(String phoneNumber);

    void doRegister(final User user, final Handler handler);

    void doLogin(String phoneNumber, final String password, final Handler handler, final ILoginView iLoginView);
}
