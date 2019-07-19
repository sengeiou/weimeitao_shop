package com.wmtc.wmtb.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ProListBean;
import com.wmtc.wmtb.mvp.pojo.ProListPojo;
import com.wmtc.wmtb.mvp.pojo.ProjectStatusPojo;
import com.wmtc.wmtb.mvp.presenter.ProListPresenter;
import com.wmtc.wmtb.ui.activity.ProjectActivity;
import com.wmtc.wmtb.ui.adapter.ProListAdapter;

import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProjectListFragment extends SuperBaseFragment {

    private ProListPresenter mPresenter;
    private ProListAdapter mAdapter;
    private String status;
    private ProListPojo mPojo;

    @Override
    public int initLayout() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mAdapter = new ProListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ProListPresenter(this);
        mPojo = new ProListPojo();
        showLoading();
        mPojo.setShopId(WmtApplication.user_shopId);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ProListBean.DataBean dataBean = mAdapter.getData().get(position);
            ProjectStatusPojo pojo = new ProjectStatusPojo();
            pojo.setProjectId(dataBean.projectId);

            if (view.getId() == R.id.tvDel) {
                pojo.setStatus("0");
                mPresenter.updateProjectStatus(pojo);
            } else if (view.getId() == R.id.tvUp) {
                pojo.setStatus("1");
                mPresenter.updateProjectStatus(pojo);
            } else if (view.getId() == R.id.tvDown) {
                pojo.setStatus("7");
                mPresenter.updateProjectStatus(pojo);
            } else if (view.getId() == R.id.tvEdit) {
                Bundle bundle = new Bundle();
                bundle.putString("edit", "1");
                bundle.putString("proId", dataBean.projectId);
                ActivityUtils.init().start(mActivity, ProjectActivity.class, "录入项目", bundle);
            }
            return false;
        });
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPojo.setStatus(status);
        mPresenter.projectList(mPojo);
    }

    public void projectList(ProListBean bean) {
        responseSuccess();
        if (bean.data != null && bean.data.size() > 0) {
            mAdapter.setNewData(bean.data);
        } else {
            showEmpty();
        }
    }

    public void setStatus(String status) {
        this.status = status;
        mPojo.setStatus(status);
        mPresenter.projectList(mPojo);
    }

    public void updateStatus() {
        showLoading();
        mPresenter.projectList(mPojo);
    }
}
