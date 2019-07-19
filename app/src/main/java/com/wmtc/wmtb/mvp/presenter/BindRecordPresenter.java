package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.BindListBean;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.activity.BindRecordListActivity;
import com.wmtc.wmtb.ui.activity.OrderRecordListActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

public class BindRecordPresenter extends BasePresenter<BindRecordListActivity> {

    private OrderModel myorderModel;

    public BindRecordPresenter(BindRecordListActivity iView) {
        super(iView);
        myorderModel = new OrderModel(WmtServer.class);
    }


    public void getBangDingNumList(String page) {
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        pojo.setCurrentPage(page);
        myorderModel.getBangDingNumList(pojo).safeSubscribe(new DefaultCallBackObserver<BindListBean>() {
            @Override
            public void responseSuccess(BindListBean bean) {
                mIView.getBangDingNumList(bean);
            }

            @Override
            public void responseFail(BindListBean bean) {

            }
        });
    }
}
