package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.PoolAmountBean;
import com.wmtc.wmtb.mvp.bean.PoolListBean;
import com.wmtc.wmtb.mvp.presenter.PlatformPresenter;
import com.wmtc.wmtb.ui.adapter.PlatformListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PlatformActivity extends SuperBaseActivity {
    @BindView(R.id.tvBack)
    TextView mTvBack;
    @BindView(R.id.tvSave)
    TextView mTvSave;
    @BindView(R.id.tvGetMoney)
    TextView mTvGetMoney;
    @BindView(R.id.tvGet)
    TextView mTvGet;
    @BindView(R.id.tvGetting)
    TextView mTvGetting;
    @BindView(R.id.tvGetError)
    TextView mTvGetError;
    private Unbinder mBind;
    private PlatformPresenter mPresenter;
    private PlatformListAdapter mAdapter;
    private int page = 1;
    public String status = "1";
    private boolean isLoad = false;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_platform;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewToolBar).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBind = ButterKnife.bind(this);
        mTvBack.setOnClickListener(v -> finish());
        mPresenter = new PlatformPresenter(this);
        mAdapter = new PlatformListAdapter(null, this);
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PoolListBean.DataBean.RecordsBean recordsBean = mAdapter.getData().get(position);
            if ("1".equals(status) && "1".equals(recordsBean.orderType)) {
                toShowDetail(recordsBean);
            } else {
                if ("".equals(recordsBean.couponConfigType)) {
                    if ("1".equals(recordsBean.orderType)) {
                        toShowDetail(recordsBean);
                    } else if ("2".equals(recordsBean.orderType)) {
                        toShowOffDetail(recordsBean);
                    }
                }
            }

        });
        mPresenter.getPoolList(status, page);
        mRecyclerView.setAdapter(mAdapter);
        showLoading();
        mPresenter.getAmountMoney();
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            netLoad(status, ++page);
        });
        mTvGet.setOnClickListener(v -> {
            if (!isLoad) {
                showLoading();
                setEnable(false, true, true);
            }
            netLoad("1", 1);
        });
        mTvGetting.setOnClickListener(v -> {
            if (!isLoad) {
                showLoading();
                setEnable(true, false, true);
            }

            netLoad("2", 1);
        });
        mTvGetError.setOnClickListener(v -> {
            if (!isLoad) {
                showLoading();
                setEnable(true, true, false);
            }
            netLoad("3", 1);
        });
        mTvSave.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, PlatformRemainActivity.class, "");
        });
    }

    private void toShowOffDetail(PoolListBean.DataBean.RecordsBean recordsBean) {
        Bundle bundle = new Bundle();
        bundle.putString("id", recordsBean.orderId);
        bundle.putInt("type", 1);
        ActivityUtils.init().start(mActivity, OrderRecordDetailActivity.class, "线下明细", bundle);
    }

    private void toShowDetail(PoolListBean.DataBean.RecordsBean recordsBean) {
        Bundle bundle = new Bundle();
        bundle.putString("id", recordsBean.orderId);
        ActivityUtils.init().start(mActivity, OrderDetailsActivity.class, "", bundle);
    }

    private void netLoad(String status, int page) {
        if (!isLoad) {
            this.status = status;
            this.page = page;
            mPresenter.getPoolList(status, page);
            isLoad = true;
        }
    }

    public void setEnable(boolean s1, boolean s2, boolean s3) {
        mTvGet.setEnabled(s1);
        mTvGetting.setEnabled(s2);
        mTvGetError.setEnabled(s3);
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        netLoad(status, 1);
    }

    public void getPoolList(PoolListBean bean) {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
        PoolListBean.DataBean data = bean.data;
        if (page == 1) {
            if (data.records != null && data.size > 0) {
                mAdapter.setNewData(data.records);
            }
        } else {
            if (data.records != null && data.size > 0) {
                mAdapter.addData(data.records);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    public void complete() {
        isLoad = false;
    }

    public void amountMoney(PoolAmountBean bean) {
        mTvGetMoney.setText(StringUtils.init().fixNullStrMoney(bean.data.totalAmount + "", "￥"));
    }
}
