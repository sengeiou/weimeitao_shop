package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/4/19.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OnLineDetailBean extends BaseBean {

    /**
     * data : {"totalAmount":"0","yuyueFirstAmount":"1","projectPriceEnd":"80","actualPaid":"81","platformShouyi":"0","platformSubsidyShopFee":"0","platformSubsidyShopAmount":"0","createTime":"2019-03-31 02:26:21","projectName":"三千痴缠~皮肤护理","orderId":"319058384005365760","orderNo":"wmt319058384005365760"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * totalAmount : 0
         * yuyueFirstAmount : 1
         * projectPriceEnd : 80
         * actualPaid : 81
         * platformShouyi : 0
         * platformSubsidyShopFee : 0
         * platformSubsidyShopAmount : 0
         * createTime : 2019-03-31 02:26:21
         * projectName : 三千痴缠~皮肤护理
         * orderId : 319058384005365760
         * orderNo : wmt319058384005365760
         */

        public String totalAmount;//titleMoney
        public String yuyueFirstAmount;//预约金
        public String originalProjectPriceEnd;//应付尾款
        public String projectPriceEnd;
        public String couponType;
        public String couponName;
        public String couponPrice;
        public String actualPaid;
        public String platformShouyi;
        public String platformSubsidyShopAmount;//平台总金额
        public String createTime;
        public String projectName;
        public String orderId;
        public String orderNo;
        public String bangDingTixianStatus;
        public String bangdingFee;//绑定金
        public String platformSubsidyShopFee;//预约金返回
        public String hongbaoName;//红包名称
        public String hongbaoPrice;//红包价格
    }
}
