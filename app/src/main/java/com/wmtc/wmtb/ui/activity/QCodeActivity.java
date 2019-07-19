package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.event.ShareAllEvent;
import com.wmtc.wmtb.mvp.event.ShareOneEvent;
import com.wmtc.wmtb.ui.dialog.ShareDialog;
import com.wmtc.wmtb.wxapi.WXShare;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.SizeUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class QCodeActivity extends CommonWmtActivity {

    private ImageView mIvQCode;
    private DialogLoading mLoading;
    private Bitmap mBitmap;
    private String mSaveFilePath;
    private TextView mTvToUser;
    private TextView mTvToPay;
    private TextView mTvToShop;
    private boolean userSel = true;
    private Bitmap mBitmapUser;
    private Bitmap mBitmapShop;
    private Bitmap mBitmapPay;
    private CardView mCard;

    @Override
    public int initAddLayout() {
        return R.layout.activity_qcode;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mIvQCode = rootView.findViewById(R.id.ivQCode);
        mTvToShop = rootView.findViewById(R.id.tvToShop);
        mTvToUser = rootView.findViewById(R.id.tvToUser);
        mTvToPay = rootView.findViewById(R.id.tvToPay);
        mCard = rootView.findViewById(R.id.card);
        mTvToUser.setSelected(userSel);
        mTvToShop.setSelected(!userSel);
        mTvToPay.setSelected(!userSel);
        String textContent =
                BuildConfig.HOST + "h5/index.html#/?type=2&uid=" + WmtApplication.user_shopId;
        String textContentShop =
                BuildConfig.HOST + "h5/index.html#/pages/index/register_shop?type=2&uid=" + WmtApplication.user_shopId;
        String textContentPay =
                BuildConfig.HOST + "h5/index.html#/pages/index/shopw?type=2&uid=" + WmtApplication.user_shopId;

        int dp2px = SizeUtils.dp2px(200);
        mBitmapUser = null;
        Observable.just(textContent).subscribeOn(Schedulers.io()).map(s -> CodeUtils.createImage(s, dp2px, dp2px,
                BitmapUtil.drawableToBitmap(getResources()
                        .getDrawable(R.mipmap.logo)))).observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> {
            mBitmapUser = bitmap;
            mIvQCode.setImageBitmap(mBitmapUser);
        });
        mBitmapShop = null;
        Observable.just(textContentShop).subscribeOn(Schedulers.io()).map(s -> CodeUtils.createImage(s, dp2px, dp2px,
                BitmapUtil.drawableToBitmap(getResources()
                        .getDrawable(R.mipmap.logo)))).observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> {
            mBitmapShop = bitmap;
        });
        mBitmapPay = null;
        Observable.just(textContentPay).subscribeOn(Schedulers.io()).map(s -> CodeUtils.createImage(s, dp2px, dp2px,
                BitmapUtil.drawableToBitmap(getResources()
                        .getDrawable(R.mipmap.logo)))).observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> {
            mBitmapPay = bitmap;
        });

        mTvToolRight.setText("分享");
        EventBus.getDefault().register(this);
        ImageView ivAvatar = rootView.findViewById(R.id.ivAvatar);
        TextView tvTitle = rootView.findViewById(R.id.tvTitle);
        TextView tvSubTitle = rootView.findViewById(R.id.tvSubTitle);
        tvTitle.setText(mBundle.getString("title"));
        tvSubTitle.setText(mBundle.getString("subtitle"));
        Glide.with(mActivity).load(WmtApplication.user_avatar).apply(GlideUtils.init().options(R.drawable.wmt_default)).into(ivAvatar);
        mTvToUser.setOnClickListener(v -> {
            if (mBitmapUser != null) {
                mIvQCode.setImageBitmap(mBitmapUser);
                mTvToUser.setSelected(userSel);
                mTvToShop.setSelected(!userSel);
                mTvToPay.setSelected(!userSel);

            }
        });
        mTvToShop.setOnClickListener(v -> {
            if (mBitmapShop != null) {
                mIvQCode.setImageBitmap(mBitmapShop);
                mTvToShop.setSelected(userSel);
                mTvToUser.setSelected(!userSel);
                mTvToPay.setSelected(!userSel);
            }
        });
        mTvToPay.setOnClickListener(v -> {
            if (mBitmapPay != null) {
                mIvQCode.setImageBitmap(mBitmapPay);
                mTvToPay.setSelected(userSel);
                mTvToUser.setSelected(!userSel);
                mTvToShop.setSelected(!userSel);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        mLoading = new DialogLoading(this);
        mLoading.show();
        Observable.just(1).subscribeOn(Schedulers.io())
                .map(aLong -> {
                    mBitmap = BitmapUtil.screenShotView(mCard);
                    mSaveFilePath = BitmapUtil.saveBitmap("share" + UUID.randomUUID() + ".png", mBitmap);
                    return aLong;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong
                        -> {
                    if (mLoading != null) {
                        mLoading.dismiss();
                        new ShareDialog(mActivity).show();
                    }
                });
    }

    @Subscribe
    public void shareEvent(ShareOneEvent event) {
        if (mSaveFilePath != null && mBitmap != null) {
            new WXShare(this).shareImage(mSaveFilePath, mBitmap, SendMessageToWX.Req.WXSceneSession);
        }
    }

    @Subscribe
    public void shareEvent(ShareAllEvent event) {
        if (mSaveFilePath != null && mBitmap != null) {
            new WXShare(this).shareImage(mSaveFilePath, mBitmap, SendMessageToWX.Req.WXSceneTimeline);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mBitmapShop != null && !mBitmapShop.isRecycled()) {
            mBitmapShop.recycle();
            mBitmapShop = null;
        }
        if (mBitmapUser != null && !mBitmapUser.isRecycled()) {
            mBitmapUser.recycle();
            mBitmapUser = null;
        }
        if (mBitmapPay != null && !mBitmapPay.isRecycled()) {
            mBitmapPay.recycle();
            mBitmapPay = null;
        }
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }

    }
}
