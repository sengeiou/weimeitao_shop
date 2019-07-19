package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;

import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.ui.activity.WebViewActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PreRegisterActivity extends CommonLoginActivity {

    SpannableString mSpannableString;

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mTvTitleTip.setText("请输入手机号");
        String sub = "为了方便联系，请输入您常用的手机号码";
        mTvSubTitleTip.setText(sub);
        mEditView.setHint("+86");
        btnNext.setText("获取验证码");
        mTvPasswordTip.setVisibility(View.VISIBLE);
        llShopTip.setVisibility(View.VISIBLE);
        String spannable = "登录/注册表示同意用户协议、隐私条款";
        mSpannableString = new SpannableString(spannable);
        //设置字体前景色
        mSpannableString.setSpan(new ForegroundColorSpan(
                        getResources().getColor(R.color.colorPrimary)), 9,
                spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTvPasswordTip.setText(mSpannableString);
        mTvPasswordTip.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", BuildConfig.HOST + "h5/index.html#/pages/index/seconde");
            ActivityUtils.init().start(mActivity, WebViewActivity.class, "用户协议", bundle);
        });
        btnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditView)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入手机号");
                return;
            }
            SmsCodePojo smsCodePojo = new SmsCodePojo();
            String phone = StringUtils.init().fixNullStr(mEditView.getText());
            smsCodePojo.setTel(phone);
            WmtApplication.user_phone = phone;
            mPresenter.smsCode(smsCodePojo);
        });

    }

    @Override
    public void smsCode() {
        super.smsCode();
        Bundle bundle = new Bundle();
        bundle.putString("llTip", "register");
        bundle.putBoolean("forget", false);
        ActivityUtils.init().start(mActivity, VerCodeActivity.class, "", bundle);
    }
}
