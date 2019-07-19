package com.wmtc.wmtb.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.LoginBean;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.VerLoginBean;
import com.wmtc.wmtb.mvp.presenter.LoginPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@SuppressLint("Registered")
public abstract class CommonLoginActivity extends SuperBaseActivity {

    @BindView(R.id.viewToolBar)
    View mViewToolBar;
    @BindView(R.id.tvTitleTip)
    public TextView mTvTitleTip;
    @BindView(R.id.ivLoginBack)
    public ImageView mIvBack;
    @BindView(R.id.tvSubTitleTip)
    public TextView mTvSubTitleTip;
    @BindView(R.id.tvForgetPwd)
    public TextView mTvForgetPwd;
    @BindView(R.id.tvSmsCode)
    public TextView mTvSmsCode;
    @BindView(R.id.tvPasswordTip)
    public TextView mTvPasswordTip;
    @BindView(R.id.editView)
    public EditText mEditView;
    @BindView(R.id.btnNext)
    public Button btnNext;
    @BindView(R.id.tvToRegister)
    public Button btnRegister;
    @BindView(R.id.selectCity)
    public TextView mSelectCity;
    @BindView(R.id.llShopTip)
    public LinearLayout llShopTip;
    @BindView(R.id.tvRegisterT)
    public TextView tvRegisterT;
    @BindView(R.id.tvDoorT)
    public TextView tvDoorT;
    @BindView(R.id.tvPushT)
    public TextView tvPushT;
    @BindView(R.id.ivShow)
    public ImageButton ivShow;
    @BindView(R.id.line2)
    public View line2;
    @BindView(R.id.line1)
    public View line1;
    private Unbinder mBind;
    public LoginPresenter mPresenter;


    @Override
    protected int initRootLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBind = ButterKnife.bind(this, view);
        mPresenter = new LoginPresenter(this);
        mIvBack.setOnClickListener(v -> {
            finish();
        });
        WmtApplication.mActivityLogin.add(this);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(mViewToolBar).init();
    }

    public void verLogin(VerLoginBean verLoginBean) {

    }

    public void shopEnter(ShopsBean bean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        WmtApplication.mActivityLogin.remove(this);
    }

    public void toLogin(LoginBean bean) {
    }

    public void smsCode() {

    }

    public void smsVer() {

    }

}
