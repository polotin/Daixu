package com.polotin.daixu.model;

import com.polotin.daixu.listener.OnLoginFinishedListener;

public interface IUserModel {
    public void validLogin(String id, String pwd, OnLoginFinishedListener listener);
}
