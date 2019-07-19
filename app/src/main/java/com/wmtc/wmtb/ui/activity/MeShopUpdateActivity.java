package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.event.VideoVrUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

/**
 * Created by Obl on 2019/6/19.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MeShopUpdateActivity extends CommonWmtActivity {
    @BindView(R.id.ivAvatar)
    PolygonImageView mIvAvatar;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvSubTitle)
    TextView mTvSubTitle;
    @BindView(R.id.llPic)
    LinearLayout mLlPic;
    @BindView(R.id.llVideo)
    LinearLayout mLlVideo;
    @BindView(R.id.llVR)
    LinearLayout mLlVR;
    private Unbinder mBind;
    private ShopsBean.DataBean mShop;

    @Override
    public int initAddLayout() {
        return R.layout.activity_shop_update;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mShop = mBundle.getParcelable("shop");
        LogUtil.e(mShop);
        if (mShop != null) {
            mTvTitle.setText(mShop.shopName);
            mTvSubTitle.setText(String.format(Locale.CHINA, "绑定手机号：%s", mShop.shopPhone));
        }
        Glide.with(mActivity)
                .load(WmtApplication.user_avatar)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(mIvAvatar);
        mLlPic.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, ShopBannerActivity.class, "设置门店主图");
        });
        mLlVideo.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", "门店视频展示");
            bundle.putString("url",  mShop.videoUrl);
            bundle.putString("type", "添加门店视频展示");
            bundle.putString("subTitle", "上传门店视频，让消费者直观的了解您的门店环境及服务特色。视频时长不要超过30s");
            ActivityUtils.init().start(mActivity, ShopVideoOrVrActivity.class, "设置门店视频", bundle);
        });
        mLlVR.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", "门店VR展示");
            bundle.putString("url",   mShop.vrUrl);
            bundle.putString("type", "添加门店VR展示");
            bundle.putString("subTitle", "上传门店VR图片，让消费者更加直观的了解您门店的环境。");
            ActivityUtils.init().start(mActivity, ShopVideoOrVrActivity.class, "设置门店VR", bundle);
        });
    }


    @Subscribe
    public void onEvnet(VideoVrUpdateEvent event) {
        if (StringUtils.isNotBlank(event.vrUrl)) {
            mShop.vrUrl = event.vrUrl;
        }
        if (StringUtils.isNotBlank(event.videoUrl)) {
            mShop.videoUrl = event.videoUrl;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mBind.unbind();
    }
}
