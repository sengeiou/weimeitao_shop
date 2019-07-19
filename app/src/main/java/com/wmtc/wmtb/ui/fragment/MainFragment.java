package com.wmtc.wmtb.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmtc.wmtb.BuildConfig;
import com.wmtc.wmtb.MainActivity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.CommentBannerBean;
import com.wmtc.wmtb.mvp.bean.NewOrderBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.TodayAmountBean;
import com.wmtc.wmtb.mvp.event.ShopAppointEvent;
import com.wmtc.wmtb.mvp.event.ShopReviewOkEvent;
import com.wmtc.wmtb.mvp.event.ShopTextEditEvent;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderStatusPojo;
import com.wmtc.wmtb.mvp.presenter.MainPresenter;
import com.wmtc.wmtb.ui.activity.AppointSetActivity;
import com.wmtc.wmtb.ui.activity.BindRecordListActivity;
import com.wmtc.wmtb.ui.activity.MessagesActivity;
import com.wmtc.wmtb.ui.activity.OrderDetailsActivity;
import com.wmtc.wmtb.ui.activity.OrderRecordListActivity;
import com.wmtc.wmtb.ui.activity.PlatformActivity;
import com.wmtc.wmtb.ui.activity.PreApplyActivity;
import com.wmtc.wmtb.ui.activity.ProjectsTabActivity;
import com.wmtc.wmtb.ui.activity.TeachListActivity;
import com.wmtc.wmtb.ui.activity.VipActivity;
import com.wmtc.wmtb.ui.adapter.AdapterAppoint;
import com.wmtc.wmtb.ui.adapter.AdapterNewOrder;
import com.wmtc.wmtb.ui.dialog.FixEndPriceDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.dialog.DialogEditBottom;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.NumAnimUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.CardTransformer;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MainFragment extends SuperBaseFragment {
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvMessage)
    TextView mTvMessage;
    @BindView(R.id.tvAppointSetting)
    TextView mTvAppointSetting;
    @BindView(R.id.tvTodayMoney)
    TextView mTvTodayMoney;
    @BindView(R.id.tvAllOrder)
    TextView mTvAllOrder;
    @BindView(R.id.tvOrders)
    TextView mTvOrders;
    @BindView(R.id.tvApply)
    TextView mTvApply;
    @BindView(R.id.tvProjects)
    TextView mTvProjects;
    @BindView(R.id.tvTeach)
    TextView mTvTeach;
    @BindView(R.id.tvBindNum)
    TextView mTvBindNum;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.viewPagerAppoint)
    ViewPager viewPagerAppoint;
    @BindView(R.id.llNewOrders)
    LinearLayout llNewOrders;
    @BindView(R.id.tvNewOrderNum)
    TextView tvNewOrderNum;
    @BindView(R.id.tvAppointNum)
    TextView tvAppointNum;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvVip)
    TextView tvVip;
    @BindView(R.id.banner)
    BGABanner mBanner;
    @BindView(R.id.ivRedDit)
    ImageView ivRedDit;
    @BindView(R.id.llNot)
    LinearLayout llNot;
    Unbinder unbinder;
    private MainPresenter mPresenter;
    private AdapterAppoint mAdapterAppoint;
    private AdapterNewOrder mAdapterNewOrder;
    private ShopsBean.DataBean mShop;
    public MainActivity mMainActivity;
    private int mReadMsgCount;
    private Disposable mSubscribe;
    private int jpushCount = 0;

    @Override
    public int initLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarMain).init();
    }

    @Override
    protected void initData(View rootView) {
        initImmersionBar();
        initRefreshStatusView(rootView);
        mReadMsgCount = JMessageClient.getAllUnReadMsgCount();
        unbinder = ButterKnife.bind(this, rootView);
        mMainActivity = (MainActivity) mActivity;
        EventBus.getDefault().register(this);
        mShop = mMainActivity.mShop;

        if (mShop != null) {
            WmtApplication.user_shopId = mShop.id + "";
        }

        llNot.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);
        tvVip.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", mShop.shopName);
            bundle.putString("level", mShop.level + "");
            bundle.putString("levelName", mShop.levelName);
            bundle.putString("levelUrl", mShop.levelUrl);
            bundle.putString("levelDateStr", mShop.levelDateStr);
            ActivityUtils.init().start(mActivity, VipActivity.class, "", bundle);
        });
        tvMoney.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                ActivityUtils.init().start(mActivity, PlatformActivity.class, "平台奖金");
            }
        });
        mBanner.setAdapter((BGABanner.Adapter<ImageView, CommentBannerBean.DataBean>) (banner, itemView, model, position) -> {
            if (model != null) {
                Glide.with(mActivity)
                        .load(model.path)
                        .apply(GlideUtils.init().options(R.drawable.wmt_default))
                        .into(itemView);
            }
        });
        mTvProjects.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                ActivityUtils.init().start(mMainActivity, ProjectsTabActivity.class, "项目管理");
            }
        });
        mTvTitle.setOnClickListener(v -> mMainActivity.toFixStatus(mShop));
        mPresenter = new MainPresenter(this);
        mPresenter.todayAmount();
        mPresenter.shopEnter();
        mPresenter.getHeaderBanner();
        mPresenter.getNewVersion();
        mSubscribe = Observable.interval(0, 5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(aLong -> {
                    WmtApplication.mJPushUdid = JPushInterface.getRegistrationID(mActivity);
                    if (StringUtils.isNotBlank(WmtApplication.mJPushUdid) || jpushCount > 9) {
                        if (mSubscribe != null && !mSubscribe.isDisposed()) {
                            mSubscribe.dispose();
                        }
                    }
                    ++jpushCount;
                    LogUtil.e("----Jpush更新----");
                    return aLong;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mPresenter.updateShopUserInfo(WmtApplication.mJPushUdid);
                }, throwable -> {
                });

        getStatusOrderList("3");
        getStatusOrderList("5");
        if (mShop != null) {
            mTvTitle.setText(mShop.shopName);
        }
        mTvMessage.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "admin");
                ActivityUtils.init().start(mMainActivity, MessagesActivity.class, "消息", bundle);
            }
        });
        mViewPager.setPageTransformer(true, new CardTransformer(30));

        viewPagerAppoint.setPageTransformer(true, new CardTransformer(30));
        mTvTeach.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                Bundle bundle = new Bundle();
                bundle.putString("shopName", StringUtils.init().fixNullStr(mTvTitle.getText()));
                ActivityUtils.init().start(mMainActivity, TeachListActivity.class, "技师管理", bundle);
            }
        });

        mTvOrders.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                ActivityUtils.init().start(mMainActivity, OrderRecordListActivity.class, "收款记录");
            }
        });
        mTvApply.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                ActivityUtils.init().start(mMainActivity, PreApplyActivity.class, "提现");
            }
        });

        mTvTodayMoney.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                ActivityUtils.init().start(mMainActivity, OrderRecordListActivity.class, "收款记录");
            }
        });

        mTvAllOrder.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                ActivityUtils.init().start(mMainActivity, OrderRecordListActivity.class, "收款记录");
            }
        });
        mTvBindNum.setOnClickListener(v -> {

            if (mMainActivity.toFixStatus(mShop)) {
                ActivityUtils.init().start(mMainActivity, BindRecordListActivity.class, "绑定记录");
            }
        });
        mTvAppointSetting.setOnClickListener(v -> {
            if (mMainActivity.toFixStatus(mShop)) {
                Bundle bundle = new Bundle();
                bundle.putString("restDay", StringUtils.init().fixNullStr(mShop.closetime));
                bundle.putString("openTime", StringUtils.init().fixNullStr(mShop.openTime));
                bundle.putString("endTime", StringUtils.init().fixNullStr(mShop.endTime));
                bundle.putString("sendPhone", StringUtils.init().fixNullStr(mShop.sendphone));
                bundle.putString("average", StringUtils.init().fixNullStr(mShop.average));
                bundle.putString("teachNum", StringUtils.init().fixNullStr(mShop.teachNum + ""));
                ActivityUtils.init().start(mMainActivity, AppointSetActivity.class, "预约设置", bundle);
            }
        });

        ivRedDit.setVisibility(JMessageClient.getAllUnReadMsgCount() > 0 ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.todayAmount();
        getStatusOrderList("3");
        getStatusOrderList("5");

    }

    private void getStatusOrderList(String status) {
        OrderPojo pojo = new OrderPojo();
        pojo.setOrderStatus(status);
        pojo.setShopId(WmtApplication.user_shopId);
        mPresenter.getOrderList(pojo);
    }

    @Subscribe
    public void onEvnet(ShopTextEditEvent editEvent) {
        mPresenter.shopEnter();
    }

    @Subscribe
    public void onEvnet(ShopAppointEvent editEvent) {
        mPresenter.shopEnter();
    }

    @Subscribe
    public void onEvnet(ShopReviewOkEvent reviewOkEvent) {
        if (reviewOkEvent.shop != null) {
            mShop = reviewOkEvent.shop;
        }
    }

    @Override
    protected void onShowFragment() {
        super.onShowFragment();
        mPresenter.todayAmount();
        getStatusOrderList("3");
        getStatusOrderList("5");
        ivRedDit.setVisibility(JMessageClient.getAllUnReadMsgCount() > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void todayAmount(TodayAmountBean bean) {
        TodayAmountBean.DataBean data = bean.data;
        String totalAmount = data.totalAmount;
        String strMoney = StringUtils.init().fixNullStrMoney(totalAmount);
        mTvTodayMoney.setText(strMoney);
        float v = Float.parseFloat(strMoney);
        if (strMoney != null && !"".equals(strMoney) && !"0.00".equals(strMoney) && v > 1.0) {
            NumAnimUtil.startAnim(mTvTodayMoney, v, strMoney);
        }

        mTvAllOrder.setText(String.format(Locale.CHINA, "交易共%d笔", data.num));
        mTvBindNum.setText(String.format(Locale.CHINA, "绑定共%d人", data.bangdingNum));

    }

    public void orderList(NewOrderBean bean, String orderStatus) {
        NewOrderBean.DataBean data = bean.data;
        if (data != null && data.list != null && data.list.size() > 0) {
            if (orderStatus.equals("3")) {
                llNewOrders.setVisibility(View.VISIBLE);
                if (data.list.size() > 0) {
                    tvNewOrderNum.setVisibility(View.VISIBLE);
                    tvNewOrderNum.setText(String.format(Locale.CHINA, "%d", data.list.size()));
                } else {
                    tvNewOrderNum.setVisibility(View.INVISIBLE);
                }
                mAdapterNewOrder = new AdapterNewOrder(data.list);
                mViewPager.setAdapter(mAdapterNewOrder);
                mViewPager.setOffscreenPageLimit(3);
                mAdapterNewOrder.setListener(new AdapterNewOrder.ClickListener() {
                    @Override
                    public void okListener(NewOrderBean.DataBean.ListBean bean, int position) {
                        OrderStatusPojo pojo = new OrderStatusPojo();
                        pojo.setOrderId(bean.id);
                        pojo.setOrderStatus("5");
                        mPresenter.updateOrderByShop(pojo);
                    }

                    @Override
                    public void noListener(NewOrderBean.DataBean.ListBean bean, int position) {
                        LogUtil.e(bean);
                        OrderStatusPojo pojo = new OrderStatusPojo();
                        pojo.setOrderId(bean.id);
                        pojo.setOrderStatus("4");
                        mPresenter.updateOrderByShop(pojo);
                    }

                    @Override
                    public void detail(NewOrderBean.DataBean.ListBean bean, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", bean.id);
//                        bundle.putString("orderStatus", bean.orderStatus);
//                        bundle.putString("type", "3");
                        ActivityUtils.init().start(mMainActivity, OrderDetailsActivity.class, "", bundle);
                    }
                });
            } else {
                tvAppointNum.setVisibility(View.VISIBLE);
                tvAppointNum.setText(String.format(Locale.CHINA, "%d", data.list.size()));
                mAdapterAppoint = new AdapterAppoint(data.list);
                viewPagerAppoint.setAdapter(mAdapterAppoint);
                viewPagerAppoint.setOffscreenPageLimit(3);
                mAdapterAppoint.setListener(new AdapterNewOrder.ClickListener() {
                    @Override
                    public void okListener(NewOrderBean.DataBean.ListBean bean, int position) {
                        if ("407".equals(bean.pid)) {
                            DialogEditBottom editBottom = new DialogEditBottom(mActivity);
                            editBottom.setInputHint("请输入要修改的尾款金额");
                            editBottom.show(R.id.inputOK, view -> {
                                EditText editText = (EditText) view;
                                if (StringUtils.init().isEmpty(editText)) {
                                    ToastUtils.init().showInfoToast(mActivity, "尾款金额不可为零");
                                    return;
                                }
                                String strMoney = editText.getText().toString();
                                double parseDouble = Double.parseDouble(strMoney);
                                int fenDouble = (int) (parseDouble * 100);
                                if (fenDouble < 100) {
                                    ToastUtils.init().showInfoToast(mActivity, "不能小于金额一元");
                                    return;
                                }
                                OrderStatusPojo pojo = new OrderStatusPojo();
                                pojo.setOrderId(bean.id);
                                pojo.setOrderStatus("");
                                pojo.setProjectPriceEnd(fenDouble + "");
                                mPresenter.updateOrderByShop(pojo);
                                editBottom.dismiss();
                            });
                        } else {

                            if ("1".equals(bean.changeProjectEndStatus)) {// 只可修改一次
                                FixEndPriceDialog dialog = new FixEndPriceDialog(mActivity);
                                dialog.setMoney(bean.projectPriceEnd);
                                dialog.show(R.id.btnSure, view -> {
                                    EditText editText = (EditText) view;
                                    if (StringUtils.init().isEmpty(editText)) {
                                        ToastUtils.init().showInfoToast(mMainActivity, "尾款金额不可为零");
                                        return;
                                    }
                                    String strMoney = editText.getText().toString();
                                    double parseDouble = Double.parseDouble(strMoney);
                                    int fenDouble = (int) (parseDouble * 100);
                                    OrderStatusPojo pojo = new OrderStatusPojo();
                                    pojo.setOrderId(bean.id);
                                    pojo.setOrderStatus("");
                                    pojo.setProjectPriceEnd(fenDouble + "");
                                    mPresenter.updateOrderByShop(pojo);
                                    dialog.dismiss();
                                });
                            } else {
                                ToastUtils.init().showInfoToast(mActivity, "尾款金额只可修改一次");
                            }
                        }
                    }

                    @Override
                    public void noListener(NewOrderBean.DataBean.ListBean bean, int position) {
                        OrderStatusPojo pojo = new OrderStatusPojo();
                        pojo.setOrderId(bean.id);
                        pojo.setOrderStatus("10");
                        mPresenter.updateOrderByShop(pojo);
                    }

                    @Override
                    public void detail(NewOrderBean.DataBean.ListBean bean, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", bean.id);
//                        bundle.putString("orderStatus", bean.orderStatus);
//                        bundle.putString("type", "5");
                        ActivityUtils.init().start(mMainActivity, OrderDetailsActivity.class, "", bundle);
                    }
                });
            }
        } else {

            llNewOrders.setVisibility(View.GONE);
            ArrayList<NewOrderBean.DataBean.ListBean> list = new ArrayList<>();
            NewOrderBean.DataBean.ListBean listBean = new NewOrderBean.DataBean.ListBean(1);
            list.add(listBean);
            mAdapterAppoint = new AdapterAppoint(list);
            tvAppointNum.setVisibility(View.INVISIBLE);
            viewPagerAppoint.setAdapter(mAdapterAppoint);
            viewPagerAppoint.setOffscreenPageLimit(3);
        }
    }

    @Override
    protected void onHideFragment() {
        super.onHideFragment();
        if (mAdapterNewOrder != null) {
            if (mAdapterNewOrder.mDisposables != null) {
                for (Disposable disposable : mAdapterNewOrder.mDisposables) {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (StringUtils.isNotBlank(WmtApplication.mJPushUdid) || jpushCount > 9) {
            if (mSubscribe != null && !mSubscribe.isDisposed()) {
                mSubscribe.dispose();
            }
        }
        if (mAdapterNewOrder != null) {
            if (mAdapterNewOrder.mDisposables != null) {
                for (Disposable disposable : mAdapterNewOrder.mDisposables) {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }
        }
    }

    public void orderStatusUp(String orderStatus) {
        getStatusOrderList("3");
        getStatusOrderList("5");
    }

    public void shopEnter(ShopsBean bean) {
        if (bean != null && bean.data != null && bean.data.size() > 0) {
            ShopsBean.DataBean dataBean = bean.data.get(0);
            mTvTitle.setText(dataBean.shopName);
            mShop = dataBean;
            MainActivity activity = (MainActivity) mMainActivity;
            activity.mShop = mShop;
        }
    }


    public void headerBanner(CommentBannerBean bean) {
        mBanner.setData(bean.data, null);
    }

    public void updatePush() {
        if (StringUtils.isNotBlank(WmtApplication.mJPushUdid) || jpushCount > 9) {
            if (mSubscribe != null && !mSubscribe.isDisposed()) {
                mSubscribe.dispose();
            }
        }
    }
}
