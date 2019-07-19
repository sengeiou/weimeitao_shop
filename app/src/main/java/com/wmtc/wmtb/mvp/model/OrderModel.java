package com.wmtc.wmtb.mvp.model;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.mvp.bean.BindDetailBean;
import com.wmtc.wmtb.mvp.bean.BindListBean;
import com.wmtc.wmtb.mvp.bean.CanNotTixianBean;
import com.wmtc.wmtb.mvp.bean.CardListBean;
import com.wmtc.wmtb.mvp.bean.CustomChatBean;
import com.wmtc.wmtb.mvp.bean.OffLineDetailBean;
import com.wmtc.wmtb.mvp.bean.OnLineDetailBean;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;
import com.wmtc.wmtb.mvp.bean.TixianBean;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.OrderDetailsBean;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.mvp.bean.TixianMainBean;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderRecordPojo;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;

public class OrderModel extends BaseModel<WmtServer> {
    public OrderModel(Class<WmtServer> t) {
        super(t);
    }

    public Observable<OrderStateBean> getOrderState(OrderPojo orderPojo) {
        return mServer.getOrderState(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<OrderDetailsBean> getOrderDetails(OrderPojo orderPojo) {
        return mServer.getOrderDetails(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<CustomChatBean> getCustomerService(OrderPojo orderPojo) {
        return mServer.getCustomerService(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<OrderStateBean> getAllOrder(OrderPojo orderPojo) {
        return mServer.getOrderState(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateOrderByShop(OrderPojo orderPojo) {
        return mServer.updateOrderByShop(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<TixianBean> getTixian(OrderPojo orderPojo) {
        return mServer.getTixian(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShoukuanBean> getShouKuanRecord(OrderPojo orderPojo) {
        return mServer.getShouKuanRecord(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<BindListBean> getBangDingNumList(OrderPojo orderPojo) {
        return mServer.getBangDingNumList(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShoukuanBean> getOfflineShouKuanRecord(OrderPojo orderPojo) {
        return mServer.getOfflineShouKuanRecord(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShoukuanBean> getBangDingList(OrderPojo orderPojo) {
        return mServer.getBangDingList(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<OffLineDetailBean> getOfflineDetail(String id) {
        OrderRecordPojo orderPojo = new OrderRecordPojo();
        orderPojo.setOfflineOrderId(id);
        return mServer.getOfflineDetail(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<OnLineDetailBean> getOnLineDetail(String id) {
        OrderRecordPojo orderPojo = new OrderRecordPojo();
        orderPojo.setOfflineOrderId(id);
        return mServer.getOnLineDetail(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<BindDetailBean> getBangDingDetail(String id) {
        OrderRecordPojo orderPojo = new OrderRecordPojo();
        orderPojo.setOfflineOrderId(id);
        return mServer.getBangDingDetail(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<TixianMainBean> getTixianMain(OrderPojo orderPojo) {
        return mServer.getTixianMain(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<CardListBean> getCard(OrderPojo orderPojo) {
        return mServer.getCard(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<CanNotTixianBean> getCanNotTixianAmount(OrderPojo orderPojo) {
        return mServer.getCanNotTixianAmount(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> addTixianRecord(OrderPojo orderPojo) {
        return mServer.addTixianRecord(orderPojo).compose(new IoMainSchedule<>());
    }


}
