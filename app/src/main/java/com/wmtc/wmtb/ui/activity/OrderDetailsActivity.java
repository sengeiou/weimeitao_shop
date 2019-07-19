package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.CustomChatBean;
import com.wmtc.wmtb.mvp.bean.MsgListBean;
import com.wmtc.wmtb.mvp.bean.OrderDetailsBean;
import com.wmtc.wmtb.mvp.event.CancelOrderEvent;
import com.wmtc.wmtb.mvp.pojo.AfterSalePojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderStatusPojo;
import com.wmtc.wmtb.mvp.presenter.OrderDetailsPresenter;
import com.wmtc.wmtb.ui.dialog.CustomDialog;
import com.wmtc.wmtb.ui.dialog.DialogCancelPush;
import com.wmtc.wmtb.ui.dialog.FixEndPriceDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jiguang.chat.activity.ui.activity.CustomChatActivity;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.dialog.DialogEditBottom;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

public class OrderDetailsActivity extends SuperBaseActivity implements View.OnClickListener {

    private OrderDetailsPresenter mPresenter;
    private String id, orderStatus, userId;
    private RelativeLayout rl_back;
    private TextView tv_state;
    private TextView tv_refund;
    private ImageView im_pic;
    private TextView tv_name;
    private TextView tv_msg;
    private View v_1;
    private View v_2;
    private View v_3;
    private TextView tv_time;
    private TextView tv_teacher;
    private RelativeLayout rl_order_date;
    private TextView tv_refund_price;
    private TextView tv_reason;
    private TextView tv_describe;
    private RelativeLayout rl_order_refund;
    private TextView tv_shop_name;
    private TextView tv_address;
    private TextView tv_chat;
    private TextView tv_phone;
    private TextView price_first;
    private TextView tv_price_first;
    private LinearLayout ll_price_first;
    private TextView price_end;
    private TextView tv_price_end;
    private LinearLayout ll_price_end;
    private TextView price_active;
    private TextView tv_price_active;
    private LinearLayout ll_price_active;
    private TextView price_packet;
    private TextView tv_price_packet;
    private LinearLayout ll_price_packet;
    private TextView price_serve;
    private TextView tv_price_serve;
    private LinearLayout ll_price_serve;
    private TextView pay;
    private TextView tv_pay;
    private TextView tv_message;
    private TextView tv_order_number;
    private LinearLayout ll_copy;
    private TextView tv_order_creattime;
    private TextView tv_order_firstpay;
    private TextView tv_shop_confirm_time;
    private LinearLayout ll_shop_confirm_time;
    private TextView tv_shop_cancel_time;
    private LinearLayout ll_shop_cancel;
    private TextView tv_arrival_time;
    private LinearLayout ll_arrival_time;
    private TextView tv_after_sale_time;
    private LinearLayout ll_after_sale_time;
    private TextView btn_a_new, btn_a_wait;
    private TextView btn_b_new, btn_b_wait;
    private RelativeLayout rl_order_btn_new, rl_order_btn_wait;
    private Disposable disposable;

    private String tel, type;
    private CustomDialog customDialog;
    private OrderDetailsBean orderDetailsBean = new OrderDetailsBean();
    private Disposable mDisposable;
    private TextView mTv_bind;
    private TextView mTv_appoint;
    private TextView mTv_platform;
    private TextView mTv_real_end;
    private LinearLayout mLlAddPrice;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        EventBus.getDefault().register(this);
        mPresenter = new OrderDetailsPresenter(this);
        id = mBundle.getString("id");
//        orderStatus = mBundle.getString("orderStatus");
        userId = BaseInitApplication.mHeardMap.get("id");
//        type = mBundle.getString("type");
//        Intent intent = getIntent();
//        orderStatus = intent.getStringExtra("orderStatus");
//        userId = BaseInitApplication.mHeardMap.get("id");
//        type = intent.getStringExtra("type");
//        id = intent.getStringExtra("id");
        //初始化控件
        initView();
        //订单详情
        getDate(id);
        mPresenter.getCustomerService();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarAppointment).init();
    }

    //接口获取订单详情
    private void getDate(String id) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setId(id);
        mPresenter.getOrderDetails(orderPojo);
    }

    //接口返回的数据
    public void getOrderDetails(OrderDetailsBean detailsBean) {
        orderDetailsBean = detailsBean;
        //设置对应布局
        if (orderDetailsBean != null && orderDetailsBean.data != null) {
            orderStatus = orderDetailsBean.data.orderStatus;
            setLayout(orderStatus, detailsBean);
            tel = detailsBean.data.shopTel;
        }
    }

    //操作订单
    private void updateOrder(String orderId, String orderStatus, String projectPriceEnd) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderId(orderId);
        orderPojo.setOrderStatus(orderStatus);
        orderPojo.setProjectPriceEnd(projectPriceEnd);
        mPresenter.updateOrderByShop(orderPojo);
    }

    //返回操作订单
    public void backorderStatusUp(BaseBean bean) {
        if (bean.result.equals("ok")) {
            getDate(id);
        } else {
            Toast.makeText(OrderDetailsActivity.this, bean.message, Toast.LENGTH_SHORT).show();
        }
    }

    //初始化控件
    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_refund = (TextView) findViewById(R.id.tv_refund);
        im_pic = (ImageView) findViewById(R.id.im_pic);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        v_1 = (View) findViewById(R.id.v_1);
        v_2 = (View) findViewById(R.id.v_2);
        v_3 = (View) findViewById(R.id.v_3);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_teacher = (TextView) findViewById(R.id.tv_teacher);
        rl_order_date = (RelativeLayout) findViewById(R.id.rl_order_date);
        tv_refund_price = (TextView) findViewById(R.id.tv_refund_price);
        tv_reason = (TextView) findViewById(R.id.tv_reason);
        tv_describe = (TextView) findViewById(R.id.tv_describe);
        rl_order_refund = (RelativeLayout) findViewById(R.id.rl_order_refund);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);
        price_first = (TextView) findViewById(R.id.price_first);
        tv_price_first = (TextView) findViewById(R.id.tv_price_first);
        ll_price_first = (LinearLayout) findViewById(R.id.ll_price_first);
        price_end = (TextView) findViewById(R.id.price_end);
        tv_price_end = (TextView) findViewById(R.id.tv_price_end);
        ll_price_end = (LinearLayout) findViewById(R.id.ll_price_end);
        price_active = (TextView) findViewById(R.id.price_active);
        tv_price_active = (TextView) findViewById(R.id.tv_price_active);
        ll_price_active = (LinearLayout) findViewById(R.id.ll_price_active);
        price_packet = (TextView) findViewById(R.id.price_packet);
        tv_price_packet = (TextView) findViewById(R.id.tv_price_packet);
        ll_price_packet = (LinearLayout) findViewById(R.id.ll_price_packet);
        price_serve = (TextView) findViewById(R.id.price_serve);
        tv_price_serve = (TextView) findViewById(R.id.tv_price_serve);
        ll_price_serve = (LinearLayout) findViewById(R.id.ll_price_serve);
        pay = (TextView) findViewById(R.id.pay);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        ll_copy = (LinearLayout) findViewById(R.id.ll_copy);
        ll_copy.setOnClickListener(this);
        tv_order_creattime = (TextView) findViewById(R.id.tv_order_creattime);
        tv_order_firstpay = (TextView) findViewById(R.id.tv_order_firstpay);
        tv_shop_confirm_time = (TextView) findViewById(R.id.tv_shop_confirm_time);
        ll_shop_confirm_time = (LinearLayout) findViewById(R.id.ll_shop_confirm_time);
        tv_shop_cancel_time = (TextView) findViewById(R.id.tv_shop_cancel_time);
        ll_shop_cancel = (LinearLayout) findViewById(R.id.ll_shop_cancel);
        tv_arrival_time = (TextView) findViewById(R.id.tv_arrival_time);
        ll_arrival_time = (LinearLayout) findViewById(R.id.ll_arrival_time);
        tv_after_sale_time = (TextView) findViewById(R.id.tv_after_sale_time);
        ll_after_sale_time = (LinearLayout) findViewById(R.id.ll_after_sale_time);
        btn_a_new = (TextView) findViewById(R.id.btn_a_new);
        btn_a_new.setOnClickListener(this);
        btn_b_new = (TextView) findViewById(R.id.btn_b_new);
        btn_b_new.setOnClickListener(this);
        rl_order_btn_new = (RelativeLayout) findViewById(R.id.rl_order_btn_new);

        btn_a_wait = (TextView) findViewById(R.id.btn_a_wait);
        btn_a_wait.setOnClickListener(this);
        btn_b_wait = (TextView) findViewById(R.id.btn_b_wait);
        btn_b_wait.setOnClickListener(this);
        rl_order_btn_wait = (RelativeLayout) findViewById(R.id.rl_order_btn_wait);
        customDialog = new CustomDialog(OrderDetailsActivity.this);


        mTv_bind = findViewById(R.id.tv_bind);
        mTv_appoint = findViewById(R.id.tv_appoint);
        mTv_platform = findViewById(R.id.tv_platform);
        mTv_real_end = findViewById(R.id.tv_real_end);
        mLlAddPrice = findViewById(R.id.llAddPrice);
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                setResult(11);
                finish();
                break;
            case R.id.tv_chat:
                Bundle bundle = new Bundle();
                bundle.putString("name", "admin");
                if (orderDetailsBean != null) {
                    JMessageClient.getUserInfo(orderDetailsBean.data.userId, WmtApplication.C_JMKEY,
                            new GetUserInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, UserInfo userInfo) {
                                    responseSuccess();
                                    if (userInfo != null) {
                                        MsgListBean.DataBean dataBean = new MsgListBean.DataBean();
                                        dataBean.createTime = "";
                                        dataBean.messageTitle = "唯美淘在线客服";
                                        String nick = StringUtils.init().fixNullStr(userInfo.getNickname());
                                        dataBean.messageContent = nick;
                                        dataBean.chatId = "admin";
                                        bundle.putString("name", orderDetailsBean.data.userId);
                                        ActivityUtils.init().start(mActivity, CustomChatActivity.class, nick, bundle);
                                    } else {
                                        showEmpty();
                                    }
                                    LogUtil.e(userInfo);
                                }
                            });
                }
                break;
            case R.id.tv_phone:
                CallPhone(tel);
                break;
            case R.id.ll_copy:
                break;
            case R.id.btn_a_new:
                updateOrder(id, "5", "");
                break;
            case R.id.btn_b_new:
                updateOrder(id, "4", "");
                break;
            case R.id.btn_a_wait:
                updateOrder(id, "10", "");
                break;
            case R.id.btn_b_wait:
//                DialogEditBottom editBottom = new DialogEditBottom(mActivity);
//                editBottom.setInputHint("请输入要修改的尾款金额");
//                editBottom.show(R.id.inputOK, view -> {
//                    EditText editText = (EditText) view;
//                    if (StringUtils.init().isEmpty(editText)) {
//                        ToastUtils.init().showInfoToast(mActivity, "尾款金额不可为零");
//                        return;
//                    }
//                    String strMoney = editText.getText().toString();
//                    double parseDouble = Double.parseDouble(strMoney);
//                    int fenDouble = (int) (parseDouble * 100);
//                    String strPriceEnd = orderDetailsBean.data.projectPriceEnd;
//                    int endPrice = Integer.parseInt(strPriceEnd);
//                    if (fenDouble > endPrice) {
//                        ToastUtils.init().showInfoToast(mActivity, "不能大于原尾款金额");
//                        return;
//                    }
//                    if (fenDouble < 100) {
//                        ToastUtils.init().showInfoToast(mActivity, "不能小于金额一元");
//                        return;
//                    }
//                    updateOrder(id, "", fenDouble + "");
//                    editBottom.dismiss();
//                });

                OrderDetailsBean.DataBean data = orderDetailsBean.data;
                if ("407".equals(data.pid)) {
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
                        updateOrder(id, "", fenDouble + "");
                        editBottom.dismiss();
                    });
                } else {
                    if ("1".equals(data.changeProjectEndStatus)) {
                        FixEndPriceDialog dialog = new FixEndPriceDialog(mActivity);
                        dialog.setMoney(data.projectPriceEnd);
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
                            pojo.setOrderId(orderDetailsBean.data.id);
                            pojo.setOrderStatus("");
                            pojo.setProjectPriceEnd(fenDouble + "");
                            updateOrder(id, "", fenDouble + "");
                            dialog.dismiss();
                        });
                    } else {
                        ToastUtils.init().showInfoToast(mActivity, "尾款只可修改一次");
                    }
                }

                break;
        }

    }

    private void CallPhone(final String tel) {
        customDialog.setDismissButton(CustomDialog.Type.TEXTVIEW);
        customDialog.show();
        customDialog.init(tel, "", "取消", "呼叫", new CustomDialog.OnCustomDialogClickListener() {
            @Override
            public void onClick(CustomDialog dialog, CustomDialog.Type type) {
                switch (type) {
                    case LEFT:
                        customDialog.dismiss();
                        break;
                    case RIGHT:
                        Intent it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(it);
                        customDialog.dismiss();
                        break;
                }
            }
        });
    }

    //设置相应布局
    // 新订单 orderStatus = 3
    // 待到店 orderStatus = 5
    // 已取消 orderStatus = 7，8，10
    // 售后   orderStatus = 暂无
    private void setLayout(String orderStatus, OrderDetailsBean detailsBean) {
        String picurl = WmtApplication.url_host;
        OrderDetailsBean.DataBean bean = detailsBean.data;
        tv_state.setText(StringUtils.init().fixNullStr(bean.orderStatusName));
        if ("7,8,10,11,13,14,15".contains(bean.orderStatus)) {
            tv_refund.setVisibility(View.VISIBLE);
            switch (orderStatus) {
                case "7":
                    tv_msg.setText(StringUtils.init().fixNullStr("用户在合理时间内取消"));
                    tv_msg.setTextColor(Color.parseColor("#FFD700"));
                    break;
                case "8":
                    tv_msg.setText(StringUtils.init().fixNullStr("用户违约主动取消"));
                    tv_msg.setTextColor(Color.parseColor("#FF0000"));
                    break;
                case "10":
                    tv_msg.setText(StringUtils.init().fixNullStr("用户超时，商户主动取消"));
                    tv_msg.setTextColor(Color.parseColor("#FF0000"));
                    break;
            }
            tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(bean.orderDesc);
        } else {
            tv_refund.setVisibility(View.GONE);
            tv_msg.setVisibility(View.GONE);
        }
        tv_name.setText(StringUtils.init().fixNullStr(bean.projectName));
        Glide.with(this)
                .load(picurl + bean.picUrl)
                .apply(GlideUtils.init().options())
                .into(im_pic);
        if (orderStatus.equals("3") || orderStatus.equals("5") || orderStatus.equals("7") || orderStatus.equals("8") || orderStatus.equals("10")) {
            rl_order_date.setVisibility(View.VISIBLE);
            tv_time.setText(StringUtils.init().fixNullStr(bean.arrivalTime));
            tv_teacher.setText(StringUtils.init().fixNullStr(bean.technicianName));
            if (orderStatus.equals("7") || orderStatus.equals("8") || orderStatus.equals("10")) {
                v_1.setVisibility(View.VISIBLE);
                v_2.setVisibility(View.VISIBLE);
                v_3.setVisibility(View.VISIBLE);
            } else {
                v_1.setVisibility(View.GONE);
                v_2.setVisibility(View.GONE);
                v_3.setVisibility(View.GONE);
            }
        } else {
            rl_order_date.setVisibility(View.GONE);
        }
        if (StringUtils.isBlank(bean.paidProjectPriceFirst)) {
            bean.paidProjectPriceFirst = "0";
        }
        if (orderStatus.equals("退款")) {
            rl_order_refund.setVisibility(View.VISIBLE);
            tv_refund_price.setText(StringUtils.init().fixNullStrMoney(bean.paidProjectPriceFirst));
            tv_reason.setText(StringUtils.init().fixNullStr("自己写的退款原因"));
            tv_describe.setText(StringUtils.init().fixNullStr("退款的详细描述"));
        } else {
            rl_order_refund.setVisibility(View.GONE);
        }
        tv_shop_name.setText(StringUtils.init().fixNullStr(bean.shopName));
        tv_address.setText(StringUtils.init().fixNullStr(bean.shopAddress));
        //活动优惠相关字段
        tv_price_first.setText(StringUtils.init().fixNullStrMoney(bean.projectPriceFirst));
        tv_price_end.setText(StringUtils.init().fixNullStrMoney(bean.projectPriceEnd));
        if (orderStatus.equals("7") || orderStatus.equals("8") || orderStatus.equals("10") || orderStatus.equals("售后")) {
            pay.setText("退款总金额");
            tv_pay.setText(StringUtils.init().fixNullStrMoney(bean.paidProjectPriceFirst, "￥"));
        } else {
            pay.setVisibility(View.GONE);
            tv_pay.setText(StringUtils.init().fixNullStrMoney(bean.totalAmount, "服务收入·￥"));
        }
        tv_message.setText(StringUtils.init().fixNullStr(bean.leavingMsg));
        tv_order_number.setText(StringUtils.init().fixNullStr(bean.id));
        tv_order_creattime.setText(StringUtils.init().fixNullStr(bean.createTime));
        tv_order_firstpay.setText(StringUtils.init().fixNullStr(bean.projectPriceFirstPaidTime));
        if (orderStatus.equals("5") || orderStatus.equals("7") || orderStatus.equals("8") || orderStatus.equals("10") || orderStatus.equals("退款")) {
            ll_shop_confirm_time.setVisibility(View.VISIBLE);
            tv_shop_confirm_time.setText(StringUtils.init().fixNullStr(bean.shopConfirmTime));
        } else {
            ll_shop_confirm_time.setVisibility(View.GONE);
        }
        if (orderStatus.equals("7") || orderStatus.equals("8") || orderStatus.equals("10")) {
            ll_shop_cancel.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(bean.userCancelTime)) {
                tv_shop_cancel_time.setText(bean.userCancelTime);
            } else {
                tv_shop_cancel_time.setText(bean.shopCancelTime);
            }
        } else {
            ll_shop_cancel.setVisibility(View.GONE);
        }
        if (orderStatus.equals("退款")) {
            ll_arrival_time.setVisibility(View.VISIBLE);
            tv_arrival_time.setText(StringUtils.init().fixNullStr(bean.projectPriceEndPaidTime));
            ll_after_sale_time.setVisibility(View.VISIBLE);
            tv_after_sale_time.setText(StringUtils.init().fixNullStr("发起售后的时间"));
        } else {
            ll_arrival_time.setVisibility(View.GONE);
            ll_after_sale_time.setVisibility(View.GONE);
        }
        if (orderStatus.equals("3")) {
            rl_order_btn_new.setVisibility(View.VISIBLE);
            rl_order_btn_wait.setVisibility(View.GONE);
            btn_a_new.setBackground(getResources().getDrawable(R.drawable.order_bg_agree));
            mDisposable = downTime(bean.projectPriceFirstPaidTime, btn_a_new);
            btn_a_new.setTextColor(Color.parseColor("#ffffff"));
            btn_b_new.setBackground(getResources().getDrawable(R.drawable.order_bg_repulse));
            btn_b_new.setText("拒绝预约");
            btn_b_new.setTextColor(Color.parseColor("#3F51B5"));
        } else if (orderStatus.equals("5")) {
            rl_order_btn_new.setVisibility(View.GONE);
            rl_order_btn_wait.setVisibility(View.VISIBLE);
            btn_a_wait.setText("用户超时取消");
            btn_b_wait.setText("更改尾款");

            long aftTime = DateUtils.dateToStamp(bean.afterArrivalTime);
            long arrTime = DateUtils.dateToStamp(bean.arrivalTime);
            long nowTime = new Date().getTime();

            btn_a_wait.setEnabled(aftTime < nowTime);//时间过了最后到店时间，可以取消
            btn_b_wait.setEnabled(arrTime < nowTime);//时间过了到店时间可以更改尾款

            if (btn_a_wait.isEnabled()) {
                btn_a_wait.setBackground(getResources().getDrawable(R.drawable.order_bg_agree));
                btn_a_wait.setTextColor(Color.parseColor("#ffffff"));
            } else {
                btn_a_wait.setBackground(getResources().getDrawable(R.drawable.order_bg_gray));
                btn_a_wait.setTextColor(Color.parseColor("#ffffff"));
            }

            if (btn_b_wait.isEnabled()) {
                btn_b_wait.setBackground(getResources().getDrawable(R.drawable.order_bg_agree));
                btn_b_wait.setTextColor(Color.parseColor("#ffffff"));
            } else {
                btn_b_wait.setBackground(getResources().getDrawable(R.drawable.order_bg_gray));
                btn_b_wait.setTextColor(Color.parseColor("#ffffff"));
            }

        } else if (orderStatus.equals("7") || orderStatus.equals("8") || orderStatus.equals("10")) {
            rl_order_btn_new.setVisibility(View.GONE);
            rl_order_btn_wait.setVisibility(View.GONE);
        } else if ("12".equals(bean.orderStatus)) {
            rl_order_btn_wait.setVisibility(View.VISIBLE);
            btn_b_wait.setBackground(getResources().getDrawable(R.drawable.order_bg_repulse));
            btn_b_wait.setText("拒绝退款");
            btn_b_wait.setTextColor(Color.parseColor("#3F51B5"));
            btn_a_wait.setBackground(getResources().getDrawable(R.drawable.order_bg_repulse));
            btn_a_wait.setText("同意退款");
            btn_a_wait.setTextColor(Color.parseColor("#3F51B5"));

            btn_a_wait.setOnClickListener(v -> {
                AfterSalePojo mSalePojo = new AfterSalePojo();
                mSalePojo.orderId = bean.id;
                mSalePojo.handleType = "2";
                mSalePojo.shopId = WmtApplication.user_shopId;
                mPresenter.afterSale(mSalePojo);
            });
            btn_b_wait.setOnClickListener(v -> {
                new DialogCancelPush(mActivity)
                        .setDict("shop_reject_refund_reason", bean.id)
                        .show();
            });

        } else {
            rl_order_btn_new.setVisibility(View.GONE);
            rl_order_btn_wait.setVisibility(View.GONE);
        }


        //红包
        if (StringUtils.isBlank(bean.hongbaoName)) {
            ll_price_packet.setVisibility(View.GONE);
        } else {
            ll_price_packet.setVisibility(View.VISIBLE);
            tv_price_packet.setText(StringUtils.init().fixNullStrMoney(bean.hongbaoPrice, "-￥"));
            price_packet.setText(StringUtils.init().fixNullStr(bean.hongbaoName));
        }
        //立减
        if (StringUtils.isBlank(bean.couponName)) {
            ll_price_active.setVisibility(View.GONE);
        } else {
            ll_price_active.setVisibility(View.VISIBLE);
            tv_price_active.setText(StringUtils.init().fixNullStrMoney(bean.couponPrice));
            price_active.setText(StringUtils.init().fixNullStr(bean.couponName));
        }
        if (orderStatus.equals("6")) {
            mLlAddPrice.setVisibility(View.VISIBLE);
        } else {
            mLlAddPrice.setVisibility(View.GONE);
        }
        mTv_bind.setText(StringUtils.init().fixNullStrMoney(bean.bangdingFee, "+￥"));
        mTv_appoint.setText(StringUtils.init().fixNullStrMoney(bean.platformSubsidyShopFee, "+￥"));
        mTv_platform.setText(StringUtils.init().fixNullStrMoney(bean.subsidyTotalAmount, "+￥"));
        mTv_real_end.setText(StringUtils.init().fixNullStrMoney(bean.paidProjectPriceEnd, "￥"));

    }
    @Subscribe
    public void onEvent(CancelOrderEvent event) {
        LogUtil.e(event.mPojo);
        mPresenter.afterSale(event.mPojo);
    }

    @SuppressLint("CheckResult")
    public Disposable downTime(String time, TextView textView) {
        try {
            long preTime = DateUtils.dateToStamp(time);
            long aftTime = preTime + 1000 * 60 * 10;

            Disposable disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
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
                          /*  System.out.println(String.format(Locale.CHINA,
                                    "当前剩余时间：%s:%s", mStr, sStr));*/
                            returnTime = mStr + ":" + sStr;
                        } else {
//                            LogUtil.e("订单已截止");
                        }
                        return returnTime;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {

                        textView.setText(String.format(Locale.CHINA, "接受预约%s", s));
                    });
            return disposable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void chatBean(CustomChatBean chatBean) {
        CustomChatBean.DataBean data = chatBean.data;
        if (data != null) {
            if ("1".equals(data.isPublic)) {
                tv_chat.setVisibility(View.VISIBLE);
            } else {
                tv_chat.setVisibility(View.GONE);
            }
            tv_chat.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", "C_" + orderDetailsBean.data.userId);
                JMessageClient.getUserInfo("C_" + orderDetailsBean.data.userId, WmtApplication.C_JMKEY,
                        new GetUserInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, UserInfo userInfo) {
                                if (userInfo != null) {
                                    MsgListBean.DataBean dataBean = new MsgListBean.DataBean();
                                    dataBean.createTime = "";
                                    String nick = StringUtils.init().fixNullStr(userInfo.getNickname());
                                    dataBean.messageTitle = nick;
                                    dataBean.messageContent = nick;
                                    dataBean.chatId = "C_" + orderDetailsBean.data.userId;
                                    bundle.putString("name", "C_" + orderDetailsBean.data.userId);
                                    ActivityUtils.init().start(mActivity, CustomChatActivity.class, nick, bundle);
                                }
                                LogUtil.e(userInfo);
                            }
                        });
            });
            tv_phone.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", data.jmessageUserName);
                JMessageClient.getUserInfo(data.jmessageUserName, WmtApplication.C_JMKEY,
                        new GetUserInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, UserInfo userInfo) {
                                if (userInfo != null) {
                                    MsgListBean.DataBean dataBean = new MsgListBean.DataBean();
                                    dataBean.createTime = "";
                                    String nick = StringUtils.init().fixNullStr(userInfo.getNickname());
                                    dataBean.messageTitle = nick;
                                    dataBean.messageContent = nick;
                                    dataBean.chatId = data.jmessageUserName;
                                    bundle.putString("name", data.jmessageUserName);
                                    ActivityUtils.init().start(mActivity, CustomChatActivity.class, nick, bundle);
                                }
                                LogUtil.e(userInfo);
                            }
                        });
            });

        }
    }

    public void afterSale() {
        getDate(id);
    }
}
