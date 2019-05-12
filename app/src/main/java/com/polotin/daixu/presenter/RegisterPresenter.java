package com.polotin.daixu.presenter;

import android.os.Handler;

import com.polotin.daixu.entity.User;
import com.polotin.daixu.model.UserModel;

public class RegisterPresenter implements IRegisterPresenter {
    private UserModel userModel;

    public RegisterPresenter() {
        userModel = new UserModel();
    }

    @Override
    public void doRegister(User user, Handler handler) {
        userModel.doRegister(user, handler);
    }
}
