package com.polotin.daixu.model;

import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.view.IPlanView;

public interface IPlanModel {
    void addPlan(Plan plan, IPlanView iPlanView);
}
