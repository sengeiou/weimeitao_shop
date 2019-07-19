package com.wmtc.wmtb.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.mvp.bean.BindListBean;
import com.wmtc.wmtb.mvp.bean.ShoukuanBean;

import java.util.List;

import top.jplayer.baseprolibrary.utils.StringUtils;

import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_0;
import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_1;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class BindRecordListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public BindRecordListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_bind_time);
        addItemType(TYPE_LEVEL_1, R.layout.item_bind);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                BindListBean.DataBean.ListBeanX headerBean = (BindListBean.DataBean.ListBeanX) item;
                helper.setText(R.id.tv_time, StringUtils.init().fixNullStr(headerBean.date))
                        .setText(R.id.tv_num, headerBean.num + "")
                        .setText(R.id.t1, "绑定人数")
                        .setText(R.id.t2, headerBean.date.contains("今天") ? "历史共计" : "")
                        .setText(R.id.tv3, "每一位顾客都是你未来的财富\n今天请继续加油")
                        .setVisible(R.id.ll_msg, headerBean.isAdd)
                        .setText(R.id.tv_money, StringUtils.init().fixNullStr(headerBean.totalNum));
                break;
            case TYPE_LEVEL_1:
                BindListBean.DataBean.ListBeanX.ListBean itemBean = (BindListBean.DataBean.ListBeanX.ListBean) item;
                String name = itemBean.projectName;
                if (name != null && name.length() > 1) {
                    name = "*" + name.substring(1);
                }
                helper.setText(R.id.tv_name, StringUtils.init().fixNullStr(name))
                        .setText(R.id.tv_time, StringUtils.init().fixNullStr(itemBean.projectPriceEndPaidTime));
                break;
        }
    }
}
