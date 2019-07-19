package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

public class CardNameBean extends BaseBean {

    /**
     * data : [{"id":209,"num":1,"pid":208,"name":"中国工商银行","list":[]},{"id":210,"num":1,"pid":208,"name":"中国建设银行","list":[]},{"id":211,"num":1,"pid":208,"name":"中国银行","list":[]},{"id":212,"num":1,"pid":208,"name":"中国农业银行","list":[]},{"id":213,"num":1,"pid":208,"name":"交通银行","list":[]},{"id":214,"num":1,"pid":208,"name":"招商银行","list":[]},{"id":215,"num":1,"pid":208,"name":"中信银行","list":[]},{"id":216,"num":1,"pid":208,"name":"浦发银行","list":[]},{"id":217,"num":1,"pid":208,"name":"兴业银行","list":[]},{"id":218,"num":1,"pid":208,"name":"民生银行","list":[]},{"id":219,"num":1,"pid":208,"name":"中国邮政储蓄银行","list":[]},{"id":220,"num":1,"pid":208,"name":"中国光大银行","list":[]},{"id":221,"num":1,"pid":208,"name":"平安银行","list":[]},{"id":222,"num":1,"pid":208,"name":"华夏银行","list":[]}]
     * curson : null
     * erros : null
     */

    public Object curson;
    public Object erros;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 209
         * num : 1
         * pid : 208
         * name : 中国工商银行
         * list : []
         */

        public int id;
        public int num;
        public int pid;
        public String name;
        public List<?> list;
    }
}
