package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

public class CardListBean extends BaseBean {

    /**
     * data : [{"id":"315510643698434048","userId":"1","userType":"2","cardNo":"000000040396","telPhone":"13816606990","cardBank":"中国银行","userName":"刘小杰"}]
     * curson : null
     * erros : null
     */

    public Object curson;
    public Object erros;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 315510643698434048
         * userId : 1
         * userType : 2
         * cardNo : 000000040396
         * telPhone : 13816606990
         * cardBank : 中国银行
         * userName : 刘小杰
         */

        public String id;
        public String userId;
        public String userType;
        public String cardNo;
        public String telPhone;
        public String cardBank;
        public String userName;
        public boolean isCheck = false;
    }
}
