package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.wmtc.wmtb.MainActivity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.CommonPresenterActivity;
import com.wmtc.wmtb.base.CommonProgressActivity;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.UpdateEvent;
import com.wmtc.wmtb.mvp.event.ShopReviewOkEvent;
import com.wmtc.wmtb.mvp.presenter.ShopDoorAgainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

import static com.wmtc.wmtb.base.WmtApplication.mActivityArrayList;
import static com.wmtc.wmtb.base.WmtApplication.mActivityLogin;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopDoorAgainActivity extends CommonProgressActivity {

    private ShopsBean.DataBean mShop;
    private ShopDoorAgainPresenter mPresenter;

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mShop = mBundle.getParcelable("shop");
        EventBus.getDefault().register(this);
        mPresenter = new ShopDoorAgainPresenter(this);
        refresh(mShop);
    }

    private void refresh(ShopsBean.DataBean shop) {
        if (shop != null) {
            int status = shop.status;
            mIvLoginBack.setVisibility((status == 2 || status == 3) ? View.INVISIBLE : View.VISIBLE);
            if (status == 1) {
                //已创建店铺
                mBtnNext.setText("已有店铺");
                Bundle bundle = new Bundle();
                bundle.putString("shopId", mShop.id + "");
                bundle.putParcelable("shop", mShop);
                ActivityUtils.init().start(mActivity, MainActivity.class, "", bundle);
                closeActivity();
                finish();
            } else if (status == 2) {
                mBtnNext.setText("敬请等待审核结果");
                int color = getResources().getColor(R.color.colorBlackGold);
                mTvDoorT.setTextColor(color);
                mLine1.setBackgroundColor(color);
                mLine2.setBackgroundColor(color);
                mTvPushT.setTextColor(color);
                statusType(2, shop.id + "");
            } else if (status == 3) {
                mTvRemark.setVisibility(View.VISIBLE);
                int color1 = getResources().getColor(R.color.colorBlackGold);
                mTvDoorT.setTextColor(color1);
                mLine1.setBackgroundColor(color1);
                int color = getResources().getColor(R.color.redBorder);
                mLine2.setBackgroundColor(color);
                mTvPushT.setTextColor(color);
                if (StringUtils.isNotBlank(shop.remarks)) {
                    String remarks = "原因：" + shop.remarks;
                    String aftRemark = "*入驻申请失败" + remarks;
                    mTvRemark.setText(aftRemark);
                } else {
                    mTvRemark.setText("*入驻申请失败");
                }
                mBtnNext.setText("重新提交申请");
                statusType(3, shop.id + "");
            } else if (status == 4) {
                //特殊状态，之前有店
                statusType(4, shop.id + "");
            } else {
                //冻结状态
                statusType(0, shop.id + "");
            }
        } else {
            //无店铺
            statusType(0, "");
        }
    }

    private void closeActivity() {
        for (CommonPresenterActivity activity : mActivityArrayList) {
            activity.finish();
        }
        for (CommonLoginActivity activity : mActivityLogin) {
            activity.finish();
        }
    }

    @Subscribe
    public void onEvent(UpdateEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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


    private void statusType(int status, String shopId) {
        Bundle bundle = new Bundle();
        bundle.putString("shopId", shopId);
        bundle.putParcelable("shop", mShop);
        mBtnNext.setOnClickListener(v -> {
            if (status == 0) {
                ActivityUtils.init().start(mActivity, SelectCityActivity.class, "", bundle);
            } else if (status == 2) {
                mPresenter.shopEnter(status, shopId);
            } else if (status == 1) {
                ActivityUtils.init().start(mActivity, MainActivity.class, "", bundle);
                closeActivity();
                finish();
            } else if (status == 3) {
                ActivityUtils.init().start(mActivity, UpdateShopActivity.class, "", getBundle());
            }
        });
    }

    public void showShopEnter(ShopsBean bean, int status, String shopId) {
        if (bean != null && bean.data != null && bean.data.size() > 0) {
            if (status == 2) {
                mShop = bean.data.get(0);
                Bundle bundle = new Bundle();
                bundle.putString("shopId", shopId);
                bundle.putParcelable("shop", mShop);
                ActivityUtils.init().start(mActivity, MainActivity.class, "", bundle);
                EventBus.getDefault().post(new ShopReviewOkEvent(mShop));
                closeActivity();
                finish();
            }
        }
    }
}
