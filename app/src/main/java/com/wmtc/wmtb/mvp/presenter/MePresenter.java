package com.wmtc.wmtb.mvp.presenter;

import android.os.Bundle;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.W7Bean;
import com.wmtc.wmtb.mvp.model.LoginModel;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.ui.fragment.MeFragment;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DefaultObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;
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
public class MePresenter extends BasePresenter<MeFragment> {

    private LoginModel mLoginModel;

    public MePresenter(MeFragment iView) {
        super(iView);
        mLoginModel = new LoginModel(WmtServer.class);
    }

    public void shopEnter(String uid) {
        mLoginModel.shopEnter(uid).subscribe(new DefaultCallBackObserver<ShopsBean>() {

            @Override
            public void responseSuccess(ShopsBean bean) {
                mIView.shopEnter(bean);
            }

            @Override
            public void responseFail(ShopsBean bean) {

            }
        });
    }

    public void avatar(List<File> fileList, Map<String, String> stringMap) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();

        for (String key : stringMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    stringMap.get(key) == null ? "" : stringMap.get(key));
            mBodyHashMap.put(key, requestBody);
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            mBodyHashMap.put("avatar\"; filename=\"" + file.getName(), fileBody);
        }
        mLoginModel.avatar(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity)) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.avatar();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    public void logout(OrderPojo pojo) {
        mLoginModel.logout(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView.mActivity)) {

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.logoutDate(bean);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }

}
