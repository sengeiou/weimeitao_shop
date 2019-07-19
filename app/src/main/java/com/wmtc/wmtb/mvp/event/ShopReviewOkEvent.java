package com.wmtc.wmtb.mvp.event;

import com.wmtc.wmtb.mvp.bean.ShopsBean;

/**
 * Created by Obl on 2019/4/29.
 * com.wmtc.wmtb.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopReviewOkEvent {
    public ShopsBean.DataBean shop;

    public ShopReviewOkEvent(ShopsBean.DataBean shop) {
        this.shop = shop;
    }
}
