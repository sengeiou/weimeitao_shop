package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.OffLineDetailBean;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.activity.OrderRecordListActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

public class CollectionPresenter extends BasePresenter<OrderRecordListActivity> {

    private OrderModel myorderModel;

    public CollectionPresenter(OrderRecordListActivity iView) {
        super(iView);
        myorderModel = new OrderModel(WmtServer.class);
    }


    public void getShouKuanRecord(String page) {
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        pojo.setCurrentPage(page);
        myorderModel.getShouKuanRecord(pojo).safeSubscribe(new DefaultCallBackObserver<ShoukuanBean>() {
            @Override
            public void responseSuccess(ShoukuanBean shoukuanBean) {
                mIView.getShouKuanRecordDate(shoukuanBean);
            }

            @Override
            public void responseFail(ShoukuanBean shoukuanBean) {

            }
        });
    }

    public void getBangDingList(String page) {
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        pojo.setCurrentPage(page);
        myorderModel.getBangDingList(pojo).safeSubscribe(new DefaultCallBackObserver<ShoukuanBean>() {
            @Override
            public void responseSuccess(ShoukuanBean shoukuanBean) {
                mIView.getShouKuanRecordDate(shoukuanBean);
            }

            @Override
            public void responseFail(ShoukuanBean shoukuanBean) {

            }
        });
    }


    public void getOfflineShouKuanRecord(String page) {
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        pojo.setCurrentPage(page);
        myorderModel.getOfflineShouKuanRecord(pojo).safeSubscribe(new DefaultCallBackObserver<ShoukuanBean>() {
            @Override
            public void responseSuccess(ShoukuanBean shoukuanBean) {
                mIView.getShouKuanRecordDate(shoukuanBean);
            }

            @Override
            public void responseFail(ShoukuanBean shoukuanBean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.complete();
            }
        });
    }
}
