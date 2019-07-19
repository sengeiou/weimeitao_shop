package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.model.LoginModel;
import com.wmtc.wmtb.ui.activity.ShopDoorAgainActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopProgressPresenter extends BasePresenter<ShopDoorAgainActivity> {


    private final LoginModel mModel;

    public ShopProgressPresenter(ShopDoorAgainActivity iView) {
        super(iView);
        mModel = new LoginModel(WmtServer.class);
    }

    public void shopEnter(String uid) {
        mModel.shopEnter(uid).subscribe(new DefaultCallBackObserver<ShopsBean>() {

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
