package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class VerLoginBean extends BaseBean {


    /**
     * data : {"stockNo":"已绑定手机号","stockPwd":"已绑定密码"}
     * curson : null
     * erros : null
     */

    public DataBean data;
    public Object curson;
    public Object erros;

    public static class DataBean {
        /**
         * stockNo : 已绑定手机号
         * stockPwd : 已绑定密码
         */

        public String stockNo;
        public String stockPwd;
    }
}
