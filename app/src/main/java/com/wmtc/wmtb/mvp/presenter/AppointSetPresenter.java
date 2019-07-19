package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.AppointPojo;
import com.wmtc.wmtb.ui.activity.AppointSetActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AppointSetPresenter extends BasePresenter<AppointSetActivity> {

    private final CommonModel mCommonModel;

    public AppointSetPresenter(AppointSetActivity iView) {
        super(iView);
        mCommonModel = new CommonModel(WmtServer.class);
    }

    public void appointSet(AppointPojo pojo) {
        mCommonModel.appointSet(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.netOk();
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

    public void shopEnter(String uid) {
        mCommonModel.shopEnter(uid).subscribe(new DefaultCallBackObserver<ShopsBean>() {

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
