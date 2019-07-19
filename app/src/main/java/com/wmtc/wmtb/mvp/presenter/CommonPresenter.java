package com.wmtc.wmtb.mvp.presenter;

import android.annotation.SuppressLint;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.CommonPresenterActivity;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.ProListBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.model.LoginModel;
import com.wmtc.wmtb.mvp.pojo.ProListPojo;
import com.wmtc.wmtb.ui.activity.LoginActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2019/3/23.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CommonPresenter extends BasePresenter<CommonPresenterActivity> {

    private final LoginModel mLoginModel;
    private HashMap<String, RequestBody> mBodyHashMap;

    public CommonPresenter(CommonPresenterActivity iView) {
        super(iView);
        mLoginModel = new LoginModel(WmtServer.class);
    }


    public void createShop(List<File> fileList, Map<String, String> stringMap) {
        mBodyHashMap = new HashMap<>();

        for (String key : stringMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    stringMap.get(key) == null ? "" : stringMap.get(key));
            mBodyHashMap.put(key, requestBody);
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            mBodyHashMap.put("pics\"; filename=\"" + file.getName(), fileBody);
        }
        mLoginModel.createShop(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.createOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }

    @SuppressLint("CheckResult")
    public void shopEnter(String uid) {
        mLoginModel.shopEnter(uid).subscribe(new DefaultCallBackObserver<ShopsBean>(new GetImplTip(mIView)) {

            @Override
            public void responseSuccess(ShopsBean bean) {
                mIView.shopEnter(bean);
            }

            @Override
            public void responseFail(ShopsBean bean) {
                Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
                    ActivityUtils.init().start(mIView, LoginActivity.class);

                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
                    ActivityUtils.init().start(mIView, LoginActivity.class);

                });
            }
        });
    }

    @SuppressLint("CheckResult")
    public void shopEnter() {
        mLoginModel.shopEnter(BaseInitApplication.mHeardMap.get("id")).subscribe(new DefaultCallBackObserver<ShopsBean>(new LoaddingErrorImplTip(mIView)) {

            @Override
            public void responseSuccess(ShopsBean bean) {
                mIView.shopEnter(bean);
            }

            @Override
            public void responseFail(ShopsBean bean) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }
        });
    }
}
