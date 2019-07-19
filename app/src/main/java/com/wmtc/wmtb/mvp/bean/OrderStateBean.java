package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

public class OrderStateBean extends BaseBean {


    /**
     * data : {"count":1,"list":[{"id":"319130260224344064","orderNo":"wmt319130260224344064","shopName":"佳丽三千","projectName":"哦三只松鼠","projectId":"319122722804203520","shopId":"319120000180813824","arrivalTime":"2019-03-31 08:20:00","afterArrivalTime":"2019-03-31 08:50:00","beforeArrivalTime":"2019-03-31 07:50:00","projectPriceFirstPaidTime":"2019-03-31 15:59:02","createTime":"2019-03-31 15:09:43","projectPriceFirst":"20","projectPriceEnd":"80","projectPriceEndPaidTime":null,"proPic":"banner/319122722804203520/bannera2e1f606-1cf7-495a-af5c-4ec72f854eeb.png","projectAllPrice":"100","orderStatusName":"商家确认中","orderStatus":"3","commentStatus":"未评价","technicianName":"我是小学生"}]}
     * curson : null
     * erros : null
     */

    public DataBean data;
    public String curson;
    public String erros;

    public static class DataBean {
        /**
         * count : 1
         * list : [{"id":"319130260224344064","orderNo":"wmt319130260224344064","shopName":"佳丽三千","projectName":"哦三只松鼠","projectId":"319122722804203520","shopId":"319120000180813824","arrivalTime":"2019-03-31 08:20:00","afterArrivalTime":"2019-03-31 08:50:00","beforeArrivalTime":"2019-03-31 07:50:00","projectPriceFirstPaidTime":"2019-03-31 15:59:02","createTime":"2019-03-31 15:09:43","projectPriceFirst":"20","projectPriceEnd":"80","projectPriceEndPaidTime":null,"proPic":"banner/319122722804203520/bannera2e1f606-1cf7-495a-af5c-4ec72f854eeb.png","projectAllPrice":"100","orderStatusName":"商家确认中","orderStatus":"3","commentStatus":"未评价","technicianName":"我是小学生"}]
         */

        public String count;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 319130260224344064
             * orderNo : wmt319130260224344064
             * shopName : 佳丽三千
             * projectName : 哦三只松鼠
             * projectId : 319122722804203520
             * shopId : 319120000180813824
             * arrivalTime : 2019-03-31 08:20:00
             * afterArrivalTime : 2019-03-31 08:50:00
             * beforeArrivalTime : 2019-03-31 07:50:00
             * projectPriceFirstPaidTime : 2019-03-31 15:59:02
             * createTime : 2019-03-31 15:09:43
             * projectPriceFirst : 20
             * projectPriceEnd : 80
             * projectPriceEndPaidTime : null
             * proPic : banner/319122722804203520/bannera2e1f606-1cf7-495a-af5c-4ec72f854eeb.png
             * projectAllPrice : 100
             * orderStatusName : 商家确认中
             * orderStatus : 3
             * commentStatus : 未评价
             * technicianName : 我是小学生
             */

            public String id;
            public String orderNo;
            public String shopName;
            public String projectName;
            public String projectId;
            public String shopId;
            public String orderDesc;
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
            public String pid;
            public String changeProjectEndStatus;
        }
    }
}
