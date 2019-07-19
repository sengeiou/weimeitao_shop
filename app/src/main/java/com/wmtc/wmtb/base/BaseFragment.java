package com.wmtc.wmtb.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wmtc.wmtb.R;

import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.widgets.MultipleStatusView;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.base
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public abstract class BaseFragment extends SuperBaseFragment implements IContract.IView {
    public MultipleStatusView mMultipleStatusView;
    public SmartRefreshLayout smartRefreshLayout;
    public TextView tvBarTitle;
    public ImageView ivBarSearch;

    @Override

    protected void initData(View rootView) {
    }

    @Override
    public abstract int initLayout();


    @Override
    public void showError() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showError();
        }

    }

    @Override
    public void showLoading() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showLoading();
        }
    }

    @Override
    public void showEmpty() {

    }
}