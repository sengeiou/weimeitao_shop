package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.event.CreateProjectEvent;
import com.wmtc.wmtb.ui.fragment.ProjectListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2018/9/4.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ProjectsTabActivity extends CommonWmtActivity {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private Unbinder mBind;
    private LinkedHashMap<String, Fragment> mMap;
    private BaseViewPagerFragmentAdapter<String, Fragment> mAdapter;
    private ProjectListFragment mFAll;

    @SuppressLint("CheckResult")
    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mMap = new LinkedHashMap<>();
        mFAll = new ProjectListFragment();
        mMap.put("全部", mFAll);
        mMap.put("待上线", new ProjectListFragment());
        mMap.put("已上线", new ProjectListFragment());
        mMap.put("已下线", new ProjectListFragment());
        mAdapter = new BaseViewPagerFragmentAdapter<>(getSupportFragmentManager(), mMap);
        mViewPager.setAdapter(mAdapter);
        mTvToolRight.setText("新增项目");
        mTabLayout.setupWithViewPager(mViewPager);
        //项目状态：未提交2、待审核3、审核通过4、审核不通过5、待上线6、已上线1、已下线7、删除0
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ProjectListFragment fragment = (ProjectListFragment) mAdapter.getItem(position);
                refresh(position, fragment);
            }
        });
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            mFAll.setStatus("1,2,3,4,5,6,7");
        });
    }

    private void refresh(int position, ProjectListFragment fragment) {
        if (position == 0) {
            fragment.setStatus("1,2,3,4,5,6,7");
        } else if (position == 1) {
            fragment.setStatus("6");
        } else if (position == 2) {
            fragment.setStatus("1");
        } else if (position == 3) {
            fragment.setStatus("7");
        }
    }

    @Override
    public int initAddLayout() {
        return R.layout.activity_projects_tab;
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        Bundle bundle = new Bundle();
        bundle.putString("edit", "0");
        ActivityUtils.init().start(mActivity, ProjectActivity.class, "录入项目", bundle);
    }

    @Subscribe
    public void onEvent(CreateProjectEvent event) {
        int position = mViewPager.getCurrentItem();
        ProjectListFragment fragment = (ProjectListFragment) mAdapter.getItem(position);
        refresh(position, fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
