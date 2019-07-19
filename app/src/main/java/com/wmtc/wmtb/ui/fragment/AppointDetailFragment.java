package com.wmtc.wmtb.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.OrderStatusPojo;
import com.wmtc.wmtb.mvp.presenter.AppointDetailPresenter;
import com.wmtc.wmtb.ui.activity.OrderDetailsActivity;
import com.wmtc.wmtb.ui.adapter.AppointListAdapter;
import com.wmtc.wmtb.ui.dialog.FixEndPriceDialog;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;
import top.jplayer.baseprolibrary.ui.dialog.DialogEditBottom;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/4/29.
 * com.wmtc.wmtb.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AppointDetailFragment extends SuperBaseFragment {

    private AppointDetailPresenter mPresenter;
    private AppointListAdapter mAdapter;
    public String type;
    public ArrayList<Disposable> mObjects;

    @Override
    public int initLayout() {
        return R.layout.fragment_appoint_detail;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        mObjects = new ArrayList<>();
        mPresenter = new AppointDetailPresenter(this);
        mAdapter = new AppointListAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrderStateBean.DataBean.ListBean bean = mAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.id);
            ActivityUtils.init().start(mActivity, OrderDetailsActivity.class, "", bundle);
        });
        mAdapter.setOnItemChildClickListener((adapter, viewA, position) -> {
            OrderStateBean.DataBean.ListBean bean = mAdapter.getData().get(position);
            long aftTime = DateUtils.dateToStamp(bean.afterArrivalTime);
            long arrTime = DateUtils.dateToStamp(bean.arrivalTime);
            long nowTime = new Date().getTime();
            if (viewA.getId() == R.id.tv_agree) {
                updateOrder(bean.id, "5", "");
            } else if (viewA.getId() == R.id.tv_repulse) {
                updateOrder(bean.id, "4", "");
            } else if (viewA.getId() == R.id.tv_change) {
//                (arrTime < nowTime);//时间过了到店时间可以更改尾款
                if (arrTime < nowTime) {
                    if ("407".equals(bean.pid)) {//判断空项目
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
                            updateOrder(bean.id, "", fenDouble + "");
                            editBottom.dismiss();
                        });
                    } else {
                        if ("1".equals(bean.changeProjectEndStatus)) {//判断只可修改一次
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
                                updateOrder(bean.id, "", fenDouble + "");
                                dialog.dismiss();
                            });
                        } else {
                            ToastUtils.init().showInfoToast(mActivity, "尾款只可修改一次");
                        }
                    }
                } else {
                    ToastUtils.init().showInfoToast(mActivity, "未过到店时间，不可以更改尾款");
                }

            } else if (viewA.getId() == R.id.tv_time_center) {
//                (aftTime < nowTime);//时间过了最后到店时间，可以取消
                if (aftTime < nowTime) {
                    updateOrder(bean.id, "10", "");
                } else {
                    ToastUtils.init().showInfoToast(mActivity, "未过超时时间，不可以取消订单");
                }
            }
            return false;
        });
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        clearDis();
        mPresenter.getOrderList(type, null);
    }

    private void updateOrder(String orderId, String orderStatus, String projectPriceEnd) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderId(orderId);
        orderPojo.setOrderStatus(orderStatus);
        orderPojo.setProjectPriceEnd(projectPriceEnd);
        mPresenter.updateOrderByShop(orderPojo);
    }

    public void getOrderList(OrderStateBean bean) {
        responseSuccess();
        if (bean.data != null && bean.data.list != null && bean.data.list.size() > 0) {
            mAdapter.setNewData(bean.data.list);
        } else {
            showEmpty();
        }
    }

    public void updateOrder(BaseBean bean) {
        clearDis();
        mPresenter.getOrderList(type, new LoaddingErrorImplTip(getActivity()));
    }

    public void setType(String type) {
        this.type = type;
        showLoading();
        mPresenter.getOrderList(type, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        clearDis();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected void onHideFragment() {
        super.onHideFragment();
        clearDis();
    }

    private void clearDis() {
        if (mObjects != null && mObjects.size() > 0) {
            for (Disposable object : mObjects) {
                if (object != null && !object.isDisposed()) {
                    object.dispose();
                }
            }
        }
    }
}
