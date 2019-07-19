package com.wmtc.wmtb.ui.activity;

import android.view.View;
import android.widget.FrameLayout;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.CardListBean;
import com.wmtc.wmtb.mvp.event.SelCardEvent;
import com.wmtc.wmtb.mvp.event.AddCardOkEvent;
import com.wmtc.wmtb.mvp.presenter.CardListPresenter;
import com.wmtc.wmtb.ui.adapter.CardListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CardListActivity extends CommonWmtActivity {

    private CardListPresenter mPresenter;
    private CardListAdapter mAdapter;

    @Override
    public int initAddLayout() {
        return R.layout.layout_refresh_white_nofoot;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mAdapter = new CardListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new CardListPresenter(this);
        showLoading();
        mPresenter.getCard();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CardListBean.DataBean dataBean = mAdapter.getData().get(position);
            EventBus.getDefault().post(new SelCardEvent(dataBean));
            finish();
        });
        mTvToolRight.setText("添加");
        EventBus.getDefault().register(this);
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        ActivityUtils.init().start(mActivity, AddCardActivity.class, "添加银行卡");
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.getCard();
    }

    @Subscribe
    public void onEvent(AddCardOkEvent event) {
        mPresenter.getCard();
    }

    public void getCardDate(CardListBean bean) {
        responseSuccess();
        if (bean.data != null && bean.data.size() > 0) {
            mAdapter.setNewData(bean.data);
        } else {
            showEmpty();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
