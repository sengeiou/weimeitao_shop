package com.wmtc.wmtb.mvp.presenter;

import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.ui.activity.ShopTextEditActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopTextEditPresenter extends BasePresenter<ShopTextEditActivity> {

    private final CommonModel mModel;

    public ShopTextEditPresenter(ShopTextEditActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void createShop(Map<String, String> stringMap) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();

        for (String key : stringMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    stringMap.get(key) == null ? "" : stringMap.get(key));
            mBodyHashMap.put(key, requestBody);
        }

        mModel.createShop(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>(new PostImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean baseBean) {
                mIView.netOk();
            }

            @Override
            public void responseFail(BaseBean baseBean) {

            }
        });
    }
}
