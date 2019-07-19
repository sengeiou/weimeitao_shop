package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.BindDetailBean;
import com.wmtc.wmtb.mvp.bean.OffLineDetailBean;
import com.wmtc.wmtb.mvp.bean.OnLineDetailBean;
import com.wmtc.wmtb.mvp.presenter.OrderRecordDetailPresenter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/18.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderRecordDetailActivity extends CommonWmtActivity {
    @BindView(R.id.tvAliPay)
    TextView mTvAliPay;
    @BindView(R.id.tvWxPay)
    TextView mTvWxPay;
    @BindView(R.id.tvMoney)
    TextView mTvMoney;
    @BindView(R.id.tvCreateTime)
    TextView mTvCreateTime;
    @BindView(R.id.tvOrderMoney)
    TextView mTvOrderMoney;
    @BindView(R.id.tvRedMoney)
    TextView mTvRedMoney;
    @BindView(R.id.tvOrderNum)
    TextView mTvOrderNum;
    @BindView(R.id.tvShopNum)
    TextView mTvShopNum;
    @BindView(R.id.tvBindType)
    TextView mTvBindType;
    @BindView(R.id.tvBindMoney)
    TextView mTvBindMoney;
    @BindView(R.id.tvBindSafe)
    TextView mTvBindSafe;
    @BindView(R.id.tvOffSafe)
    TextView mTvOffSafe;
    @BindView(R.id.tvOnSafe)
    TextView mTvOnSafe;
    @BindView(R.id.tvBindCreateTime)
    TextView mTvBindCreateTime;
    @BindView(R.id.tvBindShopNum)
    TextView mTvBindShopNum;
    @BindView(R.id.tvBindName)
    TextView mTvBindName;
    @BindView(R.id.tvBindOrderName)
    TextView mTvBindOrderName;
    @BindView(R.id.llBind)
    LinearLayout mLlBind;
    @BindView(R.id.llOffLine)
    LinearLayout mLlOffLine;
    @BindView(R.id.viewShowOrder)
    View viewShowOrder;
    @BindView(R.id.llShowOrder)
    View llShowOrder;
    @BindView(R.id.tvOnTitle)
    TextView mTvOnTitle;
    @BindView(R.id.tvOnMoney)
    TextView mTvOnMoney;
    @BindView(R.id.tvOnAppointMoney)
    TextView mTvOnAppointMoney;
    @BindView(R.id.tvOnWillMoney)
    TextView mTvOnWillMoney;

    @BindView(R.id.tvOnDelMoney)
    TextView mTvOnDelMoney;
    @BindView(R.id.tvOnAllMoney)
    TextView mTvOnAllMoney;
    @BindView(R.id.tvOnPlatformMoney)
    TextView mTvOnPlatformMoney;
    @BindView(R.id.tvCouponType)
    TextView mTvCouponType;
    @BindView(R.id.tvOnBackMoney)
    TextView mTvOnBackMoney;
    @BindView(R.id.tvOnPlatformAddMoney)
    TextView mTvOnPlatformAddMoney;
    @BindView(R.id.tvOnCreateTime)
    TextView mTvOnCreateTime;
    @BindView(R.id.tvOnOrderNum)
    TextView mTvOnOrderNum;
    @BindView(R.id.tvOnShopNum)
    TextView mTvOnShopNum;
    @BindView(R.id.tvOnHongBaoMoney)
    TextView tvOnHongBaoMoney;
    @BindView(R.id.tvHongBaoType)
    TextView tvHongBaoType;
    @BindView(R.id.llPlatformAdd)
    LinearLayout llPlatformAdd;
    @BindView(R.id.llCoupon)
    LinearLayout llCoupon;
    @BindView(R.id.llOnLine)
    NestedScrollView mLlOnLine;
    private Unbinder mBind;
    private OrderRecordDetailPresenter mPresenter;
    private int mType;

    @Override
    public int initAddLayout() {
        return R.layout.activity_order_record_details;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mTvToolRight.setVisibility(View.INVISIBLE);
        mPresenter = new OrderRecordDetailPresenter(this);
        mType = mBundle.getInt("type");
        if (mType == 2) {
            mLlBind.setVisibility(View.VISIBLE);
            mPresenter.getBangDingDetail(mBundle.getString("id"));
        } else if (mType == 1) {
            mLlOffLine.setVisibility(View.VISIBLE);
            mPresenter.getOfflineDetail(mBundle.getString("id"));
        } else if (mType == 0) {
            mLlOnLine.setVisibility(View.VISIBLE);
            mPresenter.getOnLineDetail(mBundle.getString("id"));
        }
    }

    public void onLineDetail(OnLineDetailBean bean) {
        OnLineDetailBean.DataBean data = bean.data;
        if (data != null) {
            mTvOnCreateTime.setText(StringUtils.init().fixNullStr(data.createTime));
            mTvOnMoney.setText(StringUtils.init().fixNullStrMoney(data.totalAmount, "￥"));
            mTvOnAppointMoney.setText(StringUtils.init().fixNullStrMoney(data.yuyueFirstAmount, "￥"));
            mTvOnWillMoney.setText(StringUtils.init().fixNullStrMoney(data.originalProjectPriceEnd, "￥"));
            String couponType = data.couponType;
            String coupon;
            if ("1".equals(couponType)) {
                coupon = "现金红包";
            } else if ("2".equals(couponType)) {
                coupon = "商户券";
            } else if ("3".equals(couponType)) {
                coupon = "项目专项券";
            } else if ("4".equals(couponType)) {
                coupon = "全平台通用";
            } else {
                coupon = "";
                llCoupon.setVisibility(View.GONE);
            }
            mTvCouponType.setText(String.format(Locale.CHINA, "%s%s", coupon, StringUtils.init().fixNullStr(data.couponName)));
            mTvOnDelMoney.setText(StringUtils.init().fixNullStrMoney(data.couponPrice, "-￥"));
            mTvOnAllMoney.setText(StringUtils.init().fixNullStrMoney(data.projectPriceEnd, "￥"));
            mTvOnPlatformMoney.setText(StringUtils.init().fixNullStrMoney(data.bangdingFee, "+￥"));
            mTvOnBackMoney.setText(StringUtils.init().fixNullStrMoney(data.platformSubsidyShopFee, "+￥"));
            mTvOnPlatformAddMoney.setText(StringUtils.init().fixNullStrMoney(data.platformSubsidyShopAmount, "+￥"));
            mTvOnOrderNum.setText(StringUtils.init().fixNullStr(data.projectName));
            mTvOnShopNum.setText(StringUtils.init().fixNullStr(data.orderNo));
            mTvOnOrderNum.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.orderId);
                ActivityUtils.init().start(mActivity, OrderDetailsActivity.class, "订单详情", bundle);
            });
            boolean isSafe = "0".equals(data.bangDingTixianStatus);
            mTvOnSafe.setText(isSafe ? "订单保护期" : "");
            if (StringUtils.isNotBlank(data.hongbaoName)) {
                tvHongBaoType.setText(StringUtils.init().fixNullStr(data.hongbaoName));
            }
            tvOnHongBaoMoney.setText(StringUtils.init().fixNullStrMoney(data.hongbaoPrice, "-￥"));
        }
    }

    public void offLineDetail(OffLineDetailBean bean) {
        OffLineDetailBean.DataBean data = bean.data;
        if (data != null) {
            String payType = data.payType;
            mTvAliPay.setVisibility("zfb".equals(payType) ? View.VISIBLE : View.GONE);
            mTvWxPay.setVisibility("zfb".equals(payType) ? View.GONE : View.VISIBLE);
            mTvOrderMoney.setText(StringUtils.init().fixNullStrMoney(data.actualAmount, "￥"));
            mTvMoney.setText(StringUtils.init().fixNullStrMoney(data.totalAmount, "￥"));
            mTvRedMoney.setText(StringUtils.init().fixNullStrMoney(data.bonusPoolsSubsidyShopAmount, "￥"));
            mTvShopNum.setText(StringUtils.init().fixNullStr(data.payInfoId));
            mTvCreateTime.setText(StringUtils.init().fixNullStr(data.paidTime));
            mTvOrderNum.setText(StringUtils.init().fixNullStr(data.zfNo));
            boolean isSafe = "0".equals(data.bangDingTixianStatus);
            mTvOffSafe.setText(isSafe ? "订单保护期" : "");
        }
    }

    public void bindDetail(BindDetailBean bean) {
        BindDetailBean.DataBean data = bean.data;
        if (data != null) {
            mTvBindOrderName.setText(StringUtils.init().fixNullStr(data.projectName));
            boolean isThisShop = "1".equals(data.bingDingType);
            mTvBindType.setText(isThisShop ? "绑定金（本店消费）" : "绑定金（非本店消费）");
            viewShowOrder.setVisibility(isThisShop ? View.VISIBLE : View.INVISIBLE);
            llShowOrder.setVisibility(isThisShop ? View.VISIBLE : View.INVISIBLE);
            mTvBindCreateTime.setText(StringUtils.init().fixNullStr(data.projectPriceEndPaidTime));
            String userName = data.userName;
            if (userName.length() > 1) {
                userName = "*" + userName.substring(1);
            }
            mTvBindName.setText(StringUtils.init().fixNullStr(userName));
            mTvBindMoney.setText(StringUtils.init().fixNullStrMoney(data.bangDingFee, "￥"));
            mTvBindShopNum.setText(StringUtils.init().fixNullStr(data.orderNo));
            boolean isSafe = "0".equals(data.bangDingTixianStatus);
            mTvBindSafe.setText(isSafe ? "订单保护期" : "");
            mTvBindOrderName.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.orderId);
                ActivityUtils.init().start(mActivity, OrderDetailsActivity.class, "订单详情", bundle);
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

}
