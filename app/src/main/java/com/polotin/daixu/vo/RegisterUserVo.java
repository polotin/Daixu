package com.polotin.daixu.vo;

public class RegisterUserVo {
    private String phoneNumber;
    private String userName;
    private String pwd;
    private String pwdConfirm;

    public RegisterUserVo() {
    }

    public RegisterUserVo(String phoneNumber, String userName, String pwd, String pwdConfirm) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.pwd = pwd;
        this.pwdConfirm = pwdConfirm;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdConfirm() {
        return pwdConfirm;
    }

    public void setPwdConfirm(String pwdConfirm) {
        this.pwdConfirm = pwdConfirm;
    }
}
