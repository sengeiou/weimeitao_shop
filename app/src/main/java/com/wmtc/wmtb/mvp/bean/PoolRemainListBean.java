package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/4/26.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class PoolRemainListBean extends BaseBean {

    /**
     * data : {"total":2,"size":20,"current":1,"records":[{"orderId":"317344816255991808","orderNo":"wmt317344816255991808","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectPriceEndPaidTime":"2019-04-16 18:09:37","userName":"蔡超测试（勿动）","bangDingFee":"0"},{"orderId":"319126070399860736","orderNo":"wmt319126070399860736","projectName":"灵芝焕肤","projectPriceEndPaidTime":"2019-03-23 15:37:42","userName":"蔡超测试（勿动）","bangDingFee":"100"}],"pages":1}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * total : 2
         * size : 20
         * current : 1
         * records : [{"orderId":"317344816255991808","orderNo":"wmt317344816255991808","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectPriceEndPaidTime":"2019-04-16 18:09:37","userName":"蔡超测试（勿动）","bangDingFee":"0"},{"orderId":"319126070399860736","orderNo":"wmt319126070399860736","projectName":"灵芝焕肤","projectPriceEndPaidTime":"2019-03-23 15:37:42","userName":"蔡超测试（勿动）","bangDingFee":"100"}]
         * pages : 1
         */

        public int total;
        public int size;
        public int current;
        public int pages;
        public List<RecordsBean> records;

        public static class RecordsBean {
            /**
             * orderId : 317344816255991808
             * orderNo : wmt317344816255991808
             * projectName : 1黄金焕肤源自韩国皮肤管理界的皮肤深层清
             * projectPriceEndPaidTime : 2019-04-16 18:09:37
             * userName : 蔡超测试（勿动）
             * bangDingFee : 0
             */

            public String orderId;
            public String orderNo;
            public String projectName;
            public String projectPriceEndPaidTime;
            public String userName;
            public String bangDingFee;
        }
    }
}
