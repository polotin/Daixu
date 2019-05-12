package com.polotin.daixu.utils;

import com.polotin.daixu.presenter.LoginPresenter;
import com.polotin.daixu.presenter.ValidatePresenter;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.values.NeteaseIM;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageUtil {
    private static final String
            SERVER_URL = "https://api.netease.im/sms/sendcode.action";
    private static final String
            APP_KEY = "ba9f55be35153a97581ebe500137c8ed";
    private static final String APP_SECRET = "faabb7740feb";
    private static final String NONCE = RandomStringUtils.random(6, false, true);
    private static final String PARAMS = "['" + NONCE + "']";

    //    private static final String TEMPLATEID = "9444167";
    private static final String TEMPLATEID = "9284174";

    private static final String CODELEN = "6";

    private static String MOBILE = "";

    public static void sendMessage(String phoneNumber) throws Exception {
        MOBILE = phoneNumber;
        new Thread(send).start();
    }

    static Runnable send = new Runnable() {
        @Override
        public void run() {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(SERVER_URL);
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);
            httpPost.addHeader("AppKey", APP_KEY);
            httpPost.addHeader("Nonce", NONCE);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
            nvps.add(new BasicNameValuePair("mobile", MOBILE));
            nvps.add(new BasicNameValuePair("codeLen", CODELEN));
            nvps.add(new BasicNameValuePair("params", PARAMS));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "utf-8");
                    System.out.println(result);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals(Integer.toString(NeteaseIM.MSG_RETURN_CODE_200))) {
                        Constant.VALIDATE_CODE = jsonObject.getString("obj");
                        Constant.VALIDATE_CODE_TIME = curTime;
                    }
//                    LoginPresenter.handler.sendEmptyMessage(Integer.parseInt(jsonObject.getString("code")));
                } else {
//                    LoginPresenter.handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    static Runnable resend = new Runnable() {
        @Override
        public void run() {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(SERVER_URL);
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);
            httpPost.addHeader("AppKey", APP_KEY);
            httpPost.addHeader("Nonce", NONCE);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
            nvps.add(new BasicNameValuePair("mobile", MOBILE));
            nvps.add(new BasicNameValuePair("codeLen", CODELEN));
            nvps.add(new BasicNameValuePair("params", PARAMS));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "utf-8");
                    System.out.println(result);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals(Integer.toString(NeteaseIM.MSG_RETURN_CODE_200))) {
                        Constant.VALIDATE_CODE = jsonObject.getString("obj");
                        Constant.VALIDATE_CODE_TIME = curTime;
                        Constant.MOBILE = MOBILE;
                    }
                    ValidatePresenter.handler.sendEmptyMessage(Integer.parseInt(jsonObject.getString("code")));
                } else {
                    ValidatePresenter.handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
