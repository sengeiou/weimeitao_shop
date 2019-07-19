package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.github.florent37.viewanimator.ViewAnimator;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonPresenterActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ShopsBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2018/7/27.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class SplashActivity extends CommonPresenterActivity {

    private String mToken;
    private String mUid;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mToken = (String) SharePreUtil.getData(mActivity, "token", "");
        mUid = (String) SharePreUtil.getData(mActivity, "uid", "");
        BaseInitApplication.mHeardMap.put("token", mToken);
        BaseInitApplication.mHeardMap.put("type", "2");
        BaseInitApplication.mHeardMap.put("id", mUid);
        BaseInitApplication.mHeardMap.put("version", BuildConfig.VERSION_NAME);
        if (StringUtils.isNotBlank(mToken) && StringUtils.isNotBlank(mUid)) {
            mCommonPresenter.shopEnter(mUid);
        } else {
            ActivityUtils.init().start(this, LoginActivity.class);
        }
        ViewAnimator.animate(view.findViewById(R.id.image))
                .scale(1f, 1.1f)
                .duration(3000)
                .onStop(this::finish)
                .start();
        if (mBundle != null) {
            String id = mBundle.getString("id");
            String type = mBundle.getString("type");
            if ("order".equals(type)) {
                Disposable disposable =
                        Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                            Bundle bundleOrder = new Bundle();
                            bundleOrder.putString("id", id);
                            ActivityUtils.init().startWithTask(mActivity, OrderDetailsActivity.class, "", bundleOrder);
                        });
            }
        }
    }

    @Override
    public void shopEnter(ShopsBean bean) {
        List<ShopsBean.DataBean> data = bean.data;
        Bundle bundle = new Bundle();
        if (data != null && data.size() > 0) {
            WmtApplication.user_avatar = WmtApplication.url_host + data.get(0).userAvatar;
            bundle.putParcelable("shop", data.get(0));
            ActivityUtils.init().start(mActivity, ShopDoorAgainActivity.class, "", bundle);
        } else {
            bundle.putParcelable("shop", null);
            ActivityUtils.init().start(mActivity, ShopDoorAgainActivity.class, "", bundle);
        }
    }
}
