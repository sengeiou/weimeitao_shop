package com.wmtc.wmtb.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.event.ShopTextEditEvent;
import com.wmtc.wmtb.mvp.event.VideoVrUpdateEvent;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.presenter.MePresenter;
import com.wmtc.wmtb.ui.activity.FeedBackActivity;
import com.wmtc.wmtb.ui.activity.LoginActivity;
import com.wmtc.wmtb.ui.activity.MeShopUpdateActivity;
import com.wmtc.wmtb.ui.activity.PlatformActivity;
import com.wmtc.wmtb.ui.activity.QCodeActivity;
import com.wmtc.wmtb.ui.activity.ShopTextEditActivity;
import com.wmtc.wmtb.ui.activity.VipActivity;
import com.wmtc.wmtb.ui.dialog.CustomDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jiguang.chat.activity.ui.activity.CustomChatActivity;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MeFragment extends SuperBaseFragment {

    @BindView(R.id.ivAvatar)
    PolygonImageView mIvAvatar;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvSubTitle)
    TextView mTvSubTitle;
    @BindView(R.id.ivSetting)
    ImageView mIvSetting;
    @BindView(R.id.ivQCode)
    ImageView mIvQCode;
    @BindView(R.id.llShop)
    LinearLayout mLlShop;
    @BindView(R.id.llDecorate)
    LinearLayout mLlDecorate;
    @BindView(R.id.llInfo)
    LinearLayout mLlInfo;
    @BindView(R.id.llVip)
    LinearLayout mLlVip;
    @BindView(R.id.llPool)
    LinearLayout mLlPool;
    @BindView(R.id.llServer)
    LinearLayout mLlServer;
    @BindView(R.id.llFeedBack)
    LinearLayout llFeedBack;
    Unbinder unbinder;
    private MePresenter mPresenter;
    private TextView llLogout;
    private CustomDialog customDialog;
    private File mFile;

    @Override
    public int initLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarMe).init();
    }

    @Override
    protected void initData(View rootView) {
        initImmersionBar();
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mPresenter = new MePresenter(this);

        mIvSetting.setVisibility(View.INVISIBLE);
        mPresenter.shopEnter(BaseInitApplication.mHeardMap.get("id"));

        initView(rootView);
        customDialog = new CustomDialog(getContext());
        llLogout.setOnClickListener(v -> {
            LogOut();
        });
        mLlServer.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", "admin");
            ActivityUtils.init().start(mActivity, CustomChatActivity.class, "唯美淘在线客服", bundle);
        });
        llLogout.setOnLongClickListener(v -> {
            ToastUtils.init().showInfoToast(mActivity, BuildConfig.DEBUG ? "测试版本:" + BuildConfig.VERSION_NAME : "正式版本:" + BuildConfig.VERSION_NAME);
            return false;
        });
        mIvAvatar.setOnClickListener(v -> {
            openCamera();
        });
        llFeedBack.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, FeedBackActivity.class, "反馈内容");
        });
        mLlPool.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, PlatformActivity.class, "平台奖金");
        });

    }

    private void openCamera() {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(permissions -> CameraUtil.getInstance().openSingalCamer4Fragment(this))
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();
    }


    public void logoutDate(BaseBean bean) {
        if (bean.result.equals("ok")) {
            //退出 清除相关信息
            JMessageClient.logout();
            SharePreUtil.saveData(mActivity, "user_phone", "");
            SharePreUtil.saveData(mActivity, "user_password", "");
            SharePreUtil.saveData(mActivity, "uid", "");
            SharePreUtil.saveData(mActivity, "token", "");
            ActivityUtils.init().start(mActivity, LoginActivity.class);
            mActivity.finish();
        } else {
            Toast.makeText(mActivity, bean.message, Toast.LENGTH_SHORT).show();
        }
    }

    public void avatar() {
        if (mFile != null) {
            mPresenter.shopEnter(BaseInitApplication.mHeardMap.get("id"));
            Glide.with(mActivity).load(mFile).apply(GlideUtils.init().optionsSkip(R.drawable.wmt_default)).into(mIvAvatar);
        }
    }

    private void LogOut() {
        customDialog.setDismissButton(CustomDialog.Type.TEXTVIEW);
        customDialog.show();
        customDialog.init("确定要退出登录？", "", "取消", "确定", (dialog, type) -> {
            switch (type) {
                case LEFT:
                    customDialog.dismiss();
                    break;
                case RIGHT:
                    OrderPojo pojo = new OrderPojo();
                    pojo.setUserId(BaseInitApplication.mHeardMap.get("id"));
                    pojo.setUserType("2");
                    mPresenter.logout(pojo);
                    customDialog.dismiss();
                    break;
            }
        });
    }

    private DialogLoading mLoading;
    public boolean isZiped = true;

    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (mLoading == null) {
                mLoading = new DialogLoading(mActivity);
            }
            mLoading.show();
            isZiped = false;
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Observable.just(pathList).subscribeOn(Schedulers.io()).map(strings -> {
                mFile = BitmapUtil.compressImage(new File(strings.get(0)));
                ArrayList<File> files = new ArrayList<>();
                files.add(mFile);
                return files;
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(files -> {

                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                isZiped = true;
                HashMap<String, String> stringMap = new HashMap<>();
                stringMap.put("uid", BaseInitApplication.mHeardMap.get("id"));
                mPresenter.avatar(files, stringMap);
            });
        }
    }


    private void initView(View rootView) {
        llLogout = rootView.findViewById(R.id.llLogout);
    }

    @Override
    protected void onShowFragment() {
        super.onShowFragment();
        mPresenter.shopEnter(BaseInitApplication.mHeardMap.get("id"));
    }

    @Subscribe
    public void onEvnet(ShopTextEditEvent editEvent) {
        mPresenter.shopEnter(BaseInitApplication.mHeardMap.get("id"));
    }

    @Subscribe
    public void onEvnet(VideoVrUpdateEvent editEvent) {
        mPresenter.shopEnter(BaseInitApplication.mHeardMap.get("id"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void shopEnter(ShopsBean bean) {
        List<ShopsBean.DataBean> data = bean.data;
        if (data != null && data.size() > 0) {
            ShopsBean.DataBean dataBean = data.get(0);
            WmtApplication.user_avatar =
                    WmtApplication.url_host + dataBean.userAvatar;
            Glide.with(this)
                    .load(WmtApplication.user_avatar)
                    .apply(GlideUtils.init().options())
                    .apply(new RequestOptions().circleCrop())
                    .into(mIvAvatar);
            mLlDecorate.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("shop", dataBean);
                ActivityUtils.init().start(mActivity, MeShopUpdateActivity.class, "门店装修", bundle);
            });
            mTvTitle.setText(StringUtils.init().fixNullStr(dataBean.shopName));
            mTvSubTitle.setText(StringUtils.init().fixNullStr(dataBean.address));
            mLlShop.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("shop", dataBean);
                ActivityUtils.init().start(mActivity, ShopTextEditActivity.class, "门店管理", bundle);
            });
            mIvQCode.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("title", dataBean.shopName);
                bundle.putString("subtitle", dataBean.address);
                ActivityUtils.init().start(mActivity, QCodeActivity.class, "邀请码", bundle);
            });

            mLlVip.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", dataBean.shopName);
                bundle.putString("level", dataBean.level + "");
                bundle.putString("levelName", dataBean.levelName);
                bundle.putString("levelDateStr", dataBean.levelDateStr);
                ActivityUtils.init().start(mActivity, VipActivity.class, "", bundle);
            });


        }
    }

}
