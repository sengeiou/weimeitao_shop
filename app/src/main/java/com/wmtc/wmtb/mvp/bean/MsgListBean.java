package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/30.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MsgListBean extends BaseBean {

    /**
     * data : [{"messageId":"318786864414720000","urlParam":null,"urlName":null,"messageTitle":" 我是通知","messageContent":"hello,我是通知","createTime":"2019-03-30 08:25:11","createName":null}]
     * curson : null
     * erros : null
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * messageId : 318786864414720000
         * messageTitle :  我是通知
         * messageContent : hello,我是通知
         * createTime : 2019-03-30 08:25:11
         * createName : null
         */

        public String messageId;
        public String messageTitle;
        public String messageContent;
        public String createTime;
        public String avatar;
        public String chatId;
        public String isMsg;
    }
}
