package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.BindListBean;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;
import com.wmtc.wmtb.mvp.presenter.BindRecordPresenter;
import com.wmtc.wmtb.mvp.presenter.CollectionPresenter;
import com.wmtc.wmtb.ui.adapter.BindRecordListAdapter;
import com.wmtc.wmtb.ui.adapter.OrderRecordListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.DateUtils;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class BindRecordListActivity extends CommonWmtActivity {

    private BindRecordPresenter mPresenter;
    private ArrayList<Integer> expandPos;
    private Integer curPos = 0;
    private BindRecordListAdapter mAdapter;
    private int page = 0;
    private CardView mCardView;

    @Override
    public int initAddLayout() {
        return R.layout.activity_order_record_list;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        expandPos = new ArrayList<>();
        mTvToolRight.setVisibility(View.INVISIBLE);
        mPresenter = new BindRecordPresenter(this);
        showLoading();
        netResponse(page);
        mAdapter = new BindRecordListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            netResponse(++page);
        });
        mCardView = rootView.findViewById(R.id.cardView);
        mCardView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        expandPos.clear();
        page = 0;
        netResponse(0);
    }

    private void netResponse(int page) {
        mPresenter.getBangDingNumList(page + "");
    }

    @SuppressLint("CheckResult")
    public void getBangDingNumList(BindListBean bean) {
        responseSuccess();
        if (page == 0) {
            if (bean.data.list != null && bean.data.list.size() > 0) {
                String currentDate = DateUtils.getCurrentDate();
                if (bean.data.list.size() > 0) {
                    if (!currentDate.equals(bean.data.list.get(0).date)) {
                        addTodayBean(bean, currentDate);
                    } else {
                        bean.data.list.get(0).totalNum = bean.data.totalNum;
                        bean.data.list.get(0).date = currentDate + " (今天)";
                    }
                } else {
                    addTodayBean(bean, currentDate);
                }
                ArrayList<MultiItemEntity> itemEntities = generateData(bean.data.list);
                mAdapter.setNewData(itemEntities);
                Observable.fromIterable(expandPos).subscribe(integer -> mAdapter.expand(integer));
            } else {
                showEmpty();
            }
        } else {
            mSmartRefreshLayout.finishLoadMore();
            if (bean.data.list != null && bean.data.list.size() > 0) {
                ArrayList<MultiItemEntity> itemEntities = generateData(bean.data.list);
                mAdapter.addData(itemEntities);
                Observable.fromIterable(expandPos).subscribe(integer -> mAdapter.expand(integer));
            }
        }
    }

    private void addTodayBean(BindListBean bean, String currentDate) {
        List<BindListBean.DataBean.ListBeanX> list = bean.data.list;
        BindListBean.DataBean.ListBeanX element = new BindListBean.DataBean.ListBeanX();
        element.totalNum = bean.data.totalNum;
        element.date = currentDate + " (今天)";
        element.num = 0;
        element.isAdd = true;
        list.add(0, element);
    }

    @SuppressLint("CheckResult")
    private ArrayList<MultiItemEntity> generateData(List<BindListBean.DataBean.ListBeanX> bean) {
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
            List<BindListBean.DataBean.ListBeanX.ListBean> list = resultBeans.list;
            if (list != null && list.size() > 0) {
                curPos = list.size() + 1 + curPos;
                Observable.fromIterable(list).subscribe(resultBeans::addSubItem);
            } else {
                curPos = curPos + 1;
            }
        });
        return res;
    }
}
