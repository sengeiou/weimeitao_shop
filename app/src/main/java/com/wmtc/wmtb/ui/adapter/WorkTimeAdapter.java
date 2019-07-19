package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.StatusBean;

import java.util.ArrayList;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class WorkTimeAdapter extends BaseQuickAdapter<StatusBean, BaseViewHolder> {
    public WorkTimeAdapter(ArrayList<StatusBean> list) {
        super(R.layout.adapter_work_time, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusBean item) {
        helper.setText(R.id.tvTime, item.key);
        TextView tvTime = helper.itemView.findViewById(R.id.tvTime);
        tvTime.setSelected(item.isSel);
    }
}
