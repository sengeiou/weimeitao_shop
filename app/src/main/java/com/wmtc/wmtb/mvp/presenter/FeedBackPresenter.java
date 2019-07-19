package com.wmtc.wmtb.mvp.presenter;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.DefaultCallBackObserver;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.OSSBean;
import com.wmtc.wmtb.mvp.model.CommonModel;
import com.wmtc.wmtb.mvp.pojo.AppointPojo;
import com.wmtc.wmtb.mvp.pojo.FeedBackPojo;
import com.wmtc.wmtb.ui.activity.FeedBackActivity;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;
import static com.wmtc.wmtb.base.WmtApplication.ENDPOINT;

/**
 * Created by Obl on 2019/4/8.
 * com.wmtc.wmtb.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FeedBackPresenter extends BasePresenter<FeedBackActivity> {

    private final CommonModel mModel;

    public FeedBackPresenter(FeedBackActivity iView) {
        super(iView);
        mModel = new CommonModel(WmtServer.class);
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

    public void addFeedback(FeedBackPojo pojo) {
        pojo.setUserId(BaseInitApplication.mHeardMap.get("id"));
        pojo.setUserType("2");
        mModel.addFeedback(pojo).subscribe(new DefaultCallBackObserver<BaseBean>(new GetImplTip(mIView)) {
            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.netOk();
            }

            @Override
            public void responseFail(BaseBean bean) {

            }
        });
    }
}
