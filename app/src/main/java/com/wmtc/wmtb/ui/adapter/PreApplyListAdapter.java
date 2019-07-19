package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.TixianMainBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/16.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PreApplyListAdapter extends BaseQuickAdapter<TixianMainBean.DataBean, BaseViewHolder> {
    public PreApplyListAdapter(List<TixianMainBean.DataBean> data) {
        super(R.layout.item_tixian_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TixianMainBean.DataBean item) {
        String projectAllPrice = item.projectAllPrice;
        if (StringUtils.isBlank(projectAllPrice)) {
            projectAllPrice = "0";
        }
        double v = Integer.parseInt(projectAllPrice) * 0.01;
        helper.setText(R.id.tv_name, item.projectName)
                .setText(R.id.tv_time, item.createTime)
                .addOnClickListener(R.id.im_check)
                .setText(R.id.tv_money, String.format(Locale.CHINA, "￥%.2f", v));
        ImageView ivCheck = helper.itemView.findViewById(R.id.im_check);
        ivCheck.setSelected(item.isSel);
    }
}
