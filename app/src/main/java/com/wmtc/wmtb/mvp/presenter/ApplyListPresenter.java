package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.TixianBean;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.activity.ApplyListActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

public class ApplyListPresenter extends BasePresenter<ApplyListActivity> {

    private OrderModel myorderModel;

    public ApplyListPresenter(ApplyListActivity iView) {
        super(iView);
        myorderModel = new OrderModel(WmtServer.class);
    }

    //提现记录
    public void applyList(OrderPojo orderPojo) {
        myorderModel.getTixian(orderPojo).safeSubscribe(new DefaultCallBackObserver<TixianBean>() {
            @Override
            public void responseSuccess(TixianBean tixianBean) {
                mIView.applyList(tixianBean);
            }

            @Override
            public void responseFail(TixianBean tixianBean) {

            }
        });
    }
}
