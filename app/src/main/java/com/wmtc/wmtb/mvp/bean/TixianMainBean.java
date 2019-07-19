package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

public class TixianMainBean extends BaseBean {

    /**
     * data : [{"id":"317678036348567552","orderNo":"wmt317678036348567552","shopName":"刘杰的小铺1","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectId":"111624582166708224","shopId":"111624582166708224","arrivalTime":"2019-03-27 15:00:00","afterArrivalTime":null,"beforeArrivalTime":null,"projectPriceFirstPaidTime":"2019-03-27 14:59:18","createTime":"2019-03-27 14:59:06","projectPriceFirst":"1","projectPriceEnd":"1","projectPriceEndPaidTime":"2019-03-20 15:15:31","proPic":null,"projectAllPrice":"100","orderStatusName":null,"orderStatus":"6","commentStatus":null,"technicianName":null}]
     * curson : null
     * erros : null
     */

    public String curson;
    public String erros;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 317678036348567552
         * orderNo : wmt317678036348567552
         * shopName : 刘杰的小铺1
         * projectName : 1黄金焕肤源自韩国皮肤管理界的皮肤深层清
         * projectId : 111624582166708224
         * shopId : 111624582166708224
         * arrivalTime : 2019-03-27 15:00:00
         * afterArrivalTime : null
         * beforeArrivalTime : null
         * projectPriceFirstPaidTime : 2019-03-27 14:59:18
         * createTime : 2019-03-27 14:59:06
         * projectPriceFirst : 1
         * projectPriceEnd : 1
         * projectPriceEndPaidTime : 2019-03-20 15:15:31
         * proPic : null
         * projectAllPrice : 100
         * orderStatusName : null
         * orderStatus : 6
         * commentStatus : null
         * technicianName : null
         */

        public String id;
        public String orderNo;
        public String orderId;
        public String offlineOrderId;
        public String shopName;
        public String projectName;
        public String projectId;
        public String shopId;
        public String arrivalTime;
        public String afterArrivalTime;
        public String beforeArrivalTime;
        public String projectPriceFirstPaidTime;
        public String createTime;
        public String projectPriceFirst;
        public String projectPriceEnd;
        public String projectPriceEndPaidTime;
        public String proPic;
        public String projectAllPrice;
        public String orderStatusName;
        public String orderStatus;
        public String commentStatus;
        public String technicianName;
        public boolean isSel = false;
    }
}
