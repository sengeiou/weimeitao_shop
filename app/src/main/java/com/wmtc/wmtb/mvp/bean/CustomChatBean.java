package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/5/22.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CustomChatBean extends BaseBean {

    /**
     * data : {"jmessageUserName":"S_335369678815756288","isPublic":"1"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * jmessageUserName : S_335369678815756288
         * isPublic : 1
         */

        public String jmessageUserName;
        public String isPublic;
    }
}
