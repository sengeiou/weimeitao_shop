package com.wmtc.wmtb.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.MsgListBean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/3/30.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class NoticeMsgAdapter extends BaseQuickAdapter<MsgListBean.DataBean, BaseViewHolder> {

    public NoticeMsgAdapter(List<MsgListBean.DataBean> data) {
        super(R.layout.adapter_msg_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgListBean.DataBean item) {

        String createTime = item.createTime;
        if (createTime != null && createTime.contains(" ")) {
            createTime = createTime.split(" ")[0];
        }
        helper.setText(R.id.tvTitle, StringUtils.init().fixNullStr(item.messageTitle))
                .setText(R.id.tvSubTitle, StringUtils.init().fixNullStr(item.messageContent))
                .setText(R.id.tvTime, StringUtils.init().fixNullStr(createTime));
    }
}
