package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.PoolAmountBean;
import com.wmtc.wmtb.mvp.bean.PoolListBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.BonusPoolPojo;
import com.wmtc.wmtb.ui.activity.PlatformActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PlatformPresenter extends BasePresenter<PlatformActivity> {

    private final CommonModel mModel;

    public PlatformPresenter(PlatformActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getPoolList(String bonusStatus, int currentPage) {
        BonusPoolPojo pojo = new BonusPoolPojo();
        pojo.setBonusStatus(bonusStatus);
        pojo.setCurrentPage(currentPage+"");
        pojo.setShopId(WmtApplication.user_shopId);
        mModel.getBonusPoolsDetail(pojo).subscribe(new DefaultCallBackObserver<PoolListBean>() {
            @Override
            public void responseSuccess(PoolListBean bean) {
                mIView.getPoolList(bean);
            }

            @Override
            public void responseFail(PoolListBean poolListBean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.complete();
            }
        });
    }
    public void getAmountMoney(){
        BonusPoolPojo pojo = new BonusPoolPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        mModel.getBonusPaidAmount(pojo).subscribe(new DefaultCallBackObserver<PoolAmountBean>() {
            @Override
            public void responseSuccess(PoolAmountBean poolAmountBean) {
                mIView.amountMoney(poolAmountBean);
            }

            @Override
            public void responseFail(PoolAmountBean poolAmountBean) {

            }
        });
    }

}
