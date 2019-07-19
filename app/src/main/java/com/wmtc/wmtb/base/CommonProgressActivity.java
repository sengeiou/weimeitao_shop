package com.wmtc.wmtb.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.ShopsBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public abstract class CommonProgressActivity extends SuperBaseActivity {
    @BindView(R.id.ivLoginBack)
    public ImageView mIvLoginBack;
    @BindView(R.id.tvTitleTip)
    public TextView mTvTitleTip;
    @BindView(R.id.tvSubTitleTip)
    public TextView mTvSubTitleTip;
    @BindView(R.id.tvRegisterT)
    public TextView mTvRegisterT;
    @BindView(R.id.line1)
    public View mLine1;
    @BindView(R.id.tvDoorT)
    public TextView mTvDoorT;
    @BindView(R.id.line2)
    public View mLine2;
    @BindView(R.id.tvPushT)
    public TextView mTvPushT;
    @BindView(R.id.tvRemark)
    public TextView mTvRemark;
    @BindView(R.id.llShopTip)
    public LinearLayout mLlShopTip;
    @BindView(R.id.btnNext)
    public Button mBtnNext;
    private Unbinder mBind;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_common_progress;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBind = ButterKnife.bind(this);
        mIvLoginBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.viewToolBar).init();
    }

    public void shopEnter(ShopsBean bean) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
