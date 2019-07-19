package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.CardInforBean;
import com.wmtc.wmtb.mvp.bean.CardNameBean;
import com.wmtc.wmtb.mvp.bean.DictListBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.model.OrderModel;
import com.wmtc.wmtb.mvp.pojo.DictPojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.SendCodePojo;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.ui.activity.AddCardActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class AddCardPresenter extends BasePresenter<AddCardActivity> {

    private CommonModel mModel;

    public AddCardPresenter(AddCardActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void dictListName() {
        DictPojo pojo = new DictPojo();
        pojo.setCode("sys_bank");
        mModel.dictListName(pojo).subscribe(new DefaultCallBackObserver<CardNameBean>() {
            @Override
            public void responseSuccess(CardNameBean bean) {
                mIView.dictListDate(bean);
            }

            @Override
            public void responseFail(CardNameBean proListBean) {

            }
        });
    }

    public void getInfor() {
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        mModel.getInfor(pojo).subscribe(new DefaultCallBackObserver<CardInforBean>() {
            @Override
            public void responseSuccess(CardInforBean bean) {
                mIView.getInforDate(bean);
            }

            @Override
            public void responseFail(CardInforBean proListBean) {

            }
        });
    }

    public void smsCode(SmsCodePojo pojo) {
        mModel.smsCode(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.smsCodeDate();
            }

            @Override
            public void responseFail(BaseBean verLoginBean) {

            }
        });
    }

    public void sendCode(SendCodePojo pojo) {
        mModel.sendCode(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity), this) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.smsCodeDate();
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }
}
