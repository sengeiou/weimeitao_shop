package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.event.LocalSelEvent;
import com.wmtc.wmtb.mvp.event.ShopTextEditEvent;
import com.wmtc.wmtb.mvp.presenter.ShopTextEditPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopTextEditActivity extends CommonWmtActivity {
    @BindView(R.id.ivAvatar)
    PolygonImageView mIvAvatar;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvSubTitle)
    TextView mTvSubTitle;
    @BindView(R.id.tvName)
    EditText mTvName;
    @BindView(R.id.tvPhone)
    EditText mTvPhone;
    @BindView(R.id.rbOpen)
    RadioButton mRbOpen;
    @BindView(R.id.rbClose)
    RadioButton mRbClose;
    @BindView(R.id.tvAddress)
    TextView mTvAddress;
    private Unbinder mBind;
    private ShopTextEditPresenter mPresenter;
    private HashMap<String, String> mStringMap;
    private ShopsBean.DataBean mShop;

    @Override
    public int initAddLayout() {
        return R.layout.activity_shop_textedit;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mShop = mBundle.getParcelable("shop");
        mBind = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mPresenter = new ShopTextEditPresenter(this);
        mStringMap = new HashMap<>();
        if (mShop != null) {
            String name = StringUtils.init().fixNullStr(mShop.shopName);
            mTvName.setText(name);
            mTvTitle.setText(name);
            String address = StringUtils.init().fixNullStr(mShop.address);
            mTvAddress.setText(address);
            mTvSubTitle.setText(address);
            mTvPhone.setText(mShop.shopPhone);
            boolean isOPen = "在营业".equals(mShop.shopstatus);
            mRbOpen.setChecked(isOPen);
            mRbClose.setChecked(!isOPen);
            mStringMap.put("shopId", mShop.id + "");
            mStringMap.put("shopUserId", mShop.shopUserId + "");
            mTvAddress.setOnClickListener(v -> {
                ActivityUtils.init().start(mActivity, MapLocalActivity.class, "地址选择");
            });
            Glide.with(mActivity).load(WmtApplication.user_avatar).into(mIvAvatar);
        }
    }

    @Subscribe
    public void onEvent(LocalSelEvent event) {
        Map<String, String> stringMap = event.stringMap;
        if (stringMap != null && stringMap.size() > 0) {
            mStringMap.putAll(stringMap);
            mTvAddress.setText(mStringMap.get("address"));
        }
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        mStringMap.put("address", StringUtils.init().fixNullStr(mTvAddress.getText()));
        mStringMap.put("shopName", StringUtils.init().fixNullStr(mTvName.getText()));
        mStringMap.put("shopstatus", mRbOpen.isChecked() ? "在营业" : "未营业");
        mStringMap.put("shopPhone",StringUtils.init().fixNullStr(mTvPhone.getText()));
        mPresenter.createShop(mStringMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);

    }

    public void netOk() {
        EventBus.getDefault().post(new ShopTextEditEvent());
        Disposable subscribe =
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    finish();
                });
    }
}
