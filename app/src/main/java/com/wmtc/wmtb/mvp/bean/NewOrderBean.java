package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class NewOrderBean extends BaseBean {

    /**
     * data : {"count":2,"list":[{"id":"317342116193763328","orderNo":"wmt317342116193763328","shopName":"刘杰的小铺","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectId":"111624582166708224","arrivalTime":"2019-03-27 08:20:00","afterArrivalTime":"2019-03-27 07:50:00","projectPriceFirstPaidTime":"2019-03-26 16:44:57","createTime":"2019-03-26 16:44:16","projectPriceFirst":"1","projectPriceEnd":"80","projectPriceEndPaidTime":null,"proPic":null,"projectAllPrice":"100","orderStatusName":"商家确认中","orderStatus":"3","commentStatus":"未评价","technicianName":"刘姐姐"},{"id":"317341543612547072","orderNo":"wmt317341543612547072","shopName":"刘杰的小铺","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectId":"111624582166708224","arrivalTime":"2019-03-27 07:40:00","afterArrivalTime":"2019-03-27 07:10:00","projectPriceFirstPaidTime":null,"createTime":"2019-03-26 16:42:00","projectPriceFirst":"1","projectPriceEnd":"80","projectPriceEndPaidTime":null,"proPic":null,"projectAllPrice":"100","orderStatusName":"商家确认中","orderStatus":"3","commentStatus":"未评价","technicianName":"刘姐姐"}]}
     * curson : null
     * erros : null
     */

    public DataBean data;

    public static class DataBean {
        /**
         * count : 2
         * list : [{"id":"317342116193763328","orderNo":"wmt317342116193763328","shopName":"刘杰的小铺","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectId":"111624582166708224","arrivalTime":"2019-03-27 08:20:00","afterArrivalTime":"2019-03-27 07:50:00","projectPriceFirstPaidTime":"2019-03-26 16:44:57","createTime":"2019-03-26 16:44:16","projectPriceFirst":"1","projectPriceEnd":"80","projectPriceEndPaidTime":null,"proPic":null,"projectAllPrice":"100","orderStatusName":"商家确认中","orderStatus":"3","commentStatus":"未评价","technicianName":"刘姐姐"},{"id":"317341543612547072","orderNo":"wmt317341543612547072","shopName":"刘杰的小铺","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectId":"111624582166708224","arrivalTime":"2019-03-27 07:40:00","afterArrivalTime":"2019-03-27 07:10:00","projectPriceFirstPaidTime":null,"createTime":"2019-03-26 16:42:00","projectPriceFirst":"1","projectPriceEnd":"80","projectPriceEndPaidTime":null,"proPic":null,"projectAllPrice":"100","orderStatusName":"商家确认中","orderStatus":"3","commentStatus":"未评价","technicianName":"刘姐姐"}]
         */

        public int count;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 317342116193763328
             * orderNo : wmt317342116193763328
             * shopName : 刘杰的小铺
             * projectName : 1黄金焕肤源自韩国皮肤管理界的皮肤深层清
             * projectId : 111624582166708224
             * arrivalTime : 2019-03-27 08:20:00
             * afterArrivalTime : 2019-03-27 07:50:00
             * projectPriceFirstPaidTime : 2019-03-26 16:44:57
             * createTime : 2019-03-26 16:44:16
             * projectPriceFirst : 1
             * projectPriceEnd : 80
             * projectPriceEndPaidTime : null
             * proPic : null
             * projectAllPrice : 100
             * orderStatusName : 商家确认中
             * orderStatus : 3
             * commentStatus : 未评价
             * technicianName : 刘姐姐
             */
            public int type;
            public String id;
            public String orderNo;
            public String shopName;
            public String projectName;
            public String projectId;
            public String arrivalTime;
            public String afterArrivalTime;
            public String projectPriceFirstPaidTime;
            public String projectPriceFirst;
            public String projectPriceEnd;
            public String projectAllPrice;
            public String orderStatusName;
            public String orderStatus;
            public String commentStatus;
            public String technicianName;
            public String pid;//407 空项目
            public String changeProjectEndStatus;
            public static final int INT_NOTICE = 0;
            public static final int INT_OTHER = 1;

            public ListBean(int type) {
                this.type = type;
            }

            public int getType() {
                if (type == INT_NOTICE) {
                    return INT_NOTICE;
                } else {
                    return INT_OTHER; //不受控制的类型《过滤》

                }
            }
        }
    }
}
