package com.polotin.daixu.presenter;

import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.model.IPlanModel;
import com.polotin.daixu.model.PlanModel;
import com.polotin.daixu.view.IPlanView;

public class PlanPresenter implements IPlanPresenter {
    private IPlanView iPlanView;
    private IPlanModel iPlanModel;

    public PlanPresenter(IPlanView iPlanView) {
        this.iPlanView = iPlanView;
        iPlanModel = new PlanModel();
    }

    public void addPlan(Plan plan) {
        iPlanModel.addPlan(plan, iPlanView);
    }

    public void updatePlan(Plan plan) {
        iPlanModel.updatePlan(plan, iPlanView);
    }
}
