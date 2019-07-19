package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/3/31.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class VersionBean extends BaseBean {


    /**
     * data : {"description":"123","isForce":"0","time":"2019-02-27T09:50:22","versionName":"12","versionCode":"123","url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/app/12/"}
     * curson : null
     * erros : null
     */

    public DataBean data;

    public static class DataBean {
        /**
         * description : 123
         * isForce : 0
         * time : 2019-02-27T09:50:22
         * versionName : 12
         * versionCode : 123
         * url : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/app/12/
         */

        public String description;
        public String isForce;

        public String time;
        public String versionName;
        public String versionCode;
        public String url;
    }
}
