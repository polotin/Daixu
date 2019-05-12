package com.polotin.daixu.presenter;

import android.os.Handler;

import com.polotin.daixu.entity.User;

public interface IRegisterPresenter {
    public void doRegister(User user, Handler handler);

}
