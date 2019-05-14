package com.polotin.daixu.model;

import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.view.IMainView;

public interface IMainModel {
    void getPlanListByPhoneNumber(String phoneNumber, IMainView iMainView);

    void startPlan(Plan plan, IMainView iMainView);
}
