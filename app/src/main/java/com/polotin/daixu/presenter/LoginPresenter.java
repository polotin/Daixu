package com.polotin.daixu.presenter;

import android.os.Handler;
import android.os.Message;

import com.polotin.daixu.listener.OnLoginFinishedListener;
import com.polotin.daixu.model.IUserModel;
import com.polotin.daixu.model.UserModel;
import com.polotin.daixu.utils.CacheUtil;
import com.polotin.daixu.utils.MessageUtil;
import com.polotin.daixu.utils.PatternMatcher;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.values.Msg;
import com.polotin.daixu.values.NeteaseIM;
import com.polotin.daixu.view.ILoginView;

import org.apache.commons.lang3.StringUtils;



public class LoginPresenter implements ILoginPresenter, OnLoginFinishedListener {
    static ILoginView iLoginView;
    IUserModel iUserModel;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        iUserModel = new UserModel();
    }

    @Override
    public void sendMsg(String phoneNumber){
        iLoginView.showProgressBar();
        if (!StringUtils.isNotEmpty(phoneNumber)) {
            iLoginView.sendMessageResult(Constant.MSG_EMPTY_PHONE);
            return;
        }
        if (StringUtils.length(phoneNumber)!= 11 | !PatternMatcher.validatePhoneNumber(phoneNumber)) {
            iLoginView.sendMessageResult(Constant.MSG_ILLEGAL_PHONE_NUMBER);
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
            switch (msg.what) {
                case NeteaseIM.MSG_RETURN_CODE_200:
                    CacheUtil.cacheValidationCode();
                    iLoginView.sendMessageResult(Constant.MSG_SEND_MSG_SUCCESS);
                    iLoginView.onValidationCodeReceived();
                    break;
                case NeteaseIM.MSG_RETURN_CODE_315:
                    iLoginView.sendMessageResult(NeteaseIM.MSG_315);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_403:
                    iLoginView.sendMessageResult(NeteaseIM.MSG_403);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_404:
                    iLoginView.sendMessageResult(NeteaseIM.MSG_404);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_413:
                    iLoginView.sendMessageResult(NeteaseIM.MSG_413);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_414:
                    iLoginView.sendMessageResult(NeteaseIM.MSG_414);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_500:
                    iLoginView.sendMessageResult(NeteaseIM.MSG_500);
                    break;
                case Msg.ACTION_SEND_MESSAGE:
                    iLoginView.sendMessage();
                    break;
                default:
                    iLoginView.sendMessageResult(Constant.MSG_SEND_MSG_FAIL);
                    break;
            }
        }
    };

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onInternetError() {

    }
}
