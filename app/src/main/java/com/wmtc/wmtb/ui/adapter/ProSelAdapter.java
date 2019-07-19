package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.ProjectSelListBean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProSelAdapter extends BaseQuickAdapter<ProjectSelListBean.DataBean.RecordsBean, BaseViewHolder> {
    public ProSelAdapter(List<ProjectSelListBean.DataBean.RecordsBean> data) {
        super(R.layout.adapter_pro_sel, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectSelListBean.DataBean.RecordsBean item) {
        helper.setText(R.id.tvTitle, StringUtils.init().fixNullStr(item.projectName))
        .setText(R.id.tvSubTitle, StringUtils.init().fixNullStr(item.projectFunction));
    }
}
