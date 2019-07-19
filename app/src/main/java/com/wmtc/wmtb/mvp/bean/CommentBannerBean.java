package com.wmtc.wmtb.mvp.bean;


import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

public class CommentBannerBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * path : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/banner/7b321e25-194a-4d92-b05b-87f05e8b5864社1.jpg
         * type : 202
         * sequence : 1
         * linkType : 1
         * linkUrl : 312280410358808576
         */

        public String path;
        public String type;
        public int sequence;
        public String linkType;
        public String linkUrl;
    }
}
