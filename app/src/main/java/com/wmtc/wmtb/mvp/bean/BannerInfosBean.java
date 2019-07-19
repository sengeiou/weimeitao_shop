package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class BannerInfosBean extends BaseBean {

    /**
     * data : [{"id":315219065389973504,"businessId":308624582166708224,"type":1,"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/idcard.jpg","order":1,"remarks":null,"createBy":315219065389973504,"createTime":"2019-03-20T12:08:02.000+0000","updateBy":null,"updateTime":null,"status":null,"serviceType":null},{"id":318066256651485184,"businessId":308624582166708224,"type":1,"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/1553762500442.png","order":1,"remarks":null,"createBy":318066256651485184,"createTime":"2019-03-28T08:41:45.000+0000","updateBy":null,"updateTime":null,"status":null,"serviceType":null},{"id":318067787396284416,"businessId":308624582166708224,"type":1,"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/1553762860607.png","order":1,"remarks":null,"createBy":318067787396284416,"createTime":"2019-03-28T08:47:50.000+0000","updateBy":null,"updateTime":null,"status":null,"serviceType":null},{"id":318069462873931776,"businessId":308624582166708224,"type":1,"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/快.png","order":1,"remarks":null,"createBy":318069462873931776,"createTime":"2019-03-28T08:54:29.000+0000","updateBy":null,"updateTime":null,"status":null,"serviceType":null}]
     * curson : null
     * erros : null
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 315219065389973504
         * businessId : 308624582166708224
         * type : 1
         * url : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/idcard.jpg
         * order : 1
         * remarks : null
         * createBy : 315219065389973504
         * createTime : 2019-03-20T12:08:02.000+0000
         * updateBy : null
         * updateTime : null
         * status : null
         * serviceType : null
         */

        public long id;
        public long businessId;
        public int type;
        public String url;
        public int order;
        public String createTime;
    }
}
