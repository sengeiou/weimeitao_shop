package com.wmtc.wmtb.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import top.jplayer.baseprolibrary.ui.dialog.DialogLogout;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2018/3/15.
 * top.jplayer.baseprolibrary.widgets.dialog
 */

public class DialogLogoutTip extends BaseCustomDialog {

    private View mCancel;

    public DialogLogoutTip(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mCancel = view.findViewById(top.jplayer.baseprolibrary.R.id.btnCancel);
        mCancel.setOnClickListener(v -> cancel());
    }

    public DialogLogoutTip setTitle(String title) {
        bindText(title, top.jplayer.baseprolibrary.R.id.tvTitle);
        return this;
    }

    public DialogLogoutTip setSubTitle(String title) {
        bindText(title, top.jplayer.baseprolibrary.R.id.tvSubTitle);
        return this;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(false);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(false);

    }

    @Override
    public void onBackPressed() {
        LogUtil.e("不让你点");
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(8);
    }

    @Override
    public int setAnim() {
        return top.jplayer.baseprolibrary.R.style.AnimFade;
    }

    @Override
    public int setGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int initLayout() {
        return top.jplayer.baseprolibrary.R.layout.dialog_logout;
    }

    public DialogLogoutTip setForce(boolean force) {
        mCancel.setVisibility(force ? View.GONE : View.VISIBLE);
        return this;
    }
}
