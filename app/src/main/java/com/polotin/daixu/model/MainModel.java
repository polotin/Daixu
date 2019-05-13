package com.polotin.daixu.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.view.IMainView;


import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MainModel implements IMainModel {
    @Override
    public void getPlanListByPhoneNumber(String phoneNumber, final IMainView iMainView) {
        BmobQuery<Plan> query = new BmobQuery<Plan>();
        query.addWhereEqualTo("phoneNumber", phoneNumber);

        query.findObjects(new FindListener<Plan>() {
            @Override
            public void done(List<Plan> list, BmobException e) {
                if(e==null){
                    iMainView.setPlanList(list);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
