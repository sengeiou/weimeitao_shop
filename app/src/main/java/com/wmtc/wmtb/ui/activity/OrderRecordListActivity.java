package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;
import com.wmtc.wmtb.mvp.presenter.CollectionPresenter;
import com.wmtc.wmtb.ui.adapter.OrderRecordListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderRecordListActivity extends CommonWmtActivity {

    private CollectionPresenter mPresenter;
    private ArrayList<Integer> expandPos;
    private Integer curPos = 0;
    private OrderRecordListAdapter mAdapter;
    private int page = 0;
    private TextView mTvOnLine;
    private TextView mTvOffLine;
    private TextView mTvBind;
    private int type = 0;
    private boolean isLoadding = false;

    @Override
    public int initAddLayout() {
        return R.layout.activity_order_record_list;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        expandPos = new ArrayList<>();
        mTvToolRight.setVisibility(View.INVISIBLE);
        mPresenter = new CollectionPresenter(this);
        showLoading();
        netResponse(page);
        mAdapter = new OrderRecordListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            netResponse(++page);
        });

        mTvOnLine = rootView.findViewById(R.id.tvOnLine);
        mTvOffLine = rootView.findViewById(R.id.tvOffLine);
        mTvBind = rootView.findViewById(R.id.tvBind);

        mTvOnLine.setOnClickListener(v -> {
            if (!isLoadding) {
                type = 0;
                expandPos.clear();
                mAdapter.setNewData(null);
                showLoading();
                page = 0;
                netResponse(page);
                setEnable(false, true, true);
            }
        });
        mTvOffLine.setOnClickListener(v -> {
            if (!isLoadding) {
                type = 1;
                expandPos.clear();
                mAdapter.setNewData(null);
                showLoading();
                page = 0;
                netResponse(page);
                setEnable(true, false, true);
            }
        });
        mTvBind.setOnClickListener(v -> {
            if (!isLoadding) {
                type = 2;
                expandPos.clear();
                mAdapter.setNewData(null);
                showLoading();
                page = 0;
                netResponse(page);
                setEnable(true, true, false);
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.rlItem) {
                Bundle bundle = new Bundle();
                MultiItemEntity multiItemEntity = mAdapter.getData().get(position);
                if (multiItemEntity instanceof ShoukuanBean.DataBean.ListBean) {
                    ShoukuanBean.DataBean.ListBean bean = (ShoukuanBean.DataBean.ListBean) multiItemEntity;
                    String title = "线上明细";
                    if (type == 1) {
                        title = "线下明细";
                        bundle.putString("id", bean.offlineOrderId);
                    } else if (type == 0) {
                        bundle.putString("id", bean.orderId);
                    } else if (type == 2) {
                        title = "绑定明细";
                        bundle.putString("id", bean.orderId);
                    }
                    bundle.putInt("type", type);
                    ActivityUtils.init().start(mActivity, OrderRecordDetailActivity.class, title, bundle);
                }
            }
            return false;
        });

    }

    public void setEnable(boolean on, boolean off, boolean bind) {
        mTvOnLine.setEnabled(on);
        mTvOffLine.setEnabled(off);
        mTvBind.setEnabled(bind);
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        expandPos.clear();
        page = 0;
        netResponse(0);
    }

    private void netResponse(int page) {
        if (!isLoadding) {
            if (type == 0) {
                mPresenter.getShouKuanRecord(page + "");
            } else if (type == 1) {
                mPresenter.getOfflineShouKuanRecord(page + "");
            } else {
                mPresenter.getBangDingList(page + "");
            }
        }
        isLoadding = true;
    }

    @SuppressLint("CheckResult")
    public void  getShouKuanRecordDate(ShoukuanBean bean) {
        isLoadding = false;
        responseSuccess();
        if (page == 0) {
            if (bean.data != null) {
                String currentDate = DateUtils.getCurrentDate();
                if (bean.data.size() > 0) {
                    if (!currentDate.equals(bean.data.get(0).date)) {
                        addTodayBean(bean, currentDate);
                    }
                } else {
                    addTodayBean(bean, currentDate);
                }
                ArrayList<MultiItemEntity> itemEntities = generateData(bean.data);
                mAdapter.setNewData(itemEntities);
                Observable.fromIterable(expandPos).subscribe(integer -> mAdapter.expand(integer));
            } else {
                showEmpty();
            }
        } else {
            mSmartRefreshLayout.finishLoadMore();
            if (bean.data != null && bean.data.size() > 0) {
                ArrayList<MultiItemEntity> itemEntities = generateData(bean.data);
                mAdapter.addData(itemEntities);
                Observable.fromIterable(expandPos).subscribe(integer -> mAdapter.expand(integer));
            }
        }
    }

    private void addTodayBean(ShoukuanBean bean, String currentDate) {
        ShoukuanBean.DataBean element = new ShoukuanBean.DataBean();
        element.totalAmount = "0";
        element.date = currentDate + " (今天)";
        element.num = "0";
        element.isAdd = true;
        bean.data.add(0, element);
    }

    @SuppressLint("CheckResult")
    private ArrayList<MultiItemEntity> generateData(List<ShoukuanBean.DataBean> bean) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        if (page == 0) {
            expandPos.clear();
            curPos = 0;
        } else {
            curPos = mAdapter.getData().size();
        }
        Observable.fromIterable(bean).subscribe(resultBeans -> {
            res.add(resultBeans);
            expandPos.add(curPos);
            List<ShoukuanBean.DataBean.ListBean> list = resultBeans.list;
            if (list != null && list.size() > 0) {
                curPos = list.size() + 1 + curPos;
                Observable.fromIterable(list).subscribe(resultBeans::addSubItem);
            } else {
                curPos = curPos + 1;
            }
        });
        return res;
    }


    public void complete() {
        isLoadding = false;
    }
}
