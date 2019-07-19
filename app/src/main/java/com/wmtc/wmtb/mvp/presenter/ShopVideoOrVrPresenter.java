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
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.VideoVrPojo;
import com.wmtc.wmtb.ui.activity.ShopVideoOrVrActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;
import static com.wmtc.wmtb.base.WmtApplication.ENDPOINT;

/**
 * Created by Obl on 2019/6/19.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopVideoOrVrPresenter extends BasePresenter<ShopVideoOrVrActivity> {

    private final CommonModel mModel;

    public ShopVideoOrVrPresenter(ShopVideoOrVrActivity activity) {
        super(activity);
        mModel = new CommonModel(WmtServer.class);
    }

    public void ossToken() {
        mModel.ossToken().subscribe(new DefaultCallBackObserver<OSSBean>(this) {
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

    public void saveVideoOrVr(String url, String type) {
        VideoVrPojo videoVrPojo = new VideoVrPojo();
        videoVrPojo.setShopId(WmtApplication.user_shopId);
        videoVrPojo.setUrl(url);
        videoVrPojo.setType(type);
        mModel.saveVideoOrVr(videoVrPojo).subscribe(new DefaultCallBackObserver<BaseBean>(this) {
            @Override
            public void responseSuccess(BaseBean bean) {

            }

            @Override
            public void responseFail(BaseBean bean) {

            }

            @Override
            public void onComplete() {
                super.onComplete();
                mIView.saveOk();
            }
        });
    }
}
