package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;

import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class VerCodeActivity extends CommonLoginActivity {

    String mRegister;

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        if (mBundle != null) {
            mRegister = mBundle.getString("llTip");
            if (mRegister != null && mRegister.contains("register")) {
                llShopTip.setVisibility(View.VISIBLE);
            }
        }
        mTvSmsCode.setVisibility(View.VISIBLE);
        mTvTitleTip.setText("请输入您的验证码");
        String phone = "验证码已发送到您的手机\n86+" + WmtApplication.user_phone;
        mTvSubTitleTip.setText(phone);
        btnNext.setText("下一步");
        mEditView.setHint("请输入验证码");
        mTvSmsCode.setOnClickListener(v -> {
            SmsCodePojo pojo = new SmsCodePojo();
            pojo.setTel(WmtApplication.user_phone);
            mPresenter.smsCode(pojo);
        });
        StringUtils.init().getSmCode(mTvSmsCode);
        btnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditView)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入验证码");
                return;
            }
            VerLoginPojo pojo = new VerLoginPojo();
            String code = StringUtils.init().fixNullStr(mEditView.getText());
            pojo.setPhone(WmtApplication.user_phone);
            pojo.setCode(code);
            mPresenter.smsVer(pojo);
        });
    }

    @Override
    public void smsCode() {
        super.smsCode();
        StringUtils.init().getSmCode(mTvSmsCode);
    }

    @Override
    public void smsVer() {
        super.smsVer();
        Bundle bundle = new Bundle();
        bundle.putString("llTip", "register");
        bundle.putBoolean("forget", mBundle.getBoolean("forget"));
        ActivityUtils.init().start(mActivity, SetPasswordActivity.class, "", bundle);
    }
}
