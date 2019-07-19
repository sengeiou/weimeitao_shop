package com.wmtc.wmtb.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.TixianBean;

import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.utils.StringUtils;

import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_0;
import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_1;

/**
 * Created by Obl on 2019/4/1.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ApplyListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ApplyListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_month);
        addItemType(TYPE_LEVEL_1, R.layout.item_date);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        int adapterPosition = helper.getLayoutPosition();
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                TixianBean.DataBean dataBean = (TixianBean.DataBean) item;
                helper.itemView.setOnClickListener(v -> {
                    if (dataBean.isExpanded()) {
                        collapse(helper.getLayoutPosition());
                    } else {
                        expand(helper.getLayoutPosition());
                    }
                });
                helper.setText(R.id.tv_month, String.format(Locale.CHINA, "%s月", StringUtils.init().fixNullStr(dataBean.month)))
                        .setText(R.id.tv_all_money, String.format(Locale.CHINA, "提现￥%s",
                                StringUtils.init().fixNullStr(dataBean.totalAmount)));
                break;
            case TYPE_LEVEL_1:
                TixianBean.DataBean.ListBean listBean = (TixianBean.DataBean.ListBean) item;
                String cardNo = StringUtils.init().fixNullStr(listBean.cardNo);
                if (cardNo.length() > 4) {
                    cardNo = cardNo.substring(cardNo.length() - 4);
                }
                helper.setText(R.id.tv_time, String.format(Locale.CHINA, "%s",
                        StringUtils.init().fixNullStr(listBean.applyTime)))
                        .setText(R.id.name, String.format(Locale.CHINA, "%s",
                                StringUtils.init().fixNullStr(listBean.bankName)))
                        .setText(R.id.tv_money, String.format(Locale.CHINA, "%s",
                                StringUtils.init().fixNullStr(listBean.totalAmount)))
                        .setText(R.id.tv_bank_num, String.format(Locale.CHINA, "尾号%s储蓄卡",
                                StringUtils.init().fixNullStr(cardNo)))
                        .setText(R.id.tv_state, String.format(Locale.CHINA, "%s",
                                StringUtils.init().fixNullStr(listBean.recordStatusName)));
                break;
        }
    }
}