package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/4/18.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OffLineDetailBean extends BaseBean {

    /**
     * data : {"offlineOrderId":null,"payInfoId":"324573813100511232","zfNo":"2019041522001417141029524474","payType":"zfb","totalAmount":"3","actualAmount":"2","bonusPoolsSubsidyShopAmount":"1","paidTime":"2019-04-18 06:49:24"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * offlineOrderId : null
         * payInfoId : 324573813100511232
         * zfNo : 2019041522001417141029524474
         * payType : zfb
         * totalAmount : 3
         * actualAmount : 2
         * bonusPoolsSubsidyShopAmount : 1
         * paidTime : 2019-04-18 06:49:24
         */

        public Object offlineOrderId;
        public String payInfoId;
        public String bangDingTixianStatus;
        public String zfNo;
        public String payType;
        public String totalAmount;
        public String actualAmount;
        public String bonusPoolsSubsidyShopAmount;
        public String paidTime;
    }
}
