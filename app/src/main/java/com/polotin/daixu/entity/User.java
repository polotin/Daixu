package com.polotin.daixu.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject implements Serializable{
    private String phoneNumber;
    private String userName;
    private String password;

    public User() {
    }

    public User(String phoneNumber, String userName, String password) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
    }

    public User(String tableName, String phoneNumber, String userName, String password) {
        super(tableName);
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
