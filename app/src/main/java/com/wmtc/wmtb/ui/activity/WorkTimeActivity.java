package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.StatusBean;
import com.wmtc.wmtb.mvp.event.TeachUpdateEvent;
import com.wmtc.wmtb.mvp.pojo.TeachInfoPojo;
import com.wmtc.wmtb.mvp.presenter.WorkTimePresenter;
import com.wmtc.wmtb.ui.adapter.WorkTimeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class WorkTimeActivity extends CommonWmtActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvSelect)
    TextView mTvSelect;
    @BindView(R.id.llIdType)
    LinearLayout mLlIdType;
    private Unbinder mBind;
    private WorkTimeAdapter mAdapter;
    private PickerUtils mPickerUtils;
    private String[] mSplit = new String[]{};
    private WorkTimePresenter mPresenter;
    private TeachInfoPojo mPojo;

    @Override
    public int initAddLayout() {
        return R.layout.activity_work_time;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this, rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        String restDay = mBundle.getString("restDay");
        ArrayList<String> days = new ArrayList<>();
        if (restDay != null && restDay.contains(",")) {
            String[] split = restDay.split(",");
            days.addAll(Arrays.asList(split));
        } else {
            days.add(restDay);
        }
        String workTime = mBundle.getString("workTime");
        if (StringUtils.isNotBlank(workTime)) {
            mSplit = workTime.split("");
            mTvSelect.setText(workTime);
        }
        ArrayList<StatusBean> list = new ArrayList<>();
        list.add(new StatusBean("星期一", days.contains("星期一")));
        list.add(new StatusBean("星期二", days.contains("星期二")));
        list.add(new StatusBean("星期三", days.contains("星期三")));
        list.add(new StatusBean("星期四", days.contains("星期四")));
        list.add(new StatusBean("星期五", days.contains("星期五")));
        list.add(new StatusBean("星期六", days.contains("星期六")));
        list.add(new StatusBean("星期日", days.contains("星期日")));
        mAdapter = new WorkTimeAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            StatusBean statusBean = mAdapter.getData().get(position);
            statusBean.isSel = !statusBean.isSel;
            mAdapter.notifyItemChanged(position);
        });
        mPickerUtils = new PickerUtils();
        mTvSelect.setOnClickListener(v -> {
            mPickerUtils.initTimePicker(mActivity, mSplit, (date, patternDate) -> {
                LogUtil.e(patternDate);
                mTvSelect.setText(patternDate);
            });
            mPickerUtils.timeShow();
        });
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        mPojo = new TeachInfoPojo();
        StringBuilder builder = new StringBuilder();
        for (StatusBean datum : mAdapter.getData()) {
            if (datum.isSel) {
                builder.append(datum.key);
                builder.append(",");
            }
        }
        String restDay = builder.toString();
        if (StringUtils.isNotBlank(restDay)) {
            String s = restDay.substring(0, restDay.length() - 1);
            mPojo.setRestDay(s);
        }else {
            mPojo.setRestDay("");
        }
        mPojo.setStatus(mBundle.getString("status"));
        mPojo.setId(mBundle.getString("id"));
        mPojo.setUpdateBy(WmtApplication.user_shopId);
        mPresenter = new WorkTimePresenter(this);
        mPresenter.updateTechnicianDetail(mPojo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    public void netOk(String s) {
        EventBus.getDefault().post(new TeachUpdateEvent());
        Disposable subscribe =
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    finish();
                });
    }
}
