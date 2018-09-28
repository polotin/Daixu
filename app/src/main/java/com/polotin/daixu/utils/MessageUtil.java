package com.polotin.daixu.utils;

import com.polotin.daixu.presenter.RegisterPresenter;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.view.RegisterActivity;

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

import java.io.IOException;
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

    private static final String TEMPLATEID = "9444167";

    private static final String CODELEN = "6";

    private static String PHONENUMBER = "";

    public static void senMessage(String phoneNumber) throws Exception {
        PHONENUMBER = phoneNumber;
        new Thread(runnable).start();
    }

    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(SERVER_URL);
            String curTime = String.valueOf((new Date()).getTime() / 1000L);

            String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

            httpPost.addHeader("AppKey", APP_KEY);
            httpPost.addHeader("Nonce", NONCE);
            System.out.println(NONCE);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            /*
             * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
             * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
             * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
             */
            nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
            nvps.add(new BasicNameValuePair("mobile", PHONENUMBER));
            nvps.add(new BasicNameValuePair("codeLen", CODELEN));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
                // 执行请求
                HttpResponse response = httpClient.execute(httpPost);
                /*
                 * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
                 * 2.具体的code有问题的可以参考官网的Code状态表
                 */
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "utf-8");
                    System.out.println(result);
                    JSONObject jsonObject = new JSONObject(result);
                    Constant.VALIDATE_CODE = jsonObject.getString("obj");
//                    Constant.VALIDATE_CODE_TIME = System.currentTimeMillis();
                    System.out.println("VALIDATE_CODE:" + Constant.VALIDATE_CODE);
//                    System.out.println("VALIDATE_CODE_TIME:" + Constant.VALIDATE_CODE_TIME);
                    RegisterPresenter.handler.sendEmptyMessage(Integer.parseInt(jsonObject.getString("code")));
                } else {
                    RegisterPresenter.handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
