package com.polotin.daixu.presenter;

import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.model.IMainModel;
import com.polotin.daixu.model.MainModel;
import com.polotin.daixu.view.IMainView;

public class MainPresenter implements IMainPresenter {
    private IMainModel iMainModel;
    private IMainView iMainView;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        this.iMainModel = new MainModel();
    }

    @Override
    public void getPlanListByPhoneNumber(String phoneNumber) {
        iMainModel.getPlanListByPhoneNumber(phoneNumber, iMainView);
    }

    @Override
    public void startPlan(Plan plan) {
        iMainModel.startPlan(plan, iMainView);
    }
}
