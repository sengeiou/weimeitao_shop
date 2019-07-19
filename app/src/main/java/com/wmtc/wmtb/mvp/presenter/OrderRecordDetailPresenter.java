package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.BindDetailBean;
import com.wmtc.wmtb.mvp.bean.OffLineDetailBean;
import com.wmtc.wmtb.mvp.bean.OnLineDetailBean;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.ui.activity.OrderRecordDetailActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/4/18.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderRecordDetailPresenter extends BasePresenter<OrderRecordDetailActivity> {

    private OrderModel mModel;

    public OrderRecordDetailPresenter(OrderRecordDetailActivity iView) {
        super(iView);
        mModel = new OrderModel(WmtServer.class);
    }


    public void getOnLineDetail(String id) {
        mModel.getOnLineDetail(id).subscribe(new DefaultCallBackObserver<OnLineDetailBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(OnLineDetailBean bean) {
                mIView.onLineDetail(bean);
            }

            @Override
            public void responseFail(OnLineDetailBean shoukuanBean) {

            }
        });
    }
   public void getOfflineDetail(String id) {
        mModel.getOfflineDetail(id).subscribe(new DefaultCallBackObserver<OffLineDetailBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(OffLineDetailBean bean) {
                mIView.offLineDetail(bean);
            }

            @Override
            public void responseFail(OffLineDetailBean shoukuanBean) {

            }
        });
    }

    public void getBangDingDetail(String id) {
        mModel.getBangDingDetail(id).subscribe(new DefaultCallBackObserver<BindDetailBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(BindDetailBean bean) {
                mIView.bindDetail(bean);
            }

            @Override
            public void responseFail(BindDetailBean shoukuanBean) {

            }
        });
    }


}
