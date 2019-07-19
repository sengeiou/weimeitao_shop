package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.AfterSalePojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderStatusPojo;
import com.wmtc.wmtb.ui.activity.AllOrderActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class OrderAllPresenter extends BasePresenter<AllOrderActivity> {

    private OrderModel myorderModel;
    private CommonModel mModel;

    public OrderAllPresenter(AllOrderActivity iView) {
        super(iView);
        myorderModel = new OrderModel(WmtServer.class);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getAllOrder(OrderPojo orderPojo) {
        myorderModel.getAllOrder(orderPojo).subscribe(new DefaultCallBackObserver<OrderStateBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(OrderStateBean orderStateBean) {
                mIView.getAllOrder(orderStateBean);
            }

            @Override
            public void responseFail(OrderStateBean orderDetailsBean) {

            }
        });
    }

    public void getAllOrderUpdate(OrderPojo orderPojo) {
        myorderModel.getAllOrder(orderPojo).subscribe(new DefaultCallBackObserver<OrderStateBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(OrderStateBean orderStateBean) {
                mIView.getAllOrder(orderStateBean);
            }

            @Override
            public void responseFail(OrderStateBean orderDetailsBean) {

            }
        });
    }

    public void afterSale(AfterSalePojo pojo) {
        mModel.handleAfterSale(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.afterSale();
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

    public void updateOrderByShop(OrderStatusPojo pojo) {
        mModel.updateOrderByShop(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity),this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.orderStatusUp(pojo.getOrderStatus());
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

}
