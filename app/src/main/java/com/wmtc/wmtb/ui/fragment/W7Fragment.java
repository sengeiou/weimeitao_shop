package com.wmtc.wmtb.ui.fragment;

import android.view.View;

import com.wmtc.wmtb.MainActivity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.presenter.W7Presenter;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.listener.observer.CustomObservable;
import top.jplayer.baseprolibrary.listener.observer.CustomObserver;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class W7Fragment extends SuperBaseFragment implements CustomObserver {

    private W7Presenter mW7Presenter;
    private DialogLoading mLoading;

    @Override
    public int initLayout() {
        return R.layout.fragment_w7;
    }

    @Override
    protected void initData(View rootView) {
        BaseInitApplication.mObserverMap.put("func", this);
        mW7Presenter = new W7Presenter(this);
        mLoading = new DialogLoading(mActivity);
        showLoading();
        mW7Presenter.w7Login(WmtApplication.user_phone, WmtApplication.user_pwd);
    }
    @Override
    protected void onShowFragment() {
        super.onShowFragment();
        LogUtil.e("show");
        showLoading();
        mW7Presenter.w7Login(WmtApplication.user_phone, WmtApplication.user_pwd);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        if (mLoading != null) {
            mLoading.show();
        }
    }

    public void response() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    @Override
    public void update(CustomObservable o, Object arg) {
        LogUtil.e(arg);
        MainActivity activity = (MainActivity) mActivity;
        activity.onTabClick(activity.mViewList.get(0), 0);
    }
}
