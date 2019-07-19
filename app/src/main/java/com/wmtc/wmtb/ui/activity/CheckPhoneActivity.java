package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.BaseBean;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.event.AddCardOkEvent;
import com.wmtc.wmtb.mvp.event.ApplyEvent;
import com.wmtc.wmtb.mvp.pojo.CardPojo;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;
import com.wmtc.wmtb.mvp.presenter.CheckPhoneCardPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

public class CheckPhoneActivity extends CommonWmtActivity {

    @BindView(R.id.tv_msg)
    TextView mTvMsg;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.btn_send_code)
    TextView mBtnSendCode;
    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.btn_ok)
    TextView mBtnOk;
    private CheckPhoneCardPresenter presenter;
    private Unbinder mBind;
    private String mCardBank;
    private String mCardNo;
    private String mTelPhone;
    private String mUserName;
    private String mUserType;
    private String mUserId;

    @Override
    public int initAddLayout() {
        return R.layout.activity_cared_check_phone;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this);
        mTvToolRight.setVisibility(View.INVISIBLE);
        presenter = new CheckPhoneCardPresenter(this);
        mCardBank = mBundle.getString("cardBank");
        mCardNo = mBundle.getString("cardNo");
        mTelPhone = mBundle.getString("telPhone");
        mUserName = mBundle.getString("userName");
        mUserType = "2";
        mUserId = BaseInitApplication.mHeardMap.get("id");
        StringUtils.init().getSmCode(mBtnSendCode);
        mBtnSendCode.setOnClickListener(v -> {
            SmsCodePojo pojo = new SmsCodePojo();
            pojo.setTel(mTelPhone);
            presenter.smsCode(pojo);
        });
        mBtnOk.setOnClickListener(v -> {
            VerLoginPojo pojo = new VerLoginPojo();
            String code = StringUtils.init().fixNullStr(mEtNum.getText());
            pojo.setPhone(mTelPhone);
            pojo.setCode(code);
            presenter.smsVer(pojo);
        });
        mTvMsg.setText(String.format(Locale.CHINA, "接收验证的手机号:%s", mTelPhone));
    }

    public void smsCodeDate() {
        StringUtils.init().getSmCode(mBtnSendCode);
    }

    public void smsVerDate() {
        CardPojo pojo = new CardPojo();
        pojo.setCardBank(mCardBank);
        pojo.setCardNo(mCardNo);
        pojo.setTelPhone(mTelPhone);
        pojo.setUserId(mUserId);
        pojo.setUserName(mUserName);
        pojo.setUserType(mUserType);
        presenter.addCard(pojo);
    }


    public void addCardDate() {
        EventBus.getDefault().post(new AddCardOkEvent());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        KeyboardUtils.init().hideSoftInput(this);
    }
}
