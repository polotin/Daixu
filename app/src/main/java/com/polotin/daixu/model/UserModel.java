package com.polotin.daixu.model;

import android.util.Log;

import com.polotin.daixu.DaixuMessage;
import com.polotin.daixu.entity.User;
import com.polotin.daixu.listener.OnLoginFinishedListener;
import com.polotin.daixu.values.Constant;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class UserModel implements IUserModel {
    @Override
    public void validLogin(String id, String pwd, OnLoginFinishedListener listener) {
        int result = 0;

        //登录验证
        result = Constant.LOGIN_SUCCESS;

        if (result == Constant.LOGIN_SUCCESS) {
        }
    }

    @Override
    public void sendMsg(String phoneNumber) {

    }

    @Override
    public void doRegister(final User user, final Handler handler) {
        BmobQuery query =new BmobQuery("User");
        query.addWhereEqualTo("phoneNumber", user.getPhoneNumber());
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    if(jsonArray.length() > 0) {
                        handler.sendEmptyMessage(DaixuMessage.PHONE_EXIST);
                    } else {
                        saveUser(user, handler);
                    }
                } else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void saveUser(User user, final Handler handler) {
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    handler.sendEmptyMessage(DaixuMessage.REGISTER_SUCCESS);
                } else {
                    Log.i("bmob", "失败："+e.getMessage()+","+e.getErrorCode());
                    handler.sendEmptyMessage(DaixuMessage.REGISTER_FAIL);
                }
            }
        });
    }

    @Override
    public void doLogin(String phoneNumber, final String password, final Handler handler){
        BmobQuery query = new BmobQuery("User");
        query.addWhereEqualTo("phoneNumber", phoneNumber);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    if (jsonArray.length() > 0) {
                        try {
//                            User user = (User) jsonArray.opt(0);
                            JSONObject jsonObject = jsonArray.optJSONObject(0);

                            if (password.equals(jsonObject.getString("password"))) {
                                handler.sendEmptyMessage(DaixuMessage.LOGIN_SUCCESS);
                            } else {
                                handler.sendEmptyMessage(DaixuMessage.PHONE_OR_PWD_ERROR);
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                } else {
                    handler.sendEmptyMessage(DaixuMessage.LOGIN_FAILED);
                }
            }
        });
    }
}
