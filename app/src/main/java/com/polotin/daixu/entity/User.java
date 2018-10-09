package com.polotin.daixu.entity;

import java.io.Serializable;

public class User implements Serializable{
    private String phone;
    private String userName;
    private String loginTime;

    public User(String phone, String userName, String loginTime) {
        this.phone = phone;
        this.userName = userName;
        this.loginTime = loginTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
