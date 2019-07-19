package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/3/22.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LoginBean extends BaseBean {

    /**
     * data : {"stockUrl":"http://wx.weimeitao.net/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=account.login","userId":"315782369833385984","stockNo":"已绑定手机号","token":"841613d3a59253f29ec9225d28c89595","stockPwd":"已绑定密码"}
     * curson : null
     * erros : null
     */

    public DataBean data;
    public Object curson;
    public Object erros;

    public static class DataBean {
        /**
         * stockUrl : http://wx.weimeitao.net/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=account.login
         * userId : 315782369833385984
         * stockNo : 已绑定手机号
         * token : 841613d3a59253f29ec9225d28c89595
         * stockPwd : 已绑定密码
         */

        public String stockUrl;
        public String userId;
        public String stockNo;
        public String token;
        public String stockPwd;
    }
}
