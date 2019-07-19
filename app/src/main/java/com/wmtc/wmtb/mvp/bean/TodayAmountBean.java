package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/3/26.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TodayAmountBean extends BaseBean {


    /**
     * data : {"totalAmount":"0.00","num":0}
     * curson : null
     * erros : null
     */

    public DataBean data;

    public static class DataBean {
        /**
         * totalAmount : 0.00
         * num : 0
         */

        public String totalAmount;
        public int num;
        public int bangdingNum;
    }
}
