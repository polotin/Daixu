package com.polotin.daixu.entity;

import java.io.Serializable;

public class ValidationCode implements Serializable{
    private String code;
    private String codeTime;

    public ValidationCode(String code, String codeTime) {
        this.code = code;
        this.codeTime = codeTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeTime() {
        return codeTime;
    }

    public void setCodeTime(String codeTime) {
        this.codeTime = codeTime;
    }

    @Override
    public String toString() {
        return "ValidationCode{" +
                "code='" + code + '\'' +
                ", codeTime='" + codeTime + '\'' +
                '}';
    }
}
