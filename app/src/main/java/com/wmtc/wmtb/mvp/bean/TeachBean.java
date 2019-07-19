package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/27.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TeachBean extends BaseBean {

    /**
     * data : [{"id":310463593147531264,"shopId":308624582166708224,"shopName":"刘杰的小铺","technicianName":"122","avatar":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/avatar/310463593147531264/ee.jpg","title":"123","goodAt":"打人","workTime":"2017-03-07","restDay":"星期六","createBy":310463593147531264,"createTime":"2019-03-07T09:11:29.000+0000","updateBy":null,"updateTime":null,"remarks":null,"status":1,"technicianIdcardName":null,"technicianIdcardNo":null,"technicianIdcardFront":null,"technicianIdcardReverse":null,"introduce":null},{"id":310461330307940352,"shopId":308624582166708224,"shopName":"刘杰的小铺","technicianName":"刘姐姐","avatar":null,"title":"Guns技术文档最新版","goodAt":"打人啊啊啊啊","workTime":"2019-03-07","restDay":"星期二,星期三,星期六","createBy":310461330307940352,"createTime":"2019-03-07T09:02:29.000+0000","updateBy":null,"updateTime":null,"remarks":null,"status":1,"technicianIdcardName":null,"technicianIdcardNo":null,"technicianIdcardFront":null,"technicianIdcardReverse":null,"introduce":null}]
     * curson : null
     * erros : null
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 310463593147531264
         * shopId : 308624582166708224
         * shopName : 刘杰的小铺
         * technicianName : 122
         * avatar : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/avatar/310463593147531264/ee.jpg
         * title : 123
         * goodAt : 打人
         * workTime : 2017-03-07
         * restDay : 星期六
         * createBy : 310463593147531264
         * createTime : 2019-03-07T09:11:29.000+0000
         * updateBy : null
         * updateTime : null
         * remarks : null
         * status : 1
         * technicianIdcardName : null
         * technicianIdcardNo : null
         * technicianIdcardFront : null
         * technicianIdcardReverse : null
         * introduce : null
         */

        public long id;
        public long shopId;
        public String shopName;
        public String technicianName;
        public String avatar;
        public String title;
        public String goodAt;
        public String workTime;
        public String restDay;
        public String technicianIdcardName;
        public String technicianIdcardNo;
        public String technicianIdcardFront;
        public String technicianIdcardReverse;
        public String introduce;
        public int status;
    }
}
