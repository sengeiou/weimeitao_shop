package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.TixianBean;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.presenter.ApplyListPresenter;
import com.wmtc.wmtb.ui.adapter.ApplyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

/**
 * Created by Obl on 2019/4/1.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ApplyListActivity extends CommonWmtActivity {

    private ApplyListAdapter mAdapter;
    //记录需扩展位置
    private ArrayList<Integer> expandPos;
    private Integer curPos = 0;
    private ApplyListPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_applylist;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        expandPos = new ArrayList<>();
        mTvToolRight.setVisibility(View.INVISIBLE);
        mAdapter = new ApplyListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ApplyListPresenter(this);
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        showLoading();
        mPresenter.applyList(pojo);
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        OrderPojo pojo = new OrderPojo();
        pojo.setShopId(WmtApplication.user_shopId);
        mPresenter.applyList(pojo);
    }

    @SuppressLint("CheckResult")
    public void applyList(TixianBean bean) {
        responseSuccess();
        ArrayList<MultiItemEntity> itemEntities = generateData(bean.data);
        mAdapter.setNewData(itemEntities);
        Observable.fromIterable(expandPos).subscribe(integer -> mAdapter.expand(integer));
    }

    @SuppressLint("CheckResult")
    private ArrayList<MultiItemEntity> generateData(List<TixianBean.DataBean> bean) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        expandPos.clear();
        curPos = 0;
        Observable.fromIterable(bean).subscribe(resultBeans -> {
            res.add(resultBeans);
            expandPos.add(curPos);
            List<TixianBean.DataBean.ListBean> list = resultBeans.list;
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
