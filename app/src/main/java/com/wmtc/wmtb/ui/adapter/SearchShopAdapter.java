package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;

import java.util.List;

/**
 * Created by Obl on 2019/3/23.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SearchShopAdapter extends BaseQuickAdapter<Tip, BaseViewHolder> {


    public SearchShopAdapter(List<Tip> data) {
        super(R.layout.adapter_search_shop, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {
        helper.setText(R.id.tvTitle, item.getName())
                .setText(R.id.tvLocal, item.getAddress())
                .addOnClickListener(R.id.tvSelect);
    }
}
