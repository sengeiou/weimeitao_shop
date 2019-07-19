package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/4/18.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class BindDetailBean extends BaseBean {

    /**
     * data : {"orderNo":"wmt317341449412673536","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectPriceEndPaidTime":"2019-04-18 19:50:26","userName":"斌斌大魔王","bangDingTixianStatus":"1","bingDingType":"1","bangDingFee":"1"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * orderNo : wmt317341449412673536
         * projectName : 1黄金焕肤源自韩国皮肤管理界的皮肤深层清
         * projectPriceEndPaidTime : 2019-04-18 19:50:26
         * userName : 斌斌大魔王
         * bangDingTixianStatus : 1
         * bingDingType : 1
         * bangDingFee : 1
         */

        public String orderNo;
        public String orderId;
        public String projectName;
        public String projectPriceEndPaidTime;
        public String userName;
        public String bangDingTixianStatus;
        public String bingDingType;
        public String bangDingFee;
    }
}
