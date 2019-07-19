package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.TeachInfoPojo;
import com.wmtc.wmtb.ui.activity.WorkTimeActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class WorkTimePresenter extends BasePresenter<WorkTimeActivity> {

    private final CommonModel mCommonModel;

    public WorkTimePresenter(WorkTimeActivity iView) {
        super(iView);
        mCommonModel = new CommonModel(WmtServer.class);
    }

    public void updateTechnicianDetail(TeachInfoPojo pojo) {
        mCommonModel.updateTechnicianDetail(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new PostImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.netOk("3");
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });

    }
}
