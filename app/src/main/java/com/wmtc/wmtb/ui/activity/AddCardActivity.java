package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.CardInforBean;
import com.wmtc.wmtb.mvp.bean.CardNameBean;
import com.wmtc.wmtb.mvp.event.AddCardOkEvent;
import com.wmtc.wmtb.mvp.pojo.DictPojo;
import com.wmtc.wmtb.mvp.pojo.OrderPojo;
import com.wmtc.wmtb.mvp.pojo.SendCodePojo;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.mvp.presenter.AddCardPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

public class AddCardActivity extends CommonWmtActivity {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.btn_next)
    TextView mBtnNext;
    private AddCardPresenter mPresenter;
    private Unbinder mBind;
    private PickerUtils mPickerUtils;

    @Override
    public int initAddLayout() {
        return R.layout.activity_add_card;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mPresenter = new AddCardPresenter(this);
        mPresenter.getInfor();
        mPresenter.dictListName();
        mPickerUtils = new PickerUtils();
        mTvToolRight.setVisibility(View.INVISIBLE);
        mBtnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mTvBank)) {
                ToastUtils.init().showInfoToast(mActivity, "请选择银行卡类型");
                return;
            }
            if (StringUtils.init().isEmpty(mEtNum)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入持卡人银行卡号");
                return;
            }
            if (StringUtils.init().isEmpty(mTvName)) {
                ToastUtils.init().showInfoToast(mActivity, "未绑定持卡人姓名");
                return;
            }
            if (StringUtils.init().isEmpty(mTvPhone)) {
                ToastUtils.init().showInfoToast(mActivity, "未绑定持卡人手机号");
                return;
            }
            smsCode(mTvPhone.getText().toString());
        });
        mTvBank.setOnClickListener(v -> {
            mPickerUtils.stringShow();
        });
    }

    @SuppressLint("CheckResult")
    public void dictListDate(CardNameBean bean) {
        if (bean.data != null) {
            List<String> list = new ArrayList<>();
            Observable.fromIterable(bean.data).subscribe(dataBean -> {
                list.add(dataBean.name);
            });
            mPickerUtils.initStringPicker(list, 0, mActivity, (position, string) -> {
                mTvBank.setText(string);
            });
        }
    }

    public void getInforDate(CardInforBean bean) {
        CardInforBean.DataBean data = bean.data;
        if (data != null) {
            mTvName.setText(StringUtils.init().fixNullStr(data.idcardname));
            mTvPhone.setText(StringUtils.init().fixNullStr(data.shopPhone));
        }
    }

    private void smsCode(String phone) {
        SendCodePojo pojo = new SendCodePojo();
        pojo.setTel(phone);
        pojo.setTemplateCode("SMS_167180906");
        mPresenter.sendCode(pojo);
    }

    public void smsCodeDate() {
        Bundle bundle = new Bundle();
        bundle.putString("cardNo", StringUtils.init().fixNullStr(mEtNum.getText()));
        bundle.putString("cardBank", StringUtils.init().fixNullStr(mTvBank.getText()));
        bundle.putString("telPhone", StringUtils.init().fixNullStr(mTvPhone.getText()));
        bundle.putString("userName", StringUtils.init().fixNullStr(mTvName.getText()));
        ActivityUtils.init().start(mActivity, CheckPhoneActivity.class, "验证手机号", bundle);
    }

    @Subscribe
    public void onEvent(AddCardOkEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
        KeyboardUtils.init().hideSoftInput(this);
    }
}
