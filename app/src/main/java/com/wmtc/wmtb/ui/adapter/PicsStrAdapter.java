package com.wmtc.wmtb.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.WmtApplication;
import com.wmtc.wmtb.mvp.bean.ProjectDetialBean;

import java.io.File;
import java.util.ArrayList;

import top.jplayer.baseprolibrary.glide.GlideUtils;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PicsStrAdapter extends BaseQuickAdapter<ProjectDetialBean.DataBean.CommonPicBean, BaseViewHolder> {

    public PicsStrAdapter(ArrayList<ProjectDetialBean.DataBean.CommonPicBean> data) {
        super(R.layout.adapter_pics, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectDetialBean.DataBean.CommonPicBean item) {
        ImageView ivItemSrc = helper.itemView.findViewById(R.id.ivItemSrc);
        helper.addOnClickListener(R.id.ivDel);
        Glide.with(mContext).load(WmtApplication.url_host + item.url).apply(GlideUtils.init().options(R.drawable.placeholder)).into(ivItemSrc);
    }
}
