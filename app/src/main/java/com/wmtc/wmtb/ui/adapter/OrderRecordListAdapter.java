package com.wmtc.wmtb.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.utils.StringUtils;

import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_0;
import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_1;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OrderRecordListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public OrderRecordListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_time);
        addItemType(TYPE_LEVEL_1, R.layout.item_shoukuan);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                ShoukuanBean.DataBean headerBean = (ShoukuanBean.DataBean) item;
                helper.setText(R.id.tv_time, StringUtils.init().fixNullStr(headerBean.date))
                        .setText(R.id.tv_num, StringUtils.init().fixNullStr(headerBean.num))
                        .setVisible(R.id.ll_msg, headerBean.isAdd)
                        .setVisible(R.id.ll_date, !headerBean.isAdd)
                        .setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(headerBean.totalAmount, "￥"));
                break;
            case TYPE_LEVEL_1:
                ShoukuanBean.DataBean.ListBean itemBean = (ShoukuanBean.DataBean.ListBean) item;
                helper.setText(R.id.tv_name, StringUtils.init().fixNullStr(itemBean.projectName))
                        .addOnClickListener(R.id.rlItem)
                        .setText(R.id.tv_time, StringUtils.init().fixNullStr(itemBean.projectPriceEndPaidTime))
                        .setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(itemBean.projectAllPrice, "￥"));
                break;
        }
    }
}
