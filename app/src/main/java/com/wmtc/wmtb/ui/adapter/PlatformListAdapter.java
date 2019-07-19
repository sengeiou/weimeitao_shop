package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.PoolListBean;
import com.wmtc.wmtb.ui.activity.PlatformActivity;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PlatformListAdapter extends BaseQuickAdapter<PoolListBean.DataBean.RecordsBean, BaseViewHolder> {
    PlatformActivity mActivity;

    public PlatformListAdapter(List<PoolListBean.DataBean.RecordsBean> data, PlatformActivity activity) {
        super(R.layout.adapter_platformlist, data);
        this.mActivity = activity;

    }

    @Override
    protected void convert(BaseViewHolder helper, PoolListBean.DataBean.RecordsBean item) {
        String titleOrderType = StringUtils.init().fixNullStr(item.titleName);
        boolean isSub = titleOrderType.length() > 1;
        if ("1".equals(mActivity.status)) {
            if (!"1".equals(item.orderType)) {
                if (isSub) {
                    titleOrderType = "*" + titleOrderType.substring(1);
                } else {
                    titleOrderType = "*" + titleOrderType;
                }
                titleOrderType += "使用了线下消费红包";
            }
            helper.setText(R.id.tvStatus, "")
                    .setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(item.totalAmount, "+￥"))
                    .setTextColor(R.id.tv_money, mActivity.getResources().getColor(R.color.money_color));
        } else {

            if (StringUtils.isNotBlank(item.couponConfigType)) {
                if (isSub) {
                    titleOrderType = "*" + titleOrderType.substring(1);
                } else {
                    titleOrderType = "*" + titleOrderType;
                }
                if ("1".equals(item.couponConfigType)) {
                    titleOrderType += "领取了" + StringUtils.init().fixNullStr(item.couponName);
                } else if ("2".equals(item.couponConfigType)) {
                    String type;
                    if (("1".equals(item.orderType))) {
                        type = "线上";
                    } else {
                        type = "线下";
                    }
                    titleOrderType += "领取了" + type + "消费红包";
                }
            }

            if ("3".equals(mActivity.status)) {
                helper.setText(R.id.tvStatus, "已过期");
                helper.setTextColor(R.id.tvStatus, mActivity.getResources().getColor(R.color.colorWmDivider));
            } else {
                if ("".equals(item.couponConfigType)) {
                    if ("2".equals(item.orderType)) {
                        titleOrderType += "使用了线下红包";
                    } else {
                        titleOrderType += "";
                    }
                    helper.setText(R.id.tvStatus, "订单保护期");
                } else {
                    helper.setText(R.id.tvStatus, "");
                }
                helper.setTextColor(R.id.tvStatus, mActivity.getResources().getColor(R.color.chocolate));
            }
            helper.setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(item.totalAmount, "￥"))
                    .setTextColor(R.id.tv_money, mActivity.getResources().getColor(R.color.colorBlackGold));
        }

        helper.setText(R.id.tv_name, titleOrderType)
                .setText(R.id.tv_time, StringUtils.init().fixNullStr(item.createTime));
    }
}
