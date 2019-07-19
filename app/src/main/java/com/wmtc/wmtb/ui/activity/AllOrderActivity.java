package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.mvp.event.CancelOrderEvent;
import com.wmtc.wmtb.mvp.pojo.AfterSalePojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderStatusPojo;
import com.wmtc.wmtb.mvp.presenter.OrderAllPresenter;
import com.wmtc.wmtb.ui.dialog.DialogCancelPush;
import com.wmtc.wmtb.ui.dialog.FixEndPriceDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.dialog.DialogEditBottom;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

public class AllOrderActivity extends CommonWmtActivity {

    private OrderAllPresenter mPresenter;
    private ImageView im_back;
    private RecyclerView list_all_order;
    private SmartRefreshLayout all_order_smart;
    private ArrayList<OrderStateBean.DataBean.ListBean> dataBeans = new ArrayList<>();
    private All_Order_Adapter all_order_adapter;
    private Disposable disposable;
    private int page = 1;
    private String shopId;
    private ArrayList<Disposable> mDisposables;

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        EventBus.getDefault().register(this);
        mPresenter = new OrderAllPresenter(this);
        shopId = WmtApplication.user_shopId;
        list_all_order = findViewById(R.id.list_all_order);
        showLoading();
        getDate(shopId, page);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_all_order.setLayoutManager(layoutManager);
        all_order_adapter = new All_Order_Adapter(this);
        list_all_order.setAdapter(all_order_adapter);
        mTvToolRight.setVisibility(View.INVISIBLE);
        mDisposables = new ArrayList<>();
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            ++page;
            getDate(shopId, page);
        });
    }

    @Override
    public int initAddLayout() {
        return R.layout.activity_all_order;
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        dataBeans.clear();
        page = 1;
        getDate(shopId, page);
        if (mDisposables != null && mDisposables.size() > 0) {
            for (Disposable disposable1 : mDisposables) {
                if (disposable1 != null && !disposable1.isDisposed()) {
                    disposable1.dispose();
                }
            }
        }
    }

    //获取全部订单
    private void getDate(String shopId, int page) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setShopId(shopId);
        orderPojo.setCurrentPage(page + "");
        mPresenter.getAllOrder(orderPojo);
    }

    //获取全部订单
    private void getDateUpdate(String shopId, int page) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setShopId(shopId);
        orderPojo.setCurrentPage(page + "");
        mPresenter.getAllOrderUpdate(orderPojo);
    }

    //接口返回的数据
    public void getAllOrder(OrderStateBean orderBean) {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
        if (page == 1) {
            dataBeans.clear();
            if (orderBean.data != null && orderBean.data.list != null && orderBean.data.list.size() > 0) {
                dataBeans.addAll(orderBean.data.list);
                all_order_adapter.notifyDataSetChanged();
            } else {
                showEmpty();
            }
        } else {
            if (orderBean.data != null && orderBean.data.list != null && orderBean.data.list.size() > 0) {
                dataBeans.addAll(orderBean.data.list);
                all_order_adapter.notifyDataSetChanged();
            }
        }
    }

    public void afterSale() {
        page = 1;
        getDate(shopId, page);
    }

    @Subscribe
    public void onEvent(CancelOrderEvent event) {
        LogUtil.e(event.mPojo);
        mPresenter.afterSale(event.mPojo);
    }

    public void orderStatusUp(String orderStatus) {
        dataBeans.clear();
        page = 1;
        getDateUpdate(shopId, page);
        if (mDisposables != null && mDisposables.size() > 0) {
            for (Disposable disposable1 : mDisposables) {
                if (disposable1 != null && !disposable1.isDisposed()) {
                    disposable1.dispose();
                }
            }
        }
    }


    //适配器
    class All_Order_Adapter extends RecyclerView.Adapter<All_Order_Adapter.All_Order_Holder> {

        private Context mContext;

        public All_Order_Adapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public All_Order_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_order_state, parent, false);
            All_Order_Adapter.All_Order_Holder holder = new All_Order_Adapter.All_Order_Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(All_Order_Holder holder, int position) {
            OrderStateBean.DataBean.ListBean bean = dataBeans.get(position);
            String type = "";
            switch (bean.orderStatus) {
                case "1":
                    type = "other";
                    break;
                case "2":
                    type = "other";
                    break;
                case "3":
                    type = "3";
                    break;
                case "4":
                    type = "other";
                    break;
                case "5":
                    type = "5";
                    break;
                case "6":
                    type = "other";
                    break;
                case "7":
                    type = "CANCEL";
                    break;
                case "8":
                    type = "CANCEL";
                    break;
                case "9":
                    type = "other";
                    break;
                case "10":
                    type = "CANCEL";
                    break;
                case "11":
                    type = "other";
                    break;
                case "12":
                    type = "AfterSale";
                    break;
                case "13":
                case "14":
                    type = "other";
                    break;
                case "15":
                    type = "AfterSale15";
                    break;
            }
            Bundle bundle = new Bundle();
            Glide.with(mContext)
                    .load(WmtApplication.url_host + bean.proPic)
                    .apply(GlideUtils.init().options(R.drawable.wmt_default))
                    .into(holder.im_photo);
            holder.tv_name.setText(bean.projectName);
            holder.tv_time.setText(bean.arrivalTime);
            holder.tv_teacher.setText(bean.technicianName);
            String finalType = type;
            holder.rl_item.setOnClickListener(v -> {
//                Toast.makeText(getContext(), "跳转订单详情", Toast.LENGTH_SHORT).show();
                bundle.putString("id", bean.id);
//                bundle.putString("orderStatus", bean.orderStatus);
//                bundle.putString("type", finalType);
                ActivityUtils.init().start(mActivity, OrderDetailsActivity.class, "", bundle);
            });
            if (StringUtils.isNotBlank(bean.projectPriceFirstPaidTime)) {
                Disposable disposable = downTime(bean.projectPriceFirstPaidTime, holder.tv_agree, position);
                if (disposable != null) {
                    mDisposables.add(disposable);
                }
            }
            holder.tv_sale_no.setVisibility("15".equals(bean.orderStatus) ? View.INVISIBLE : View.VISIBLE);
            switch (type) {
                case "3":
                    holder.rl_new.setVisibility(View.VISIBLE);
                    holder.rl_wait.setVisibility(View.GONE);
                    holder.rl_center.setVisibility(View.GONE);
                    holder.rl_after_sale.setVisibility(View.GONE);
                    holder.tv_agree.setOnClickListener(v -> {
                        OrderStatusPojo pojo = new OrderStatusPojo();
                        pojo.setOrderId(bean.id);
                        pojo.setOrderStatus("5");
                        mPresenter.updateOrderByShop(pojo);
//                        Toast.makeText(AllOrderActivity.this, "接受预约", Toast.LENGTH_SHORT).show();
                    });
                    holder.tv_repulse.setOnClickListener(v -> {
                        OrderStatusPojo pojo = new OrderStatusPojo();
                        pojo.setOrderId(bean.id);
                        pojo.setOrderStatus("4");
                        mPresenter.updateOrderByShop(pojo);
//                        Toast.makeText(AllOrderActivity.this, "拒绝预约", Toast.LENGTH_SHORT).show();
                    });
                    break;
                case "5":
                    holder.rl_new.setVisibility(View.GONE);
                    holder.rl_wait.setVisibility(View.VISIBLE);
                    holder.rl_center.setVisibility(View.GONE);
                    holder.rl_after_sale.setVisibility(View.GONE);
                    long aftTime = DateUtils.dateToStamp(bean.afterArrivalTime);
                    long arrTime = DateUtils.dateToStamp(bean.arrivalTime);
                    long nowTime = new Date().getTime();
                    holder.tv_change.setOnClickListener(v -> {
                        if (arrTime < nowTime) { //时间过了到店时间可以更改尾款
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
                                if ("1".equals(bean.changeProjectEndStatus)) {
                                    FixEndPriceDialog dialog = new FixEndPriceDialog(mActivity);
                                    dialog.setMoney(bean.projectPriceEnd);
                                    dialog.show(R.id.btnSure, view -> {
                                        EditText editText = (EditText) view;
                                        if (StringUtils.init().isEmpty(editText)) {
                                            ToastUtils.init().showInfoToast(mActivity, "尾款金额不可为零");
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
                                    ToastUtils.init().showInfoToast(mActivity, "尾款只可修改一次");
                                }
                            }
                        } else {
                            ToastUtils.init().showInfoToast(mContext, "未到可修改更改尾款时间");
                        }

                    });
                    holder.tv_time_center.setOnClickListener(v -> {
                        if (aftTime < nowTime) { //时间过了最后到店时间，可以取消
                            OrderStatusPojo pojo = new OrderStatusPojo();
                            pojo.setOrderId(bean.id);
                            pojo.setOrderStatus("10");
                            mPresenter.updateOrderByShop(pojo);
                        } else {
                            ToastUtils.init().showInfoToast(mContext, "未到取消预约时间");
                        }
                    });
                    break;
                case "CANCEL":
                    holder.rl_new.setVisibility(View.GONE);
                    holder.rl_wait.setVisibility(View.GONE);
                    holder.rl_center.setVisibility(View.VISIBLE);
                    holder.rl_after_sale.setVisibility(View.GONE);
                    holder.tv_msg.setText(bean.orderStatusName);
                    if (bean.orderStatus.equals("7")) {
                        holder.tv_msg.setTextColor(Color.parseColor("#FFD700"));
                    } else if (bean.orderStatus.equals("8") || bean.orderStatus.equals("10")) {

                    }
                    holder.tv_msg.setTextColor(Color.parseColor("#FF0000"));
                    break;
                case "other":
                    holder.rl_new.setVisibility(View.GONE);
                    holder.rl_wait.setVisibility(View.GONE);
                    holder.rl_center.setVisibility(View.VISIBLE);
                    holder.rl_after_sale.setVisibility(View.GONE);
                    if (bean.orderStatus.equals("1")) {
                        holder.tv_msg.setText("等待支付（已下单）");
                        holder.tv_msg.setTextColor(Color.parseColor("#FFD700"));
                    } else if (bean.orderStatus.equals("2")) {
                        holder.tv_msg.setText("已取消（10min超时支付）");
                        holder.tv_msg.setTextColor(Color.parseColor("#FFD700"));
                    } else if (bean.orderStatus.equals("4")) {
                        holder.tv_msg.setText("商家拒绝接单");
                        holder.tv_msg.setTextColor(Color.parseColor("#FFD700"));
                    } else if (bean.orderStatus.equals("6")) {
                        holder.tv_msg.setText("已支付尾款");
                        holder.tv_msg.setTextColor(Color.parseColor("#FFD700"));
                    } else if (bean.orderStatus.equals("9")) {
                        holder.tv_msg.setText("商家未接单，自动取消");
                        holder.tv_msg.setTextColor(Color.parseColor("#FFD700"));
                    } else {
                        holder.tv_msg.setText(bean.orderStatusName);
                    }
                    break;
                case "AfterSale":
                    holder.rl_new.setVisibility(View.GONE);
                    holder.rl_wait.setVisibility(View.GONE);
                    holder.rl_center.setVisibility(View.GONE);
                    holder.rl_after_sale.setVisibility(View.VISIBLE);
                    holder.tv_sale_ok.setOnClickListener(v -> {
                        AfterSalePojo mSalePojo = new AfterSalePojo();
                        mSalePojo.orderId = bean.id;
                        mSalePojo.handleType = "2";
                        mSalePojo.shopId = WmtApplication.user_shopId;
                        mPresenter.afterSale(mSalePojo);
                    });
                    holder.tv_sale_no.setOnClickListener(v -> {
                        new DialogCancelPush(mActivity)
                                .setDict("shop_reject_refund_reason", bean.id)
                                .show();
                    });
                    break;
                case "AfterSale15":
                    holder.rl_new.setVisibility(View.GONE);
                    holder.rl_wait.setVisibility(View.GONE);
                    holder.rl_center.setVisibility(View.GONE);
                    holder.rl_after_sale.setVisibility(View.VISIBLE);
                    holder.tv_sale_ok.setOnClickListener(v -> {
                        AfterSalePojo mSalePojo = new AfterSalePojo();
                        mSalePojo.orderId = bean.id;
                        mSalePojo.handleType = "2";
                        mSalePojo.shopId = WmtApplication.user_shopId;
                        mPresenter.afterSale(mSalePojo);
                    });
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return dataBeans.size();
        }

        public class All_Order_Holder extends RecyclerView.ViewHolder {
            RelativeLayout rl_item;
            ImageView im_photo;
            TextView tv_name, tv_time, tv_teacher;
            RelativeLayout rl_new;
            TextView tv_agree, tv_repulse;
            RelativeLayout rl_wait;
            TextView tv_change, tv_time_center;
            RelativeLayout rl_center;
            TextView tv_msg;
            RelativeLayout rl_after_sale;
            TextView tv_sale_ok, tv_sale_no;

            public All_Order_Holder(View itemView) {
                super(itemView);
                rl_item = itemView.findViewById(R.id.rl_item);
                im_photo = itemView.findViewById(R.id.im_photo);
                tv_name = itemView.findViewById(R.id.tv_name);
                tv_time = itemView.findViewById(R.id.tv_time);
                tv_teacher = itemView.findViewById(R.id.tv_teacher);
                rl_new = itemView.findViewById(R.id.rl_new);
                tv_agree = itemView.findViewById(R.id.tv_agree);
                tv_repulse = itemView.findViewById(R.id.tv_repulse);
                rl_wait = itemView.findViewById(R.id.rl_wait);
                tv_change = itemView.findViewById(R.id.tv_change);
                tv_time_center = itemView.findViewById(R.id.tv_time_center);
                rl_center = itemView.findViewById(R.id.rl_center);
                tv_msg = itemView.findViewById(R.id.tv_msg);
                rl_after_sale = itemView.findViewById(R.id.rl_after_sale);
                tv_sale_ok = itemView.findViewById(R.id.tv_sale_ok);
                tv_sale_no = itemView.findViewById(R.id.tv_sale_no);
            }
        }
    }

    @SuppressLint("CheckResult")
    public Disposable downTime(String time, TextView textView, int position) {
        try {
            long preTime = DateUtils.dateToStamp(time);
            long aftTime = preTime + 1000 * 60 * 10;

            textView.setTag(position);
            disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .map(aLong -> {
                        long now = new Date().getTime();
                        long fixTime = aftTime - now;
                        long second = fixTime / 1000;
                        String returnTime = "";
                        if (second > 0) {
                            long m = second / 60;
                            long s = second % 60;
                            String mStr = m < 10 ? "0" + m : m + "";
                            String sStr = s < 10 ? "0" + s : s + "";
//                            System.out.println(String.format(Locale.CHINA,
//                                    "当前剩余时间：%s:%s", mStr, sStr));
                            returnTime = mStr + ":" + sStr;
                        } else {
//                            LogUtil.e("订单已截止");

                        }
                        return returnTime;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        if ((int) textView.getTag() == position) {
                            textView.setText(String.format(Locale.CHINA, "接受预约%s", s));
                        }
                    });
            return disposable;
        } catch (Exception e) {
            e.printStackTrace();
            return disposable;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
        if (mDisposables != null && mDisposables.size() > 0) {
            for (Disposable disposable1 : mDisposables) {
                if (disposable1 != null && !disposable1.isDisposed()) {
                    disposable1.dispose();
                }
            }
        }
    }
}
