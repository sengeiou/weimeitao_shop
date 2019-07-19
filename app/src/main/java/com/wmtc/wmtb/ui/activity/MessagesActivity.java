package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.view.View;
import android.widget.FrameLayout;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.event.CreateProjectEvent;
import com.wmtc.wmtb.ui.fragment.NoticeMsgListFragment;
import com.wmtc.wmtb.ui.fragment.ProjectListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jmessage.biz.httptask.task.GetEventNotificationTaskMng;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.ui.fragment.TestFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/3/30.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MessagesActivity extends CommonWmtActivity {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private Unbinder mBind;
    private LinkedHashMap<String, Fragment> mMap;
    private BaseViewPagerFragmentAdapter<String, Fragment> mAdapter;
    private NoticeMsgListFragment noticeF;

    @Override
    public int initAddLayout() {
        return R.layout.activity_messages;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this, rootView);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        JMessageClient.registerEventReceiver(this);
        mMap = new LinkedHashMap<>();
        noticeF = new NoticeMsgListFragment();
        mMap.put("通知", noticeF);
        mMap.put("互动消息", new NoticeMsgListFragment());
        mMap.put("客服助手", new NoticeMsgListFragment());
        mMap.put("平台活动", new NoticeMsgListFragment());
        mAdapter = new BaseViewPagerFragmentAdapter<>(getSupportFragmentManager(), mMap);
        mViewPager.setAdapter(mAdapter);
        mTvToolRight.setVisibility(View.INVISIBLE);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                NoticeMsgListFragment fragment = (NoticeMsgListFragment) mAdapter.getItem(position);
                refresh(position, fragment);
            }
        });
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            refresh(0, noticeF);
        });
    }

    public void onEventMainThread(MessageEvent event) {
        int currentItem = mViewPager.getCurrentItem();
        NoticeMsgListFragment fragment = (NoticeMsgListFragment) mAdapter.getItem(currentItem);
        fragment.refreshDate(currentItem, "");
        LogUtil.e("--------------" + currentItem);
    }

    private void refresh(int position, NoticeMsgListFragment fragment) {
        if (position == 0) {
            fragment.refreshDate(position, "notice");
        } else if (position == 1) {
            fragment.refreshDate(position, "");
        } else if (position == 2) {
            fragment.refreshDate(position, "");
        } else if (position == 3) {
            fragment.refreshDate(position, "huodong");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        JMessageClient.unRegisterEventReceiver(this);
    }
}
