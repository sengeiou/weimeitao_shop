package com.wmtc.wmtb.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.CanNotTixianBean;
import com.wmtc.wmtb.mvp.bean.CardListBean;
import com.wmtc.wmtb.mvp.event.ApplyEvent;
import com.wmtc.wmtb.mvp.event.SelCardEvent;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.presenter.CardMainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ToApplyActivity extends CommonWmtActivity {
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    @BindView(R.id.tv_bank_num)
    TextView mTvBankNum;
    @BindView(R.id.tvNullCard)
    TextView mTvNullCard;
    @BindView(R.id.rl_bank)
    RelativeLayout mRlBank;
    @BindView(R.id.tv_put_money)
    TextView mTvPutMoney;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.btn_get_money)
    TextView mBtnGetMoney;
    private Unbinder mBind;
    private String mMoney;
    private String mOrderId;
    private CardMainPresenter mPresenter;
    private CardListBean.DataBean mDataBean;
    private String mType;

    @Override
    public int initAddLayout() {
        return R.layout.activity_to_apply;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mPresenter = new CardMainPresenter(this);
        mMoney = mBundle.getString("money");
        mOrderId = mBundle.getString("orderId");
        mType = mBundle.getString("type");
        mTvToolRight.setVisibility(View.INVISIBLE);
        mPresenter.getCard();
        mPresenter.getCanNotTixianAmount();
        mTvPutMoney.setText(StringUtils.init().fixNullStrMoney(mMoney));
        mRlBank.setOnClickListener(v -> {
            ActivityUtils.init().start(this, CardListActivity.class, "选择银行卡");
        });
        mBtnGetMoney.setOnClickListener(v -> {
            if (mDataBean != null) {
                addTixianRecord(mOrderId, WmtApplication.user_shopId, mDataBean.id, mDataBean.cardBank, mDataBean.cardNo);
            } else {
                ToastUtils.init().showInfoToast(mActivity, "请先选择需要提现的银行卡");
            }
        });
    }

    public void getCardDate(CardListBean bean) {
        responseSuccess();
        if (bean.data != null && bean.data.size() > 0) {
            mTvNullCard.setVisibility(View.INVISIBLE);
            mDataBean = bean.data.get(0);
            mTvBankName.setText(StringUtils.init().fixNullStr(mDataBean.cardBank));

            String cardNo = mDataBean.cardNo;
            if (cardNo.length() > 4) {
                String start = cardNo.substring(0, 4);
                String end = cardNo.substring(cardNo.length() - 4);
                cardNo = start + "********" + end;
            }
            mTvBankNum.setText(cardNo);
        } else {
            mTvNullCard.setVisibility(View.VISIBLE);
        }

    }

    public void getCanNotTixianAmountDate(CanNotTixianBean bean) {
        if (bean.data != null) {
            mTvMoney.setText(bean.data.totalAmount);
        }
    }

    //确认提现
    private void addTixianRecord(String orderId, String shopId, String bankId, String bankName, String cardNo) {
        OrderPojo pojo = new OrderPojo();
        pojo.setBankId(bankId);
        pojo.setBankName(bankName);
        pojo.setCardNo(cardNo);
        pojo.setOrderId(orderId);
        pojo.setShopId(shopId);
        pojo.setTixianType(mType);
        mPresenter.addTixianRecord(pojo);
    }

    //返回提现结果
    public void addTixianRecordDate(BaseBean bean) {
        ToastUtils.init().showInfoToast(mActivity, "提现成功");
        EventBus.getDefault().post(new ApplyEvent());
        finish();
    }

    @Subscribe
    public void onEvent(SelCardEvent event) {
        mTvNullCard.setVisibility(View.INVISIBLE);
        mDataBean = event.dataBean;
        mTvBankName.setText(StringUtils.init().fixNullStr(mDataBean.cardBank));

        String cardNo = mDataBean.cardNo;
        if (cardNo.length() > 4) {
            String start = cardNo.substring(0, 4);
            String end = cardNo.substring(cardNo.length() - 4);
            cardNo = start + "********" + end;
        }
        mTvBankNum.setText(cardNo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }

}
