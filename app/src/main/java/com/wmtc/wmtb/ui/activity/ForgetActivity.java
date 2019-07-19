package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.pojo.SmsCodePojo;

import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForgetActivity extends CommonLoginActivity {
    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mTvTitleTip.setText("忘记密码");
        mTvSubTitleTip.setText("验证手机号，然后重新设置密码");
        btnNext.setText("获取验证码");
        String user_phone = WmtApplication.user_phone;
        if (StringUtils.isNotBlank(user_phone)) {
            mEditView.setText(user_phone);
        } else {
            mEditView.setHint("+86");
        }
        btnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditView)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入手机号");
                return;
            }
            SmsCodePojo pojo = new SmsCodePojo();
            String phone = StringUtils.init().fixNullStr(mEditView.getText());
            WmtApplication.user_phone = phone;
            pojo.setTel(phone);
            mPresenter.smsCode(pojo);
        });
    }

    @Override
    public void smsCode() {
        super.smsCode();
        Bundle bundle = new Bundle();
        bundle.putBoolean("forget", true);
        ActivityUtils.init().start(mActivity, VerCodeActivity.class, "", bundle);
    }
}
