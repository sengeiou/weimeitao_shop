package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.VerLoginBean;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LoginActivity extends CommonLoginActivity {


    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        btnRegister.setVisibility(View.VISIBLE);
        mIvBack.setVisibility(View.INVISIBLE);
        btnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditView)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入手机号");
                return;
            }
            VerLoginPojo verLoginPojo = new VerLoginPojo();
            String phone = StringUtils.init().fixNullStr(mEditView.getText().toString());
            WmtApplication.user_phone = phone;
            verLoginPojo.setPhone(phone);
            mPresenter.verLogin(verLoginPojo);
        });
        btnRegister.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, PreRegisterActivity.class);
        });
        isOpenDoubleBack = true;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @SuppressLint("CheckResult")
    @Override
    public void verLogin(VerLoginBean verLoginBean) {
        VerLoginBean.DataBean data = verLoginBean.data;
        if (data != null) {
            if (StringUtils.contains(data.stockNo, "已绑定")) {
                if (StringUtils.contains(data.stockPwd, "已绑定")) {
                    //login
                    ActivityUtils.init().start(mActivity, PasswordActivity.class);
                } else {
                    //forget
                    ToastUtils.init().showInfoToast(mActivity, "检测到当前账号未设置密码\n请验证手机号并设置");
                    Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("", "");
                        ActivityUtils.init().start(mActivity, ForgetActivity.class, "", bundle);
                    }, throwable -> {
                    });
                }
            } else {
                ToastUtils.init().showInfoToast(mActivity, "检测到当前账号未入驻，请前往入驻");
            }
        }
    }
}
