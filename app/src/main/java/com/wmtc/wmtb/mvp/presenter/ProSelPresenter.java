package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.ProjectSelListBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.ProSelPojo;
import com.wmtc.wmtb.ui.activity.ProSelActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProSelPresenter extends BasePresenter<ProSelActivity> {

    private final CommonModel mModel;

    public ProSelPresenter(ProSelActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void selPro(ProSelPojo pojo) {
        mModel.getProjectList(pojo).subscribe(new DefaultCallBackObserver<ProjectSelListBean>() {
            @Override
            public void responseSuccess(ProjectSelListBean bean) {
                mIView.selPro(bean);
            }

            @Override
            public void responseFail(ProjectSelListBean projectSelListBean) {

            }
        });
    }
}
