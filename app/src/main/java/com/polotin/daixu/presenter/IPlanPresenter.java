package com.polotin.daixu.presenter;

import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.view.IPlanView;

public interface IPlanPresenter {

    void addPlan(Plan plan);
    void updatePlan(Plan plan);
}
