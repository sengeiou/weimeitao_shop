package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.TeachBean;
import com.wmtc.wmtb.mvp.event.TeachUpdateEvent;
import com.wmtc.wmtb.mvp.pojo.TeachInfoPojo;
import com.wmtc.wmtb.mvp.presenter.TeachListPresenter;
import com.wmtc.wmtb.ui.adapter.TeachListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/3/27.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TeachListActivity extends CommonWmtActivity {

    private TeachListAdapter mAdapter;
    private TeachListPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.layout_refresh_white_nofoot;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mTvToolRight.setText("+添加技师");
        EventBus.getDefault().register(this);
        mAdapter = new TeachListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new TeachListPresenter(this);
        showLoading();
        mPresenter.teachList();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TeachBean.DataBean dataBean = mAdapter.getData().get(position);
            if (view.getId() == R.id.tvEdit) {
                Bundle bundle = new Bundle();
                bundle.putString("shopName", mBundle.getString("shopName"));
                bundle.putString("id", dataBean.id + "");
                bundle.putBoolean("isEdit", true);
                ActivityUtils.init().start(mActivity, CreateTeachActivity.class, "编辑技师", bundle);
            } else if (view.getId() == R.id.tvDel) {
                TeachInfoPojo pojo = new TeachInfoPojo();
                pojo.setId(dataBean.id + "");
                pojo.setStatus("0");
                mPresenter.updateTechnicianDetail(pojo);
            } else if (view.getId() == R.id.tvWorkTime) {
                Bundle bundle = new Bundle();
                bundle.putString("shopName", mBundle.getString("shopName"));
                bundle.putString("id", dataBean.id + "");
                bundle.putString("workTime", dataBean.workTime);
                bundle.putString("restDay", dataBean.restDay);
                bundle.putString("status", dataBean.status+"");
                ActivityUtils.init().start(mActivity, WorkTimeActivity.class, "工作时间", bundle);
            }
            return false;
        });
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        Bundle bundle = new Bundle();
        bundle.putString("shopName", mBundle.getString("shopName"));
        ActivityUtils.init().start(mActivity, CreateTeachActivity.class, "添加技师", bundle);
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.teachList();
    }

    @Subscribe
    public void onEvent(TeachUpdateEvent event) {
        showLoading();
        mPresenter.teachList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void teachList(TeachBean teachBean) {
        responseSuccess();
        if (teachBean.data != null && teachBean.data.size() > 0) {
            mAdapter.setNewData(teachBean.data);
        } else {
            showEmpty();
        }
    }

    public void delOk() {
        mPresenter.teachList();
    }
}
