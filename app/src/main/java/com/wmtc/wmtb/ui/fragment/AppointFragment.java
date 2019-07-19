package com.wmtc.wmtb.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.wmtc.wmtb.MainActivity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.ui.activity.AllOrderActivity;
import com.wmtc.wmtb.ui.activity.AppointSetActivity;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/29.
 * com.wmtc.wmtb.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AppointFragment extends SuperBaseFragment {

    @BindView(R.id.all_order)
    TextView mAllOrder;
    @BindView(R.id.tv_set)
    TextView mTvSet;
    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    Unbinder unbinder;
    private BaseViewPagerFragmentAdapter<String, Fragment> mAdapter;

    @Override
    public int initLayout() {
        return R.layout.fragment_appoint;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        initImmersionBar();
        LinkedHashMap<String, Fragment> arrayMap = new LinkedHashMap<>();
        arrayMap.put("新订单", new AppointDetailFragment());
        arrayMap.put("待付尾款", new AppointDetailFragment());
        arrayMap.put("已取消", new AppointDetailFragment());
        mAdapter = new BaseViewPagerFragmentAdapter<>(getChildFragmentManager(), arrayMap);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                AppointDetailFragment item = (AppointDetailFragment) mAdapter.getItem(position);
                String type;
                if (position == 0) {
                    type = "3";
                } else if (position == 1) {
                    type = "5";
                } else {
                    type = "CANCEL";
                }
                item.setType(type);
            }
        });
        Observable.timer(500, TimeUnit.MILLISECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
            AppointDetailFragment item = (AppointDetailFragment) mAdapter.getItem(0);
            item.setType("3");
        });
        mAllOrder.setOnClickListener(v -> {
            ActivityUtils.init().start(mActivity, AllOrderActivity.class, "全部订单");
        });
        mTvSet.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) mActivity;
            ShopsBean.DataBean shop = activity.mShop;
            Bundle bundle = new Bundle();
            bundle.putString("restDay", StringUtils.init().fixNullStr(shop.closetime));
            bundle.putString("openTime", StringUtils.init().fixNullStr(shop.openTime));
            bundle.putString("endTime", StringUtils.init().fixNullStr(shop.endTime));
            bundle.putString("sendPhone", StringUtils.init().fixNullStr(shop.sendphone));
            bundle.putString("average", StringUtils.init().fixNullStr(shop.average));
            bundle.putString("teachNum", StringUtils.init().fixNullStr(shop.teachNum + ""));
            ActivityUtils.init().start(mActivity, AppointSetActivity.class, "预约设置", bundle);
        });
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.viewStatusBarAppoint).init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
