package com.wmtc.wmtb.mvp.presenter;

import android.os.Bundle;

import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.W7Bean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.ui.fragment.W7Fragment;

import io.reactivex.observers.DefaultObserver;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.ui.activity.WebViewActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class W7Presenter extends BasePresenter<W7Fragment> {

    private final CommonModel mModel;

    public W7Presenter(W7Fragment iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void w7Login(String mobile, String pwd) {
        mModel.w7Login(mobile, pwd).subscribe(new DefaultObserver<W7Bean>() {
            @Override
            public void onNext(W7Bean w7Bean) {
                LogUtil.e(w7Bean);
                Bundle bundle = new Bundle();
                bundle.putString("cookie", (String) SharePreUtil.getData(mIView.mActivity, "cookie", ""));
                bundle.putString("url", "https://weixin.weimeitao.net/app/index" +
                        ".php?i=2&c=entry&m=ewei_shopv2&do=mobile");
                ActivityUtils.init().start(mIView.mActivity, WebViewActivity.class, "", bundle);
                mIView.response();
            }

            @Override
            public void onError(Throwable e) {
                Bundle bundle = new Bundle();
                bundle.putString("cookie", (String) SharePreUtil.getData(mIView.mActivity, "cookie", ""));
                bundle.putString("url", "https://weixin.weimeitao.net/app/index" +
                        ".php?i=2&c=entry&m=ewei_shopv2&do=mobile");
                ActivityUtils.init().start(mIView.mActivity, WebViewActivity.class, "", bundle);
                mIView.response();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
