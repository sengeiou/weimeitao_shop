package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

public class CanNotTixianBean extends BaseBean {

    /**
     * data : {"totalAmount":"16.00","num":3}
     * curson : null
     * erros : null
     */

    public DataBean data;
    public Object curson;
    public Object erros;

    public static class DataBean {
        /**
         * totalAmount : 16.00
         * num : 3
         */

        public String totalAmount;
        public String num;
    }
}
