package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.CustomChatBean;
import com.wmtc.wmtb.mvp.bean.TixianBean;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.OrderDetailsBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.AfterSalePojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.activity.OrderDetailsActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;

public class OrderDetailsPresenter extends BasePresenter<OrderDetailsActivity> {

    private OrderModel myorderModel;
    private final CommonModel mModel;

    public OrderDetailsPresenter(OrderDetailsActivity iView) {
        super(iView);
        myorderModel = new OrderModel(WmtServer.class);
        mModel = new CommonModel(WmtServer.class);
    }

    //订单详情
    public void getOrderDetails(OrderPojo orderPojo) {
        myorderModel.getOrderDetails(orderPojo).subscribe(new DefaultCallBackObserver<OrderDetailsBean>(new LoaddingErrorImplTip(mIView),this) {
            @Override
            public void responseSuccess(OrderDetailsBean orderDetailsBean) {
                mIView.getOrderDetails(orderDetailsBean);
            }

            @Override
            public void responseFail(OrderDetailsBean orderDetailsBean) {

            }
        });
    }

    //操作订单
    public void updateOrderByShop(OrderPojo orderPojo) {
        myorderModel.updateOrderByShop(orderPojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity),this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.backorderStatusUp(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }
    public void getCustomerService(){
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setShopId(WmtApplication.user_shopId);
        orderPojo.setUserType("2");
        myorderModel.getCustomerService(orderPojo).subscribe(new DefaultCallBackObserver<CustomChatBean>(this) {
            @Override
            public void responseSuccess(CustomChatBean customChatBean) {
                mIView.chatBean(customChatBean);
            }

            @Override
            public void responseFail(CustomChatBean customChatBean) {

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

}
