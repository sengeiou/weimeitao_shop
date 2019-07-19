package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.ui.activity.ShopDoorAgainActivity;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/4/29.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopDoorAgainPresenter extends BasePresenter<ShopDoorAgainActivity> {

    private final CommonModel mModel;

    public ShopDoorAgainPresenter(ShopDoorAgainActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void shopEnter(int status,String shopId) {
        mModel.shopEnter(BaseInitApplication.mHeardMap.get("id")).subscribe(new DefaultCallBackObserver<ShopsBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(ShopsBean bean) {
                mIView.showShopEnter(bean,status,shopId);
            }

            @Override
            public void responseFail(ShopsBean bean) {

            }
        });
    }
}
