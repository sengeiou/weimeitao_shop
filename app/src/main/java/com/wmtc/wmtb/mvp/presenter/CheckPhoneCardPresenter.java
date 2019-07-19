package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.CardPojo;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;
import com.wmtc.wmtb.ui.activity.CheckPhoneActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

public class CheckPhoneCardPresenter extends BasePresenter<CheckPhoneActivity> {

    private CommonModel mModel;

    public CheckPhoneCardPresenter(CheckPhoneActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void smsVer(VerLoginPojo pojo) {
        mModel.smsVer(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new GetImplTip(mIView)) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.smsVerDate();
            }

            @Override
            public void responseFail(BaseBean verLoginBean) {

            }
        });
    }

    public void addCard(CardPojo pojo) {
        mModel.addCard(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new GetImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.addCardDate();
            }

            @Override
            public void responseFail(BaseBean bean) {

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

}
