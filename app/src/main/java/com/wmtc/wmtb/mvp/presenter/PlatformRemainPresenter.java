package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.PoolAmountBean;
import com.wmtc.wmtb.mvp.bean.PoolListBean;
import com.wmtc.wmtb.mvp.bean.PoolRemainListBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.BonusPoolPojo;
import com.wmtc.wmtb.ui.activity.PlatformActivity;
import com.wmtc.wmtb.ui.activity.PlatformRemainActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PlatformRemainPresenter extends BasePresenter<PlatformRemainActivity> {

    private final CommonModel mModel;

    public PlatformRemainPresenter(PlatformRemainActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void getRemainPoolList( int currentPage) {
        BonusPoolPojo pojo = new BonusPoolPojo();
        pojo.setCurrentPage(currentPage+"");
        pojo.setShopId(WmtApplication.user_shopId);
        mModel.getBonusPoolsRemainDetail(pojo).subscribe(new DefaultCallBackObserver<PoolRemainListBean>() {
            @Override
            public void responseSuccess(PoolRemainListBean bean) {
                mIView.getPoolRemainList(bean);
            }

            @Override
            public void responseFail(PoolRemainListBean poolListBean) {

            }
        });
    }
    public void getAmountMoney(){
        BonusPoolPojo pojo = new BonusPoolPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        mModel.getBonusPoolsRemainTotalMoney(pojo).subscribe(new DefaultCallBackObserver<PoolAmountBean>() {
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
