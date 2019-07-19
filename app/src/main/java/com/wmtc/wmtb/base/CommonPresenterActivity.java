package com.wmtc.wmtb.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.presenter.CommonPresenter;
import com.wmtc.wmtb.ui.activity.ShopDoorAgainActivity;

import java.util.ArrayList;
import java.util.List;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/3/23.
 * com.wmtc.wmtb.base
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public abstract class CommonPresenterActivity extends SuperBaseActivity {

    public CommonPresenter mCommonPresenter;

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mCommonPresenter = new CommonPresenter(this);
    }

    public void shopEnter(ShopsBean bean) {

    }

    public void createOk() {

    }
}
