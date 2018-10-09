package com.polotin.daixu.model;

import com.polotin.daixu.listener.OnLoginFinishedListener;
import com.polotin.daixu.values.Constant;

public class UserModel implements IUserModel {

    public void validLogin(String id, String pwd, OnLoginFinishedListener listener){
        int result = 0;

        //登录验证
        result = Constant.LOGIN_SUCCESS;

        if(result == Constant.LOGIN_SUCCESS){
        }
    }

    public void sendMsg(String phoneNumber){

    }
}
