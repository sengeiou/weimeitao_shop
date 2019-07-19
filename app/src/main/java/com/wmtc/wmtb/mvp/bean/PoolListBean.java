package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PoolListBean extends BaseBean {

    /**
     * data : {"total":8,"size":20,"current":1,"records":[{"titleName":"认个错","createTime":"2019-04-20 14:11:40.0","totalAmount":"104","couponConfigType":"","orderId":"326362118435635200","orderType":"1","couponName":""},{"titleName":"认个错","createTime":"2019-04-20 15:14:04.0","totalAmount":"105","couponConfigType":"","orderId":"326378589589078016","orderType":"1","couponName":""},{"titleName":"张圣海","createTime":"2019-04-24 11:36:17.0","totalAmount":"1655","couponConfigType":"2","orderId":"2","orderType":"红包1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""}],"pages":1}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * total : 8
         * size : 20
         * current : 1
         * records : [{"titleName":"认个错","createTime":"2019-04-20 14:11:40.0","totalAmount":"104","couponConfigType":"","orderId":"326362118435635200","orderType":"1","couponName":""},{"titleName":"认个错","createTime":"2019-04-20 15:14:04.0","totalAmount":"105","couponConfigType":"","orderId":"326378589589078016","orderType":"1","couponName":""},{"titleName":"张圣海","createTime":"2019-04-24 11:36:17.0","totalAmount":"1655","couponConfigType":"2","orderId":"2","orderType":"红包1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""},{"titleName":"袁鹏微信","createTime":"2019-04-19 16:24:40.0","totalAmount":"128","couponConfigType":"1","orderId":"1","orderType":"优惠券1","couponName":""}]
         * pages : 1
         */

        public int total;
        public int size;
        public int current;
        public int pages;
        public List<RecordsBean> records;

        public static class RecordsBean {
            /**
             * titleName : 认个错
             * createTime : 2019-04-20 14:11:40.0
             * totalAmount : 104
             * couponConfigType :
             * orderId : 326362118435635200
             * orderType : 1
             * couponName :
             */

            public String titleName;
            public String createTime;
            public String totalAmount;
            public String couponConfigType;
            public String orderId;
            public String orderType;
            public String couponName;
        }
    }
}
