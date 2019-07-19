package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.WmtServer;
import com.wmtc.wmtb.mvp.bean.W7Bean;
import com.wmtc.wmtb.mvp.model.CommonModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.observers.DefaultObserver;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.activity.WebViewActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

/**
 * Created by Obl on 2019/5/27.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class VipActivity extends SuperBaseActivity {
    @BindView(R.id.tvBack)
    TextView mTvBack;
    @BindView(R.id.ivVipType)
    ImageView mIvVipType;
    @BindView(R.id.ivVipBuy)
    ImageView mIvVipBuy;
    @BindView(R.id.tvVipName)
    TextView mTvVipName;
    @BindView(R.id.ivUserAvatar)
    PolygonImageView mIvUserAvatar;
    @BindView(R.id.tvUserName)
    TextView mTvUserName;
    @BindView(R.id.tvUserTime)
    TextView mTvUserTime;
    private Unbinder mBind;
    private CommonModel mModel;
    private DialogLoading mLoading;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_vip;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        String level = mBundle.getString("level");
        String levelName = mBundle.getString("levelName");
        String levelDateStr = "有效期至：" + mBundle.getString("levelDateStr");
        mModel = new CommonModel(WmtServer.class);
        mBind = ButterKnife.bind(this);
        mTvUserTime.setText(levelDateStr);
        if ("1".equals(level)) {
            mIvVipType.setImageDrawable(getResources().getDrawable(R.drawable.vip_1));
        } else if ("2".equals(level)) {
            mIvVipType.setImageDrawable(getResources().getDrawable(R.drawable.vip_2));
        } else if ("3".equals(level)) {
            mIvVipType.setImageDrawable(getResources().getDrawable(R.drawable.vip_3));
        } else if ("0".equals(level)) {
            mIvVipType.setImageDrawable(getResources().getDrawable(R.drawable.vip_0));
            mTvUserTime.setText("购买年卡VIP，享每月返券、订单返现特权");
            mIvVipBuy.setVisibility(View.VISIBLE);
            mIvVipBuy.setOnClickListener(v -> {
                w7Login(WmtApplication.user_phone, WmtApplication.user_pwd);
            });
        }
        Glide.with(mActivity)
                .load(WmtApplication.user_avatar)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(mIvUserAvatar);
        mTvVipName.setText(levelName);
        mTvUserName.setText(mBundle.getString("name"));
        mTvBack.setOnClickListener(v -> finish());
    }

    public void w7Login(String mobile, String pwd) {
        mLoading = new DialogLoading(mActivity);
        mLoading.show();
        mModel.w7Login(mobile, pwd).subscribe(new DefaultObserver<W7Bean>() {
            @Override
            public void onNext(W7Bean w7Bean) {
                LogUtil.e(w7Bean);
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                Bundle bundle = new Bundle();
                bundle.putString("cookie", (String) SharePreUtil.getData(mActivity, "cookie", ""));
                bundle.putString("url", mBundle.getString("levelUrl"));
                ActivityUtils.init().start(mActivity, WebViewActivity.class, "", bundle);
            }

            @Override
            public void onError(Throwable e) {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                Bundle bundle = new Bundle();
                bundle.putString("cookie", (String) SharePreUtil.getData(mActivity, "cookie", ""));
                bundle.putString("url", mBundle.getString("levelUrl"));
                ActivityUtils.init().start(mActivity, WebViewActivity.class, "", bundle);
            }

            @Override
            public void onComplete() {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
