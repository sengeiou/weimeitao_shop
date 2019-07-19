package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.TeachBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.TeachInfoPojo;
import com.wmtc.wmtb.ui.activity.TeachListActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/3/27.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TeachListPresenter extends BasePresenter<TeachListActivity> {

    private final CommonModel mCommonModel;

    public TeachListPresenter(TeachListActivity iView) {
        super(iView);
        mCommonModel = new CommonModel(WmtServer.class);
    }

    public void teachList() {
        mCommonModel.getTechnicianList(WmtApplication.user_shopId).subscribe(new DefaultCallBackObserver<TeachBean>() {
            @Override
            public void responseSuccess(TeachBean teachBean) {
                mIView.teachList(teachBean);
            }

            @Override
            public void responseFail(TeachBean teachBean) {

            }
        });
    }

    public void updateTechnicianDetail(TeachInfoPojo pojo) {
        mCommonModel.updateTechnicianDetail(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.delOk();
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });

    }

}
