package com.polotin.daixu.model;

import com.polotin.daixu.view.IMainView;

public interface IMainModel {
    void getPlanListByPhoneNumber(String phoneNumber, IMainView iMainView);
}
