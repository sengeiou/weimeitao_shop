package com.wmtc.wmtb.mvp.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

public class TixianBean extends BaseBean {

    /**
     * data : [{"totalAmount":1,"month":3,"list":[{"tixian_id":"316159513650528256","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:02","totalAmount":"1.00","recordStatus":"1","recordStatusName":"已打款"},{"tixian_id":"316159747692691456","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:58","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"},{"tixian_id":"316159752079933440","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:59","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"},{"tixian_id":"316159756039356416","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:26:00","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"}]},{"totalAmount":4,"month":3,"list":[{"tixian_id":"316159513650528256","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:02","totalAmount":"1.00","recordStatus":"1","recordStatusName":"已打款"},{"tixian_id":"316159747692691456","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:58","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"},{"tixian_id":"316159752079933440","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:59","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"},{"tixian_id":"316159756039356416","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:26:00","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"}]}]
     * curson : null
     * erros : null
     */

    public Object curson;
    public Object erros;
    public List<DataBean> data;
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public static class DataBean extends AbstractExpandableItem<DataBean.ListBean> implements MultiItemEntity {
        /**
         * totalAmount : 1.0
         * month : 3
         * list : [{"tixian_id":"316159513650528256","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:02","totalAmount":"1.00","recordStatus":"1","recordStatusName":"已打款"},{"tixian_id":"316159747692691456","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:58","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"},{"tixian_id":"316159752079933440","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:25:59","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"},{"tixian_id":"316159756039356416","bankName":"中国银行","cardNo":"000000040396","applyTime":"2019-03-23 10:26:00","totalAmount":"1.00","recordStatus":"0","recordStatusName":"提现中"}]
         */

        public String totalAmount;
        public String month;
        public boolean isCheck = false;
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
             * tixian_id : 316159513650528256
             * bankName : 中国银行
             * cardNo : 000000040396
             * applyTime : 2019-03-23 10:25:02
             * totalAmount : 1.00
             * recordStatus : 1
             * recordStatusName : 已打款
             */

            public String tixian_id;
            public String bankName;
            public String cardNo;
            public String applyTime;
            public String totalAmount;
            public String recordStatus;
            public String recordStatusName;

            @Override
            public int getItemType() {
                return TYPE_LEVEL_1;
            }
        }
    }
}
