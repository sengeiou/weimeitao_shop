package com.wmtc.wmtb.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.ProListBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProListAdapter extends BaseQuickAdapter<ProListBean.DataBean, BaseViewHolder> {


    public ProListAdapter(List<ProListBean.DataBean> data) {
        super(R.layout.adapter_pro_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProListBean.DataBean item) {
        String projectPrice = item.projectPrice;

        int parseInt = Integer.parseInt(StringUtils.init().fixNullStr(projectPrice, "0"));
        helper.setText(R.id.tvTitle, StringUtils.init().fixNullStr(item.projectName))
                .setText(R.id.tvProTitle, String.format(Locale.CHINA, "预约项目:%s",
                        StringUtils.init().fixNullStr(item.projectTypeName)))
                .setText(R.id.tvProEffect, String.format(Locale.CHINA, "项目功效:%s",
                        StringUtils.init().fixNullStr(item.effect)))
                .setText(R.id.tvStatus, getStatusValue(item.status, helper))
                .setText(R.id.tvPrice, String.format(Locale.CHINA, "￥%.2f", parseInt * 0.01))
                .addOnClickListener(R.id.tvDel)
                .addOnClickListener(R.id.tvUp)
                .addOnClickListener(R.id.tvEdit)
                .addOnClickListener(R.id.tvDown);

    }

    //项目状态：未提交2、待审核3、审核通过4、审核不通过5、待上线6、已上线1、已下线7、删除0
    public String getStatusValue(String status, BaseViewHolder helper) {
        if ("1".equals(status)) {
            setVisiable(helper, false, true, true, true);
            helper.setTextColor(R.id.tvStatus, mContext.getResources().getColor(R.color.colorBlackGold));
            return "已上线";
        } else if ("2".equals(status)) {
            setVisiable(helper, false, false, true, true);
            helper.setTextColor(R.id.tvStatus, mContext.getResources().getColor(R.color.colorWmDivider));
            return "未提交";
        } else if ("3".equals(status)) {
            setVisiable(helper, false, false, true, false);
            helper.setTextColor(R.id.tvStatus, mContext.getResources().getColor(R.color.colorPrimary));
            return "审核中";
        } else if ("4".equals(status)) {
            setVisiable(helper, true, false, true, true);
            helper.setTextColor(R.id.tvStatus, mContext.getResources().getColor(R.color.colorBlackGold));
            return "审核通过";
        } else if ("5".equals(status)) {
            setVisiable(helper, false, false, true, true);
            helper.setTextColor(R.id.tvStatus, mContext.getResources().getColor(R.color.redBorder));
            return "审核失败";
        } else if ("6".equals(status)) {
            setVisiable(helper, true, false, true, true);
            helper.setTextColor(R.id.tvStatus, mContext.getResources().getColor(R.color.money_color));
            return "待上线";
        } else if ("7".equals(status)) {
            setVisiable(helper, true, false, true, true);
            helper.setTextColor(R.id.tvStatus, mContext.getResources().getColor(R.color.colorBlackGold));
            return "已下线";
        } else {
            return "删除";
        }
    }

    public void setVisiable(BaseViewHolder helper, boolean b1, boolean b2, boolean b3, boolean b4) {
        helper.setVisible(R.id.tvUp, b1);
        helper.setVisible(R.id.tvDown, b2);
        helper.setVisible(R.id.tvDel, b3);
        helper.setVisible(R.id.tvEdit, b4);
    }
}
