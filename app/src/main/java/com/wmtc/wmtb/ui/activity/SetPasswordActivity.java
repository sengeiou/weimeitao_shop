package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.wmtc.wmtb.base.CommonLoginActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.LoginBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.pojo.VerLoginPojo;

import java.util.List;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SetPasswordActivity extends CommonLoginActivity {
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
        mTvTitleTip.setText("设置账号密码");
        String phone = "完成密码设置\n86+" + WmtApplication.user_phone;
        mTvSubTitleTip.setText(phone);
        mEditView.setHint("设置密码");
        btnNext.setText("完成");
        mTvPasswordTip.setVisibility(View.VISIBLE);
        btnNext.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditView)) {
                ToastUtils.init().showInfoToast(mActivity, "请设置账号密码");
                return;
            }
            VerLoginPojo pojo = new VerLoginPojo();
            pojo.setPhone(WmtApplication.user_phone);
            String password = StringUtils.init().fixNullStr(mEditView.getText());
            WmtApplication.user_pwd = password;
            SharePreUtil.saveData(mActivity, "user_phone", phone);
            SharePreUtil.saveData(mActivity, "user_password", password);
            pojo.setPassword(password);
            if (mBundle.getBoolean("forget")) {
                mPresenter.forget(pojo);
            } else {
                mPresenter.register(pojo);
            }
        });
        ivShow.setVisibility(View.VISIBLE);
        mEditView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ivShow.setOnClickListener(v -> {
            ivShow.setSelected(!ivShow.isSelected());
            mEditView.setInputType(ivShow.isSelected() ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditView.setSelection(mEditView.getText().length());
        });
    }

    @Override
    public void toLogin(LoginBean bean) {
        super.toLogin(bean);
        LoginBean.DataBean data = bean.data;
        if (data != null) {
            BaseInitApplication.mHeardMap.put("token", data.token);
            BaseInitApplication.mHeardMap.put("id", data.userId);
            SharePreUtil.saveData(mActivity, "token", data.token);
            SharePreUtil.saveData(mActivity, "uid", data.userId);
            mPresenter.shopEnter(data.userId);
        }
    }

    @Override
    public void shopEnter(ShopsBean bean) {
        List<ShopsBean.DataBean> data = bean.data;
        Bundle bundle = new Bundle();
        if (data != null && data.size() > 0) {
            WmtApplication.user_avatar =
                    WmtApplication.url_host + data.get(0).userAvatar;
            bundle.putParcelable("shop", data.get(0));
            ActivityUtils.init().start(mActivity, ShopDoorAgainActivity.class, "", bundle);
        } else {
            bundle.putParcelable("shop", null);
            ActivityUtils.init().start(mActivity, ShopDoorAgainActivity.class, "", bundle);
        }
    }
}
