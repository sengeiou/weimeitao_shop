package com.wmtc.wmtb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.TixianMainBean;
import com.wmtc.wmtb.mvp.event.ApplyEvent;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.presenter.TixainMainPresenter;
import com.wmtc.wmtb.ui.adapter.PreApplyListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/4/10.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PreApplyActivity extends CommonWmtActivity {

    private PreApplyListAdapter mAdapter;
    private TixainMainPresenter mPresenter;
    private TextView mTvSelAll;
    private TextView mTvAllMoney;

    private int allMoney = 0;
    private boolean isSelAll = false;
    private Button mBtnSure;
    int size = 0;
    private StringBuilder mStringBuilder = new StringBuilder();
    private TextView mTvOnLine;
    private TextView mTvOffLine;
    private TextView mTvBind;
    private String type = "1";
    private boolean isLoadding = false;

    @Override
    public int initAddLayout() {
        return R.layout.activity_pre_apply;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        showLoading();
        EventBus.getDefault().register(this);
        mTvSelAll = rootView.findViewById(R.id.tvSelAll);
        mTvAllMoney = rootView.findViewById(R.id.tvAllMoney);
        mBtnSure = rootView.findViewById(R.id.btnSure);

        mTvOnLine = rootView.findViewById(R.id.tvOnLine);
        mTvOffLine = rootView.findViewById(R.id.tvOffLine);
        mTvBind = rootView.findViewById(R.id.tvBind);


        mTvOnLine.setOnClickListener(v -> {
            if (!isLoadding) {
                allMoney = 0;
                type = "1";
                mTvAllMoney.setText("0.00");
                isSelAll = false;
                mTvSelAll.setText("全选");
                showLoading();
                netApplyList();
                setEnable(false, true, true);
            }

        });

        mTvOffLine.setOnClickListener(v -> {
            if (!isLoadding) {
                allMoney = 0;
                mTvAllMoney.setText("0.00");
                type = "2";
                isSelAll = false;
                mTvSelAll.setText("全选");
                showLoading();
                netApplyList();
                setEnable(true, false, true);
            }

        });
        mTvBind.setOnClickListener(v -> {
            if (!isLoadding) {
                allMoney = 0;
                mTvAllMoney.setText("0.00");
                type = "3";
                isSelAll = false;
                mTvSelAll.setText("全选");
                showLoading();
                netApplyList();
                setEnable(true, true, false);
            }
        });

        mAdapter = new PreApplyListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new TixainMainPresenter(this);
        netApplyList();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            size = 0;
            List<TixianMainBean.DataBean> data = mAdapter.getData();
            TixianMainBean.DataBean dataBean = data.get(position);
            dataBean.isSel = !dataBean.isSel;
            Observable.fromIterable(data).subscribe(bean -> {
                if (bean.isSel) {
                    ++size;
                }
            });
            if (size >= data.size()) {
                isSelAll = true;
                mTvSelAll.setText("取消全选");
            } else {
                isSelAll = false;
                mTvSelAll.setText("全选");

            }
            mAdapter.notifyItemChanged(position);
            fixMoney(dataBean.isSel, dataBean.projectAllPrice);
            return false;
        });
        mTvToolRight.setText("提现记录");
        mTvSelAll.setOnClickListener(v -> {
            isSelAll = !isSelAll;
            if (isSelAll) {
                allMoney = 0;
            }
            mTvSelAll.setText(isSelAll ? "取消全选" : "全选");
            Observable.fromIterable(mAdapter.getData()).subscribe(dataBean -> {
                dataBean.isSel = isSelAll;
                fixMoney(isSelAll, dataBean.projectAllPrice);
            }, throwable -> {
            });
            mAdapter.notifyDataSetChanged();
        });
        mBtnSure.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            List<TixianMainBean.DataBean> dataBeans = mAdapter.getData();
            for (int i = 0; i < dataBeans.size(); i++) {
                if (dataBeans.get(i).isSel) {
                    if ("2".equals(type)) {
                        mStringBuilder.append(dataBeans.get(i).offlineOrderId);
                    } else {
                        mStringBuilder.append(dataBeans.get(i).orderId);
                    }
                    mStringBuilder.append(",");
                }
            }
            if (mStringBuilder.length() < 1) {
                ToastUtils.init().showInfoToast(mActivity, "请选择需要提现的订单");
                return;
            }
            String orderId = mStringBuilder.toString().substring(0, mStringBuilder.length() - 1);
            bundle.putString("money", String.valueOf(allMoney));
            bundle.putString("orderId", orderId);
            bundle.putString("type", type);
            ActivityUtils.init().start(mActivity, ToApplyActivity.class, "提现", bundle);
            mStringBuilder.delete(0, mStringBuilder.length());
        });


    }

    public void setEnable(boolean on, boolean off, boolean bind) {
        mTvOnLine.setEnabled(on);
        mTvOffLine.setEnabled(off);
        mTvBind.setEnabled(bind);
    }

    private void netApplyList() {
        if (!isLoadding) {
            OrderPojo pojo = new OrderPojo();
            pojo.setTixianType(type);
            pojo.setShopId(WmtApplication.user_shopId);
            mPresenter.getTixianMain(pojo);
        }
        isLoadding = true;
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        ActivityUtils.init().start(mActivity, ApplyListActivity.class, "提现记录");
    }

    private void fixMoney(boolean isSel, String projectAllPrice) {
        if (StringUtils.isBlank(projectAllPrice)) {
            projectAllPrice = "0";
        }
        if (isSel) {
            allMoney += Integer.parseInt(projectAllPrice);
        } else {
            allMoney -= Integer.parseInt(projectAllPrice);
        }
        LogUtil.e(allMoney);
        mTvAllMoney.setText(String.format(Locale.CHINA, "%.2f", allMoney * 0.01));
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        allMoney = 0;
        mTvAllMoney.setText("0.00");
        isSelAll = false;
        mTvSelAll.setText("全选");
        netApplyList();
    }

    public void getPreList(TixianMainBean bean) {
        responseSuccess();
        if (bean.data != null) {
            mAdapter.setNewData(bean.data);
        } else {
            showEmpty();
        }
    }

    @Subscribe
    public void onEvent(ApplyEvent event) {
        refreshStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void complete() {
        isLoadding = false;
    }
}
