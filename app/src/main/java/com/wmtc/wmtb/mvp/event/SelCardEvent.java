package com.wmtc.wmtb.mvp.event;

import com.wmtc.wmtb.mvp.bean.CardListBean;

/**
 * Created by Obl on 2019/4/17.
 * com.wmtc.wmtb.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SelCardEvent {
    public CardListBean.DataBean dataBean;

    public SelCardEvent(CardListBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }
}
