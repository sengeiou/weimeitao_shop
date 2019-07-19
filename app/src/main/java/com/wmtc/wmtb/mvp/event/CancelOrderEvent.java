package com.wmtc.wmtb.mvp.event;


import com.wmtc.wmtb.mvp.pojo.AfterSalePojo;
import com.wmtc.wmtb.mvp.pojo.RefundPojo;

/**
 * Created by Obl on 2019/6/26.
 * com.wmtc.wmt.appoint.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CancelOrderEvent {
    public AfterSalePojo mPojo;
    public String type;

    public CancelOrderEvent(AfterSalePojo pojo, String type) {
        this.mPojo = pojo;
        this.type = type;
    }
}
