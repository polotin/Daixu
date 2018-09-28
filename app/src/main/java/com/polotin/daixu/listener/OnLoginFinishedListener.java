package com.polotin.daixu.listener;

public interface OnLoginFinishedListener {

    void onLoginSuccess();

    void onLoginFailed();

    void onInternetError();
}
