package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.CanNotTixianBean;
import com.wmtc.wmtb.mvp.bean.CardListBean;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.activity.ToApplyActivity;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class CardMainPresenter extends BasePresenter<ToApplyActivity> {

    private OrderModel myorderModel;

    public CardMainPresenter(ToApplyActivity iView) {
        super(iView);
        myorderModel = new OrderModel(WmtServer.class);
    }

    //获取卡信息
    public void getCard() {
        OrderPojo pojo = new OrderPojo();
        pojo.setUserId(BaseInitApplication.mHeardMap.get("id"));
        myorderModel.getCard(pojo).safeSubscribe(new DefaultCallBackObserver<CardListBean>() {
            @Override
            public void responseSuccess(CardListBean bean) {
                mIView.getCardDate(bean);
            }

            @Override
            public void responseFail(CardListBean bean) {

            }
        });
    }

    //获取不可提现金额
    public void getCanNotTixianAmount() {
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        myorderModel.getCanNotTixianAmount(pojo).safeSubscribe(new DefaultCallBackObserver<CanNotTixianBean>() {
            @Override
            public void responseSuccess(CanNotTixianBean bean) {
                mIView.getCanNotTixianAmountDate(bean);
            }

            @Override
            public void responseFail(CanNotTixianBean bean) {

            }
        });
    }

    //确认提现
    public void addTixianRecord(OrderPojo orderPojo) {
        myorderModel.addTixianRecord(orderPojo).safeSubscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.addTixianRecordDate(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }
}
