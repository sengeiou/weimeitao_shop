package com.wmtc.wmtb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.event.ShareAllEvent;
import com.wmtc.wmtb.mvp.event.ShareOneEvent;

import org.greenrobot.eventbus.EventBus;

import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2019/3/29.
 * com.wmtc.wmtb.ui.dialog
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShareDialog extends BaseCustomDialog {


    public ShareDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.tvOneShare).setOnClickListener(v -> {
            EventBus.getDefault().post(new ShareOneEvent());
            this.dismiss();
        });
        view.findViewById(R.id.tvAllShare).setOnClickListener(v -> {
            EventBus.getDefault().post(new ShareAllEvent());
            this.dismiss();
        });
        view.findViewById(R.id.tvClose).setOnClickListener(v -> this.dismiss());
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int setAnim() {
        return R.style.AnimBottom;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_share;
    }
}
