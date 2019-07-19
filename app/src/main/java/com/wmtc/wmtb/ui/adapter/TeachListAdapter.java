package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.TeachBean;

import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/27.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TeachListAdapter extends BaseQuickAdapter<TeachBean.DataBean, BaseViewHolder> {
    public TeachListAdapter(List<TeachBean.DataBean> data) {
        super(R.layout.adapter_teachlist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeachBean.DataBean item) {
        int status = item.status;
        TextView tvStatus = helper.itemView.findViewById(R.id.tvStatus);
        helper.setText(R.id.tvName, StringUtils.init().fixNullStr(item.technicianName))
                .setText(R.id.tvTitle, StringUtils.init().fixNullStr(item.title))
                .setText(R.id.tvGoodAt, StringUtils.init().fixNullStr(item.goodAt))
                .addOnClickListener(R.id.tvEdit)
                .addOnClickListener(R.id.tvDel)
                .setText(R.id.tvStatus, getStatus(status, tvStatus))
                .addOnClickListener(R.id.tvWorkTime);
        ImageView ivAvatar = helper.itemView.findViewById(R.id.ivAvatar);
        Glide.with(mContext).load(WmtApplication.url_host + item.avatar).apply(GlideUtils.init().options()).into(ivAvatar);
    }

    private String getStatus(int status, TextView tvStatus) {
        if (status == 1) {
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.colorBlackGold));
            return "审核通过";
        } else if (status == 4) {
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.redBorder));
            return "审核失败";
        } else if (status == 3) {
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            return "审核中";
        }
        return "";
    }
}
