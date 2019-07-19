package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.CanNotTixianBean;
import com.wmtc.wmtb.mvp.bean.CardListBean;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.activity.CardListActivity;
import com.wmtc.wmtb.ui.activity.ToApplyActivity;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

public class CardListPresenter extends BasePresenter<CardListActivity> {

    private OrderModel myorderModel;

    public CardListPresenter(CardListActivity iView) {
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
}
