package com.wmtc.wmtb.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.PoolRemainListBean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PlatformRemainAdapter extends BaseQuickAdapter<PoolRemainListBean.DataBean.RecordsBean, BaseViewHolder> {

    public PlatformRemainAdapter(List<PoolRemainListBean.DataBean.RecordsBean> data) {
        super(R.layout.adapter_platformlist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoolRemainListBean.DataBean.RecordsBean item) {
        String titleOrderType = StringUtils.init().fixNullStr(item.userName);
        boolean isSub = titleOrderType.length() > 1;
        if (isSub) {
            titleOrderType = "*" + titleOrderType.substring(1);
        } else {
            titleOrderType = "*" + titleOrderType;
        }
        helper.setText(R.id.tv_name, titleOrderType)
                .setText(R.id.tvStatus, "")
                .setText(R.id.tv_money, StringUtils.init().fixNullStrMoney(item.bangDingFee, "+￥"))
                .setText(R.id.tv_time, StringUtils.init().fixNullStr(item.projectPriceEndPaidTime));
    }
}
