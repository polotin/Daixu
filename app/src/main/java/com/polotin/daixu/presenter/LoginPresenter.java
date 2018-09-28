package com.polotin.daixu.presenter;

import android.os.Handler;
import android.os.Looper;

import com.polotin.daixu.listener.OnLoginFinishedListener;
import com.polotin.daixu.model.IUserModel;
import com.polotin.daixu.model.UserModel;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.view.ILoginView;


public class LoginPresenter implements ILoginPresenter, OnLoginFinishedListener {
    ILoginView iLoginView;
    IUserModel iUserModel;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        iUserModel = new UserModel();
    }

    public void login(String id, String pwd) {
        if (id != null && pwd != null && !id.trim().isEmpty() && !pwd.trim().isEmpty()) {
            iLoginView.showProgressBar();
            iUserModel.validLogin(id, pwd, this);
        } else if (id.isEmpty()) {
            iLoginView.hideProgressBar();
            iLoginView.onEmptyId();
        } else if (pwd.isEmpty()) {
            iLoginView.hideProgressBar();
            iLoginView.onEmptyPassord();
        }
    }

    @Override
    public void onLoginSuccess() {
        iLoginView.onLoginSuccess();
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onInternetError() {

    }
}
