package com.wmtc.wmtb.mvp.presenter;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.BannerInfosBean;
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.ui.activity.ShopBannerActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;
import static com.wmtc.wmtb.base.WmtApplication.ENDPOINT;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopBannerPresenter extends BasePresenter<ShopBannerActivity> {

    private final CommonModel mModel;

    public ShopBannerPresenter(ShopBannerActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
    }

    public void shopBannerDelOne(String picId, int pos) {
        mModel.shopBannerDelOne(WmtApplication.user_shopId, picId).subscribe(new DefaultCallBackObserver<BaseBean>(new LoaddingErrorImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.delOk(pos);
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }
    public void ossToken() {
        mModel.ossToken().subscribe(new DefaultCallBackObserver<OSSBean>() {
            @Override
            public void responseSuccess(OSSBean bean) {
                OSSBean.DataBean mBean = bean.data;
                OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {

                    @Override
                    public OSSFederationToken getFederationToken() {
                        return new OSSFederationToken(mBean.AccessKeyId, mBean.AccessKeySecret, mBean.SecurityToken, mBean.Expiration);
                    }
                };
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(9); // 最大并发请求书，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                OSSClient mOss = new OSSClient(getApplicationContext(), ENDPOINT, credentialProvider, conf);
                mIView.ossClient(mOss);
            }

            @Override
            public void responseFail(OSSBean proListBean) {

            }
        });
    }

    public void shopBannerInfo() {
        mModel.shopBannerInfo(WmtApplication.user_shopId).subscribe(new DefaultCallBackObserver<BannerInfosBean>(new LoaddingErrorImplTip(mIView.mActivity)) {
            @Override
            public void responseSuccess(BannerInfosBean bean) {
                mIView.info(bean);
            }

            @Override
            public void responseFail(BannerInfosBean bean) {

            }
        });
    }

    public void shopBannerCreate(List<File> fileList, Map<String, String> stringMap) {
        HashMap<String, RequestBody> mBodyHashMap = new HashMap<>();
        if (stringMap != null && stringMap.size() > 0) {
            for (String key : stringMap.keySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                        stringMap.get(key) == null ? "" : stringMap.get(key));
                mBodyHashMap.put(key, requestBody);
            }
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            mBodyHashMap.put("pics\"; filename=\"" + file.getName(), fileBody);
        }
        mModel.shopBannerCreate(mBodyHashMap).subscribe(new DefaultCallBackObserver<BaseBean>() {
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
