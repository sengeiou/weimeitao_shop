package com.wmtc.wmtb.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.BannerInfosBean;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;

import java.util.ArrayList;

import top.jplayer.baseprolibrary.glide.GlideUtils;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PicsStrBannerAdapter extends BaseQuickAdapter<BannerInfosBean.DataBean, BaseViewHolder> {

    public PicsStrBannerAdapter(ArrayList<BannerInfosBean.DataBean> data) {
        super(R.layout.adapter_pics, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BannerInfosBean.DataBean item) {
        ImageView ivItemSrc = helper.itemView.findViewById(R.id.ivItemSrc);
        helper.addOnClickListener(R.id.ivDel);
        Glide.with(mContext).load(item.url).apply(GlideUtils.init().options(R.drawable.placeholder)).into(ivItemSrc);
    }
}
