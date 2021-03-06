package com.polotin.daixu.entity;

import com.google.gson.Gson;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Plan extends BmobObject {
    private String phoneNumber;
    private String position;
    private String content;
    private float hours;
    private String startAt;
    private boolean startFlag;

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStartFlag() {
        return startFlag;
    }

    public void setStartFlag(boolean startFlag) {
        this.startFlag = startFlag;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
