package com.wmtc.wmtb.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.mvp.bean.StatusBean;
import com.wmtc.wmtb.mvp.event.ShopAppointEvent;
import com.wmtc.wmtb.mvp.event.ShopTextEditEvent;
import com.wmtc.wmtb.mvp.event.TeachUpdateEvent;
import com.wmtc.wmtb.mvp.pojo.AppointPojo;
import com.wmtc.wmtb.mvp.presenter.AppointSetPresenter;
import com.wmtc.wmtb.ui.adapter.WorkTimeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.AndroidBug5497Workaround;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AppointSetActivity extends CommonWmtActivity {
    @BindView(R.id.tvOpenTime)
    TextView mTvOpenTime;
    @BindView(R.id.tvEndTime)
    TextView mTvEndTime;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvShopAppointPhone)
    EditText mTvShopAppointPhone;
    @BindView(R.id.tvTeachNum)
    EditText tvTeachNum;
    @BindView(R.id.tvAverage)
    EditText mTvAverage;
    private Unbinder mUnbinder;
    private PickerUtils mPickerUtils;
    private String[] mSplit = new String[]{};
    private WorkTimeAdapter mAdapter;
    private HashMap<String, String> mMap;
    private AppointPojo mAppointPojo;
    private AppointSetPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_appoint_set;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        AndroidBug5497Workaround.assistActivity(this);
        mPickerUtils = new PickerUtils();
        List<String> time = mPickerUtils.getTime();
        mPresenter = new AppointSetPresenter(this);
        mTvOpenTime.setOnClickListener(v -> {
            mPickerUtils.initStringPicker(time, 48, mActivity, (position, string) -> {
                String timePre = string + ":00";
                mTvOpenTime.setText(timePre);
            });
            mPickerUtils.stringShow();
        });
        mTvEndTime.setOnClickListener(v -> {
            mPickerUtils.initStringPicker(time, 120, mActivity, (position, string) -> {
                String timePre = string + ":00";
                mTvEndTime.setText(timePre);
            });
            mPickerUtils.stringShow();
        });

        String restDay = mBundle.getString("restDay");
        String openTime = mBundle.getString("openTime");
        String endTime = mBundle.getString("endTime");
        String average = mBundle.getString("average");
        String sendPhone = mBundle.getString("sendPhone");
        String teachNum = mBundle.getString("teachNum");
        mTvAverage.setText(StringUtils.init().fixNullStr(average));
        mTvOpenTime.setText(StringUtils.init().fixNullStr(openTime));
        mTvEndTime.setText(StringUtils.init().fixNullStr(endTime));
        mTvShopAppointPhone.setText(StringUtils.init().fixNullStr(sendPhone));
        tvTeachNum.setText(StringUtils.init().fixNullStr(teachNum));
        tvTeachNum.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("shopName", mBundle.getString("shopName"));
//            ActivityUtils.init().start(mActivity, CreateTeachActivity.class, "添加技师", bundle);
            ToastUtils.init().showInfoToast(mActivity, "请前往技师管理，添加技师");
        });
        ArrayList<String> days = new ArrayList<>();
        if (restDay != null && restDay.contains(",")) {
            String[] split = restDay.split(",");
            days.addAll(Arrays.asList(split));
        } else {
            days.add(restDay);
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
        mAppointPojo = new AppointPojo();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        mAppointPojo.setAverage(StringUtils.init().fixNullStr(mTvAverage.getText()));
        mAppointPojo.setEndTime(StringUtils.init().fixNullStr(mTvEndTime.getText()));
        mAppointPojo.setOpenTime(StringUtils.init().fixNullStr(mTvOpenTime.getText()));
        mAppointPojo.setSendPhone(StringUtils.init().fixNullStr(mTvShopAppointPhone.getText()));
        mAppointPojo.setShopId(WmtApplication.user_shopId);
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
            mAppointPojo.setCloseTime(s);
        }else {
            mAppointPojo.setCloseTime("");
        }
        mPresenter.appointSet(mAppointPojo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void netOk() {
        EventBus.getDefault().post(new ShopTextEditEvent());
        Disposable subscribe =
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    EventBus.getDefault().post(new ShopAppointEvent());
                    finish();
                });
    }

    public void shopEnter(ShopsBean bean) {

    }
}
