package com.polotin.daixu.presenter;

import android.os.Handler;
import android.os.Message;

import com.polotin.daixu.utils.MessageUtil;
import com.polotin.daixu.utils.PatternMatcher;
import com.polotin.daixu.view.IRegisterView;

import org.apache.commons.lang3.StringUtils;

public class RegisterPresenter implements IRegisterPresenter {
    final static String EMPTY_PHONE_NUMBER = "请输入手机号";
    final static String ILLEGAL_PHONE_NUMBER = "请输入正确的手机号";
    final static String SEND_MSG_SUCCESS = "发送消息成功，请注意接收";
    final static String SEND_MSG_FAIL = "发送消息失败，请检查后重试";

    static IRegisterView iRegisterView;

    public RegisterPresenter(IRegisterView iRegisterView) {
        this.iRegisterView = iRegisterView;
    }

    @Override
    public void sendMessage(String phoneNumber) {
        if (!StringUtils.isNotEmpty(phoneNumber)) {
            iRegisterView.sendMessageResult(EMPTY_PHONE_NUMBER);
            return;
        }
        if (StringUtils.length(phoneNumber)!= 11 | !PatternMatcher.validatePhoneNumber(phoneNumber)) {
            iRegisterView.sendMessageResult(ILLEGAL_PHONE_NUMBER);
            return;
        }
        try {
            MessageUtil.senMessage(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (Integer.toString(msg.what)) {
                case "200":
                    iRegisterView.sendMessageResult(SEND_MSG_SUCCESS);
                    break;
                case "315":
                    iRegisterView.sendMessageResult("315");
                    break;
                case "403":
                    iRegisterView.sendMessageResult("403");
                    break;
                case "404":
                    iRegisterView.sendMessageResult("404");
                    break;
                case "413":
                    iRegisterView.sendMessageResult("413");
                    break;
                case "414":
                    iRegisterView.sendMessageResult("414");
                    break;
                case "500":
                    iRegisterView.sendMessageResult("500");
                    break;
                default:
                    iRegisterView.sendMessageResult(SEND_MSG_FAIL);
                    break;
            }
        }
    };
}
