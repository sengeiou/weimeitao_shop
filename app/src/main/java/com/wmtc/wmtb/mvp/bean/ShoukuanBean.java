package com.wmtc.wmtb.mvp.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_0;
import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_1;

public class ShoukuanBean extends BaseBean {

    /**
     * data : [{"date":"2019-03-20","totalAmount":"1.00","num":1,"list":[{"orderNo":"wmt317678036348567552","projectPriceEndPaidTime":"15:15:31","projectAllPrice":"1.00","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清"},{"orderNo":"wmt317678036348567552","projectPriceEndPaidTime":"15:15:31","projectAllPrice":"1.00","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清"}]},{"date":"2019-03-27","totalAmount":"1.00","num":1,"list":[{"orderNo":"wmt317731999592742912","projectPriceEndPaidTime":"18:37:00","projectAllPrice":"1.00","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清"}]}]
     * curson : null
     * erros : null
     */

    public Object curson;
    public Object erros;
    public List<DataBean> data;

    public static class DataBean extends AbstractExpandableItem<ShoukuanBean.DataBean.ListBean> implements MultiItemEntity {
        /**
         * date : 2019-03-20
         * totalAmount : 1.00
         * num : 1
         * list : [{"orderNo":"wmt317678036348567552","projectPriceEndPaidTime":"15:15:31","projectAllPrice":"1.00","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清"},{"orderNo":"wmt317678036348567552","projectPriceEndPaidTime":"15:15:31","projectAllPrice":"1.00","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清"}]
         */

        public String date;
        public String totalAmount;
        public boolean isAdd = false;
        public String num;
        public List<ListBean> list;

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getItemType() {
            return TYPE_LEVEL_0;
        }

        public static class ListBean implements MultiItemEntity {
            /**
             * orderNo : wmt317678036348567552
             * projectPriceEndPaidTime : 15:15:31
             * projectAllPrice : 1.00
             * projectName : 1黄金焕肤源自韩国皮肤管理界的皮肤深层清
             */

            public String orderNo;
            public String orderId;
            public String projectPriceEndPaidTime;
            public String projectAllPrice;
            public String projectName;
            public String offlineOrderId;

            @Override
            public int getItemType() {
                return TYPE_LEVEL_1;
            }
        }
    }
}
