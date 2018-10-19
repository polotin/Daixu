package com.polotin.daixu.view;

public interface ILoginView {

    public void showProgressBar();

    public void hideProgressBar();

    public void sendMessage();

    public void sendMessageResult(String result);

    public void onValidationCodeReceived();

    //验证码未过期
    public void onCodeValid();
}
