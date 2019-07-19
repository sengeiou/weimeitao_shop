package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.ProListBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.ProListPojo;
import com.wmtc.wmtb.mvp.pojo.ProjectStatusPojo;
import com.wmtc.wmtb.ui.fragment.ProjectListFragment;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProListPresenter extends BasePresenter<ProjectListFragment> {
    private CommonModel mCommonModel;

    public ProListPresenter(ProjectListFragment iView) {
        super(iView);
        mCommonModel = new CommonModel(WmtServer.class);
    }

    public void projectList(ProListPojo pojo) {
        mCommonModel.projectList(pojo).subscribe(new DefaultCallBackObserver<ProListBean>() {
            @Override
            public void responseSuccess(ProListBean proListBean) {
                mIView.projectList(proListBean);
            }

            @Override
            public void responseFail(ProListBean proListBean) {

            }
        });
    }

    public void updateProjectStatus(ProjectStatusPojo pojo) {
        mCommonModel.updateProjectStatus(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.updateStatus();
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

}
