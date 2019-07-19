package com.wmtc.wmtb.ui.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.NewOrderBean;
import com.wmtc.wmtb.ui.fragment.MainFragment;
import com.wmtc.wmtb.util.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerViewAdapter;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AdapterNewOrder extends BaseViewPagerViewAdapter<NewOrderBean.DataBean.ListBean> {
    public List<Disposable> mDisposables;

    public AdapterNewOrder(List<NewOrderBean.DataBean.ListBean> list) {
        super(list);
        mDisposables = new ArrayList<>();
        addItemType(NewOrderBean.DataBean.ListBean.INT_NOTICE, R.layout.dapter_new_order);
    }

    @Override
    public int getItemType(int position) {
        return list.get(position).getType();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void bindView(View view, NewOrderBean.DataBean.ListBean bean, int position) {
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvOKSel = view.findViewById(R.id.tvOKSel);
        TextView tvNoSel = view.findViewById(R.id.tvNoSel);
        TextView tvClickDetail = view.findViewById(R.id.tvClickDetail);
        tvOKSel.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.okListener(bean, position);
            }
        });
        tvNoSel.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.noListener(bean, position);
            }
        });
        tvClickDetail.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.detail(bean, position);
            }
        });
        switch (getItemType(position)) {
            case NewOrderBean.DataBean.ListBean.INT_NOTICE:
                try {
                    if (bean.arrivalTime != null) {
                        String md = TimeUtil.init().getMD(bean.arrivalTime);
                        String week = TimeUtil.init().getWeek(bean.arrivalTime);
                        String[] split = bean.arrivalTime.split(" ");
                        String text = md + " (" + week + ") " + split[1].substring(0, 5);
                        tvTime.setText(text);
                    }
                    String name = StringUtils.init().fixNullStr(bean.technicianName);
                    String proName = StringUtils.init().fixNullStr(bean.projectName);
                    String text = name + " | " + proName;
                    tvName.setText(text);
                    Disposable disposable = downTime(bean.projectPriceFirstPaidTime, tvOKSel);
                    if (disposable != null) {
                        mDisposables.add(disposable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    public void setListener(ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    private ClickListener mClickListener;

    public interface ClickListener {
        void okListener(NewOrderBean.DataBean.ListBean bean, int position);

        void noListener(NewOrderBean.DataBean.ListBean bean, int position);

        void detail(NewOrderBean.DataBean.ListBean bean, int position);
    }

    @SuppressLint("CheckResult")
    public Disposable downTime(String time, TextView textView) {
        try {
            long preTime = DateUtils.dateToStamp(time);
            long aftTime = preTime + 1000 * 60 * 10;

            Disposable disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .map(aLong -> {
                        long now = new Date().getTime();
                        long fixTime = aftTime - now;
                        long second = fixTime / 1000;
                        String returnTime = "";
                        if (second > 0) {
                            long m = second / 60;
                            long s = second % 60;
                            String mStr = m < 10 ? "0" + m : m + "";
                            String sStr = s < 10 ? "0" + s : s + "";
//                            System.out.println(String.format(Locale.CHINA,
//                                    "当前剩余时间：%s:%s", mStr, sStr));
                            returnTime = mStr + ":" + sStr;
                        } else {
//                            LogUtil.e("订单已截止");
                        }
                        return returnTime;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        textView.setText(String.format(Locale.CHINA, "接受预约%s", s));
                    });
            return disposable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
