package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.TixianMainBean;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.activity.PreApplyActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

public class TixainMainPresenter extends BasePresenter<PreApplyActivity> {

    private OrderModel myorderModel;

    public TixainMainPresenter(PreApplyActivity iView) {
        super(iView);
        myorderModel = new OrderModel(WmtServer.class);
    }

    //提现记录
    public void getTixianMain(OrderPojo orderPojo) {
        myorderModel.getTixianMain(orderPojo).safeSubscribe(new DefaultCallBackObserver<TixianMainBean>() {
            @Override
            public void responseSuccess(TixianMainBean bean) {
                mIView.getPreList(bean);
            }

            @Override
            public void responseFail(TixianMainBean bean) {

            }
            @Override
            public void onComplete() {
                super.onComplete();
                mIView.complete();
            }
        });
    }
}
