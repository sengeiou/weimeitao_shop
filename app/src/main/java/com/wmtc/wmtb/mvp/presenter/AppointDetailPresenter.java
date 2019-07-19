package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.fragment.AppointDetailFragment;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.BaseNetTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/4/29.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AppointDetailPresenter extends BasePresenter<AppointDetailFragment> {

    private OrderModel mOrderModel;

    public AppointDetailPresenter(AppointDetailFragment iView) {
        super(iView);
        mOrderModel = new OrderModel(WmtServer.class);

    }

    public void getOrderList(String type, BaseNetTip tip) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderStatus(type);
        orderPojo.setShopId(WmtApplication.user_shopId);
        mOrderModel.getOrderState(orderPojo).subscribe(new DefaultCallBackObserver<OrderStateBean>(tip,this) {
            @Override
            public void responseSuccess(OrderStateBean orderStateBean) {
                mIView.getOrderList(orderStateBean);
            }

            @Override
            public void responseFail(OrderStateBean orderStateBean) {

            }
        });
    }

    public void updateOrderByShop(OrderPojo orderPojo) {
        mOrderModel.updateOrderByShop(orderPojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity),this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.updateOrder(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

}
