package com.polotin.daixu.presenter;

import android.os.Handler;
import android.os.Message;

import com.polotin.daixu.entity.ValidationCode;
import com.polotin.daixu.utils.CacheUtil;
import com.polotin.daixu.utils.MessageUtil;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.values.Msg;
import com.polotin.daixu.values.NeteaseIM;
import com.polotin.daixu.view.IValidateView;

public class ValidatePresenter implements IValidatePresenter {
    static IValidateView iValidateView;

    public ValidatePresenter(IValidateView iValidateView) {
        this.iValidateView = iValidateView;
    }

    @Override
    public void checkValidationCode(String validationCode) {
        ValidationCode vc = CacheUtil.getValidationCode();
        if (System.currentTimeMillis() - Long.valueOf(vc.getCodeTime()) <= Constant.VALIDATE_CODE_EXPIRE_TIME && vc != null) {
            iValidateView.handleMessage(validationCode.equals(vc.getCode()) ? Msg.CHECK_VALIDATION_TRUE : Msg.CHECK_VALIDATION_FALSE);
        }
    }

    @Override
    public void sendMessage(){
        try{
            MessageUtil.sendMessage(Constant.MOBILE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NeteaseIM.MSG_RETURN_CODE_200:
                    CacheUtil.cacheValidationCode();
                    iValidateView.sendMessageResult(Constant.MSG_SEND_MSG_SUCCESS);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_315:
                    iValidateView.sendMessageResult(NeteaseIM.MSG_315);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_403:
                    iValidateView.sendMessageResult(NeteaseIM.MSG_403);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_404:
                    iValidateView.sendMessageResult(NeteaseIM.MSG_404);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_413:
                    iValidateView.sendMessageResult(NeteaseIM.MSG_413);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_414:
                    iValidateView.sendMessageResult(NeteaseIM.MSG_414);
                    break;
                case NeteaseIM.MSG_RETURN_CODE_500:
                    iValidateView.sendMessageResult(NeteaseIM.MSG_500);
                    break;
                default:
                    iValidateView.sendMessageResult(Constant.MSG_SEND_MSG_FAIL);
                    break;
            }
        }
    };
}
