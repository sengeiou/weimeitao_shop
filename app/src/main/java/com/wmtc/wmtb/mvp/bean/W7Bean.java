package com.wmtc.wmtb.mvp.bean;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.base
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class W7Bean {

    /**
     * status : 1
     * result : {"url":"http://wx.weimeitao.net/./","message":"登录成功"}
     */

    public int status;
    public ResultBean result;

    public static class ResultBean {
        /**
         * url : http://wx.weimeitao.net/./
         * message : 登录成功
         */

        public String url;
        public String message;
    }
}
