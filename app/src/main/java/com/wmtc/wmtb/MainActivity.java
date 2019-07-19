package com.wmtc.wmtb;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.wmtc.wmtb.base.FragmentFactory;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.VersionBean;
import com.wmtc.wmtb.mvp.event.ShopReviewOkEvent;
import com.wmtc.wmtb.mvp.presenter.MainPresenter;
import com.wmtc.wmtb.ui.activity.ShopDoorAgainActivity;
import com.wmtc.wmtb.ui.activity.UpdateShopActivity;
import com.wmtc.wmtb.ui.dialog.DialogLogoutTip;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jmessage.biz.httptask.task.GetEventNotificationTaskMng;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.jmrtc.JMRtcNotificationEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.ui.activity.CustomChatActivity;
import top.jplayer.baseprolibrary.net.download.DownloadByManager;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.dialog.DialogLogout;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

public class MainActivity extends SuperBaseActivity {
    public FrameLayout mFlFragment;
    public List<LinearLayout> mViewList;
    public ShopsBean.DataBean mShop;
    private MainPresenter mPresenter;
    private VersionBean.DataBean mData;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mFlFragment = view.findViewById(R.id.flFragment);
        EventBus.getDefault().register(this);
        if (mBundle != null) {
            mShop = mBundle.getParcelable("shop");
            if (mShop != null) {
                WmtApplication.user_shopId = mShop.id + "";
            }
        }
        mDownloadByManager = new DownloadByManager(this);

        bottomBar();

        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JMessageClient.registerEventReceiver(this);
        JMessageClient.login("B_" + WmtApplication.user_shopId, "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.e("-------", "登录: " + i + s + "---------45454455445-");
            }
        });
        LogUtil.e(WmtApplication.mJPushUdid + "----------极光ID--------");
        isOpenDoubleBack = true;

    }

    public boolean toFixStatus(ShopsBean.DataBean shop) {
        if (shop.status == 1) {
            return true;
        } else {
            if (2 == shop.status) {
                Bundle bundle = getBundle();
                ActivityUtils.init().start(mActivity, ShopDoorAgainActivity.class, "", bundle);
            }

            if (3 == shop.status) {
                String title = "商家信息审核失败";
                DialogLogout dialogLogout = new DialogLogout(mActivity);
                dialogLogout.setTitle(title);
                dialogLogout.setSubTitle("前往修改审核信息？");
                dialogLogout.show(R.id.btnSure, view -> {
                    Bundle bundle = getBundle();
                    ActivityUtils.init().start(mActivity, UpdateShopActivity.class, "", bundle);
                    onTabClick(mViewList.get(0), 0);
                    dialogLogout.dismiss();
                });
            }
            return false;
        }
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("shopId", mShop.id + "");
        bundle.putParcelable("bean", mShop);
        bundle.putParcelable("shop", mShop);
        HashMap<String, String> map = new HashMap<>();
        map.put("shopId", StringUtils.init().fixNullStr(mShop.id + ""));
        map.put("city", StringUtils.init().fixNullStr(mShop.city));
        map.put("shopUserId", StringUtils.init().fixNullStr(mShop.shopUserId));
        map.put("shopPhone", StringUtils.init().fixNullStr(mShop.shopPhone));
        map.put("address", StringUtils.init().fixNullStr(mShop.address));
        map.put("location", StringUtils.init().fixNullStr(mShop.location));
        map.put("shopstatus", StringUtils.init().fixNullStr(mShop.shopstatus));
        map.put("shopName", StringUtils.init().fixNullStr(mShop.shopName));
        map.put("province", StringUtils.init().fixNullStr(mShop.province));
        map.put("area", StringUtils.init().fixNullStr(mShop.area));
        map.put("area_code", StringUtils.init().fixNullStr(mShop.areaCode));
        bundle.putString("json", new Gson().toJson(map));
        return bundle;
    }


    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    private void bottomBar() {
        mViewList = new ArrayList<>();
        LinearLayout llHome = superRootView.findViewById(R.id.llHome);
        mViewList.add(llHome);
        llHome.setOnClickListener(v -> {
            onTabClick((LinearLayout) v, 0);
        });
        LinearLayout llScheme = superRootView.findViewById(R.id.llScheme);
        mViewList.add(llScheme);
        llScheme.setOnClickListener(v -> {
            if (toFixStatus(mShop)) {
                onTabClick((LinearLayout) v, 1);
            }
        });
//        LinearLayout llDecorate = superRootView.findViewById(R.id.llDecorate);
//        mViewList.add(llDecorate);
//        llDecorate.setOnClickListener(v -> onTabClick((LinearLayout) v, 2));
        LinearLayout llDesign = superRootView.findViewById(R.id.llDesign);
        mViewList.add(llDesign);
        llDesign.setOnClickListener(v -> {
            onTabClick((LinearLayout) v, 3);
        });
        LinearLayout llMe = superRootView.findViewById(R.id.llMe);
        mViewList.add(llMe);
        llMe.setOnClickListener(v -> {
            onTabClick((LinearLayout) v, 4);
        });
        onTabClick(mViewList.get(0), 0);
    }

    public int curIndex = -1;

    public void onTabClick(LinearLayout view, int index) {
        if (curIndex == index) {
            return;
        }
        for (LinearLayout linearLayout : mViewList) {
            linearLayout.getChildAt(0).setSelected(false);
            linearLayout.getChildAt(1).setSelected(false);
        }
        view.getChildAt(0).setSelected(true);
        view.getChildAt(1).setSelected(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(index + "");
        if (fragment == null) {
            fragment = FragmentFactory.instance().getSingleFragment(index);
            transaction.add(R.id.flFragment, fragment, index + "");
        }
        for (Fragment fragmentItem : getSupportFragmentManager().getFragments()) {
            if (!fragmentItem.isHidden()) {
                transaction.hide(fragmentItem);
            }
        }
        if (fragment.isHidden()) {
            transaction.show(fragment);
        }
        curIndex = index;
        transaction.commitAllowingStateLoss();
    }

    public void onEvent(GetEventNotificationTaskMng.EventEntity event) {
        //do your own business
        LogUtil.e(event);
        LogUtil.e("-----------------");
    }



    @Subscribe
    public void onEvnet(ShopReviewOkEvent reviewOkEvent) {
        if (reviewOkEvent.shop != null) {
            mShop = reviewOkEvent.shop;
        }
    }


    public void onEvent(NotificationClickEvent event) {
        Bundle bundle = new Bundle();
        Message message = event.getMessage();
        bundle.putString("name", message.getFromID());
        ActivityUtils.init().startWithTask(mActivity, CustomChatActivity.class, "唯美淘在线客服", bundle);
        LogUtil.e(event);
        LogUtil.e("---------NotificationClickEvent--------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 添加 下载
     */
    public void version(VersionBean response) {
        mData = response.data;
        if (mData != null && mData.url != null && Integer.parseInt
                (mData.versionCode) > BuildConfig.VERSION_CODE) {

            DialogLogoutTip dialog =
                    new DialogLogoutTip(mActivity).setForce("1".equals(mData.isForce)).setTitle("更新提示").setSubTitle(mData.description);
            dialog.show(R.id.btnSure, view -> {
                dialog.dismiss();
                AndPermission.with(this)
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(permissions -> {
                            downloadByManager(mData);
                        })
                        .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                        .start();
            });


        }
    }

    public void downloadByManager(VersionBean.DataBean data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !mActivity.getPackageManager()
                .canRequestPackageInstalls()) {// 8.0  安装问题 是否允许外部安装
            Uri packageURI = Uri.parse("package:" + mActivity.getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    packageURI);
            startActivityForResult(intent, 10000);
        } else {
            updateVersion(data);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == RESULT_OK) {
            if (mData != null) {
                updateVersion(mData);
            }
        }
    }

    public void updateVersion(VersionBean.DataBean data) {
        int newCode = Integer.parseInt(data.versionCode);
        mDownloadByManager.bind(newCode, data.description, data.url)
                .download().listener((currentByte, totalByte) -> {
            LogUtil.e(currentByte + "-------------" + totalByte);
        });
    }

    private DownloadByManager mDownloadByManager;

    @Override
    protected void onResume() {
        super.onResume();
        if (mDownloadByManager != null)
            mDownloadByManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDownloadByManager != null)
            mDownloadByManager.onPause();
    }

}
