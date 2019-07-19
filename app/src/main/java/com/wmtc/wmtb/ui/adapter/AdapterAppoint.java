package com.wmtc.wmtb.ui.adapter;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.NewOrderBean;
import com.wmtc.wmtb.util.TimeUtil;
import com.wmtc.wmtb.util.TimerDown;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
public class AdapterAppoint extends BaseViewPagerViewAdapter<NewOrderBean.DataBean.ListBean> {


    public AdapterAppoint(List<NewOrderBean.DataBean.ListBean> list) {
        super(list);
        addItemType(NewOrderBean.DataBean.ListBean.INT_NOTICE, R.layout.dapter_appoint_order);
        addItemType(NewOrderBean.DataBean.ListBean.INT_OTHER, R.layout.dapter_no_order);
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
        switch (getItemType(position)) {
            case NewOrderBean.DataBean.ListBean.INT_NOTICE:
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
                try {
                    if (bean.arrivalTime != null) {
                        long aftTime = DateUtils.dateToStamp(bean.afterArrivalTime);
                        long arrTime = DateUtils.dateToStamp(bean.arrivalTime);
                        long nowTime = new Date().getTime();
                        tvOKSel.setEnabled(arrTime < nowTime);//时间过了到店时间可以更改尾款
                        tvNoSel.setEnabled(aftTime < nowTime);//时间过了最后到店时间，可以取消
                        LogUtil.e(aftTime + "------" + arrTime + "-----" + nowTime);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    public void setListener(AdapterNewOrder.ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    private AdapterNewOrder.ClickListener mClickListener;

    public interface ClickListener {
        void okListener(NewOrderBean.DataBean.ListBean bean, int position);

        void noListener(NewOrderBean.DataBean.ListBean bean, int position);

        void detail(NewOrderBean.DataBean.ListBean bean, int position);
    }

}
