package com.wmtc.wmtb.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.DictListBean;

import java.util.List;
import java.util.Locale;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ReportAdapter extends BaseQuickAdapter<DictListBean.DataBean, BaseViewHolder> {

    public ReportAdapter(List<DictListBean.DataBean> data) {
        super(R.layout.adapter_topic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DictListBean.DataBean item) {
        helper.setText(R.id.tvTitle, String.format(Locale.CHINA, "%s", item.name));
        TextView tvSel = helper.itemView.findViewById(R.id.tvSel);
        tvSel.setSelected(item.isSel);
    }
}
