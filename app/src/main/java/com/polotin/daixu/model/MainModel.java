package com.polotin.daixu.model;

import android.util.Log;

import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.view.IMainView;
import com.polotin.daixu.view.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainModel implements IMainModel {
    @Override
    public void getPlanListByPhoneNumber(String phoneNumber, final IMainView iMainView) {
        BmobQuery<Plan> query = new BmobQuery<Plan>();
        query.addWhereEqualTo("phoneNumber", phoneNumber);

        query.findObjects(new FindListener<Plan>() {
            @Override
            public void done(List<Plan> list, BmobException e) {
                if (e == null) {
                    iMainView.setPlanList(list);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void startPlan(Plan plan, final IMainView iMainView) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = null;
//        try {
//            date = df.parse(df.format(new Date()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        plan.setStartFlag(true);
        plan.setStartAt(df.format(new Date()));
        plan.update(plan.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    MainActivity.refreshList();
                } else {
                    Log.i("bmob","更新失败：" + e.getMessage());
                }
            }
        });
    }
}
