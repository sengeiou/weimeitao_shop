package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.CardListBean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CardListAdapter extends BaseQuickAdapter<CardListBean.DataBean, BaseViewHolder> {
    public CardListAdapter(List<CardListBean.DataBean> data) {
        super(R.layout.adapter_card_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardListBean.DataBean item) {
        String cardNo = item.cardNo;
        if (cardNo.length() > 4) {
            String start = cardNo.substring(0, 4);
            String end = cardNo.substring(cardNo.length() - 4);
            cardNo = start + "********" + end;
        }
        helper.setText(R.id.tv_bank_num, cardNo)
                .setText(R.id.tv_bank_name, StringUtils.init().fixNullStr(item.cardBank));
    }
}
