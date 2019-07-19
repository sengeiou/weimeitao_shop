package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.PoolAmountBean;
import com.wmtc.wmtb.mvp.bean.PoolRemainListBean;
import com.wmtc.wmtb.mvp.presenter.PlatformRemainPresenter;
import com.wmtc.wmtb.ui.adapter.PlatformRemainAdapter;

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
public class PlatformRemainActivity extends SuperBaseActivity {

    @BindView(R.id.tvBack)
    TextView mTvBack;
    @BindView(R.id.tvSave)
    TextView mTvSave;
    @BindView(R.id.tvGetMoney)
    TextView mTvGetMoney;
    private PlatformRemainPresenter mPresenter;
    private int page = 1;
    private Unbinder mBind;
    private PlatformRemainAdapter mAdapter;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_platform_remain;
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
        mPresenter = new PlatformRemainPresenter(this);
        mPresenter.getAmountMoney();
        mPresenter.getRemainPoolList(page);
        mTvBack.setOnClickListener(v -> finish());
        showLoading();
        mPresenter.getRemainPoolList(page);
        mAdapter = new PlatformRemainAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.getRemainPoolList(++page);
        });
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PoolRemainListBean.DataBean.RecordsBean recordsBean = mAdapter.getData().get(position);
            if (recordsBean.orderId != null && recordsBean.orderId.length() > 1) {
                Bundle bundle = new Bundle();
                bundle.putString("id", recordsBean.orderId);
                bundle.putInt("type", 2);
                ActivityUtils.init().start(mActivity, OrderRecordDetailActivity.class, "绑定明细", bundle);
            }
        });
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        page = 1;
        mPresenter.getRemainPoolList(page);
    }

    public void getPoolRemainList(PoolRemainListBean bean) {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
        if (page == 1) {
            if (bean.data.records != null && bean.data.records.size() > 0) {
                mAdapter.setNewData(bean.data.records);
            }
        } else {
            if (bean.data.records != null && bean.data.records.size() > 0) {
                mAdapter.addData(bean.data.records);
            }
        }
    }

    public void amountMoney(PoolAmountBean bean) {
        mTvGetMoney.setText(StringUtils.init().fixNullStrMoney(bean.data.totalAmount + "", "￥"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
