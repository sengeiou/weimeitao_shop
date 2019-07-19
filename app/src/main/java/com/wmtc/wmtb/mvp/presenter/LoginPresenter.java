package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.LoginBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.VerLoginBean;
import com.wmtc.wmtb.mvp.model.LoginModel;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;
import com.wmtc.wmtb.base.CommonLoginActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LoginPresenter extends BasePresenter<CommonLoginActivity> {

    private final LoginModel mLoginModel;

    public LoginPresenter(CommonLoginActivity iView) {
        super(iView);
        mLoginModel = new LoginModel(WmtServer.class);
    }

    public void verLogin(VerLoginPojo verLoginPojo) {
        mLoginModel.verLogin(verLoginPojo).subscribe(new DefaultCallBackObserver<VerLoginBean>(new LoaddingErrorImplTip(mIView)) {

            @Override
            public void responseSuccess(VerLoginBean verLoginBean) {
                mIView.verLogin(verLoginBean);
            }

            @Override
            public void responseFail(VerLoginBean verLoginBean) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void toLogin(VerLoginPojo verLoginPojo) {
        verLoginPojo.setJiguang_id(WmtApplication.mJPushUdid);
        mLoginModel.toLogin(verLoginPojo).subscribe(new DefaultCallBackObserver<LoginBean>(new LoaddingErrorImplTip(mIView)) {

            @Override
            public void responseSuccess(LoginBean bean) {
                mIView.toLogin(bean);
            }

            @Override
            public void responseFail(LoginBean verLoginBean) {

            }
        });
    }

    public void register(VerLoginPojo verLoginPojo) {
        verLoginPojo.setJiguang_id(WmtApplication.mJPushUdid);
        mLoginModel.register(verLoginPojo).subscribe(new DefaultCallBackObserver<LoginBean>(new LoaddingErrorImplTip(mIView)) {

            @Override
            public void responseSuccess(LoginBean bean) {
                mIView.toLogin(bean);
            }

            @Override
            public void responseFail(LoginBean verLoginBean) {

            }
        });
    }

    public void forget(VerLoginPojo verLoginPojo) {
        mLoginModel.forget(verLoginPojo).subscribe(new DefaultCallBackObserver<LoginBean>(new LoaddingErrorImplTip(mIView)) {

            @Override
            public void responseSuccess(LoginBean bean) {
                mIView.toLogin(bean);
            }

            @Override
            public void responseFail(LoginBean verLoginBean) {

            }
        });
    }

    public void smsCode(SmsCodePojo pojo) {
        mLoginModel.smsCode(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.smsCode();
            }

            @Override
            public void responseFail(BaseBean verLoginBean) {

            }
        });
    }

    public void smsVer(VerLoginPojo pojo) {
        mLoginModel.smsVer(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new GetImplTip(mIView)) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.smsVer();
            }

            @Override
            public void responseFail(BaseBean verLoginBean) {

            }
        });
    }

    public void shopEnter(String uid) {
        mLoginModel.shopEnter(uid).subscribe(new DefaultCallBackObserver<ShopsBean>() {

            @Override
            public void responseSuccess(ShopsBean bean) {
                mIView.shopEnter(bean);
            }

            @Override
            public void responseFail(ShopsBean bean) {

            }
        });
    }

}
