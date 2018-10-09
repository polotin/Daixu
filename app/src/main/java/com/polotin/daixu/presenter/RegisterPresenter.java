package com.polotin.daixu.presenter;

import android.os.Handler;
import android.os.Message;

import com.polotin.daixu.utils.MessageUtil;
import com.polotin.daixu.utils.PatternMatcher;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.view.IRegisterView;

import org.apache.commons.lang3.StringUtils;

public class RegisterPresenter implements IRegisterPresenter {
    static IRegisterView iRegisterView;

    public RegisterPresenter(IRegisterView iRegisterView) {
        this.iRegisterView = iRegisterView;
    }

    @Override
    public void sendMessage(String phoneNumber) {
        if (!StringUtils.isNotEmpty(phoneNumber)) {
            iRegisterView.sendMessageResult(Constant.MSG_EMPTY_PHONE);
            return;
        }
        if (StringUtils.length(phoneNumber)!= 11 | !PatternMatcher.validatePhoneNumber(phoneNumber)) {
            iRegisterView.sendMessageResult(Constant.MSG_ILLEGAL_PHONE_NUMBER);
            return;
        }
        try {
            MessageUtil.senMessage(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
