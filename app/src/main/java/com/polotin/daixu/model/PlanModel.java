package com.polotin.daixu.model;

import android.util.Log;

import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.view.IPlanView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PlanModel implements IPlanModel {


    public void addPlan(Plan plan, final IPlanView iPlanView){
        plan.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    iPlanView.addSuccess();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void updatePlan(Plan plan, final IPlanView iPlanView) {
        plan.update(plan.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    iPlanView.addSuccess();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
