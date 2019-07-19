package com.wmtc.wmtb.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.OrderStateBean;
import com.wmtc.wmtb.ui.fragment.AppointDetailFragment;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/29.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class AppointListAdapter extends BaseQuickAdapter<OrderStateBean.DataBean.ListBean, BaseViewHolder> {
    private Disposable disposable;
    private AppointDetailFragment fragment;

    public AppointListAdapter(List<OrderStateBean.DataBean.ListBean> data, AppointDetailFragment fragment) {
        super(R.layout.item_order_state, data);
        this.fragment = fragment;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStateBean.DataBean.ListBean item) {
        ImageView im_photo = helper.itemView.findViewById(R.id.im_photo);
        TextView tv_agree = helper.itemView.findViewById(R.id.tv_agree);
        Glide.with(mContext)
                .load(WmtApplication.url_host + item.proPic)
                .apply(GlideUtils.init().options(R.drawable.wmt_default))
                .into(im_photo);
        helper.setText(R.id.tv_name, StringUtils.init().fixNullStr(item.projectName))
                .setText(R.id.tv_time, StringUtils.init().fixNullStr(item.arrivalTime))
                .setText(R.id.tv_teacher, StringUtils.init().fixNullStr(item.technicianName));
        Disposable disposable = downTime(item.projectPriceFirstPaidTime, tv_agree, helper.getAdapterPosition());
        if (disposable != null) {
            fragment.mObjects.add(disposable);
        }
        helper.setVisible(R.id.rl_new, "3".equals(fragment.type))
                .setVisible(R.id.rl_wait, "5".equals(fragment.type))
                .setVisible(R.id.rl_center, "CANCEL".equals(fragment.type))
                .setVisible(R.id.rl_after_sale, false);
        if ("3".equals(fragment.type)) {
            helper.addOnClickListener(R.id.tv_agree)
                    .addOnClickListener(R.id.tv_repulse);
        } else if ("5".equals(fragment.type)) {
            helper.setText(R.id.tv_time_center, "用户超时取消")
                    .setText(R.id.tv_change, "更改尾款")
                    .addOnClickListener(R.id.tv_change)
                    .addOnClickListener(R.id.tv_time_center);
        } else if ("CANCEL".equals(fragment.type)) {
            helper.setText(R.id.tv_msg, StringUtils.init().fixNullStr(item.orderStatusName))
                    .setTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.redBorder));
        }
    }

    @SuppressLint("CheckResult")
    private Disposable downTime(String time, TextView textView, int position) {
        try {
            long preTime = DateUtils.dateToStamp(time);
            long aftTime = preTime + 1000 * 60 * 10;

            textView.setTag(position);
            disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
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
                  /*          System.out.println(String.format(Locale.CHINA,
                                    "当前剩余时间：%s:%s", mStr, sStr));*/
                            returnTime = mStr + ":" + sStr;
                        } else {
//                            LogUtil.e("订单已截止");

                        }
                        return returnTime;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        if ((int) textView.getTag() == position) {
                            textView.setText(String.format(Locale.CHINA, "接受预约%s", s));
                        }
                    });
            return disposable;
        } catch (Exception e) {
            e.printStackTrace();
            return disposable;
        }
    }


}
