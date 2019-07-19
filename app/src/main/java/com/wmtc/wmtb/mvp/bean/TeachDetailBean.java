package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/28.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TeachDetailBean extends BaseBean {

    /**
     * data : {"sysShopTechnician":{"id":310461330307940352,"shopId":308624582166708224,"shopName":"刘杰的小铺","technicianName":"刘姐姐","avatar":null,"title":"Guns技术文档最新版","goodAt":"打人啊啊啊啊","workTime":"2019-03-07","restDay":"星期二,星期三,星期六","createBy":310461330307940352,"createTime":"2019-03-07T09:02:29.000+0000","updateBy":null,"updateTime":null,"remarks":null,"status":1,"technicianIdcardName":null,"technicianIdcardNo":null,"technicianIdcardFront":null,"technicianIdcardReverse":null,"introduce":null},"attaches":[{"id":310462896146481152,"businessId":310461330307940352,"type":1,"url":"310461330307940352/pla.jpg","order":1,"remarks":null,"createBy":1,"createTime":"2019-03-07T09:08:43.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":null}]}
     * curson : null
     * erros : null
     */

    public DataBean data;

    public static class DataBean {
        /**
         * sysShopTechnician : {"id":310461330307940352,"shopId":308624582166708224,"shopName":"刘杰的小铺","technicianName":"刘姐姐","avatar":null,"title":"Guns技术文档最新版","goodAt":"打人啊啊啊啊","workTime":"2019-03-07","restDay":"星期二,星期三,星期六","createBy":310461330307940352,"createTime":"2019-03-07T09:02:29.000+0000","updateBy":null,"updateTime":null,"remarks":null,"status":1,"technicianIdcardName":null,"technicianIdcardNo":null,"technicianIdcardFront":null,"technicianIdcardReverse":null,"introduce":null}
         * attaches : [{"id":310462896146481152,"businessId":310461330307940352,"type":1,"url":"310461330307940352/pla.jpg","order":1,"remarks":null,"createBy":1,"createTime":"2019-03-07T09:08:43.000+0000","updateBy":null,"updateTime":null,"status":1,"serviceType":null}]
         */

        public SysShopTechnicianBean sysShopTechnician;

        public static class SysShopTechnicianBean {
            /**
             * id : 310461330307940352
             * shopId : 308624582166708224
             * shopName : 刘杰的小铺
             * technicianName : 刘姐姐
             * avatar : null
             * title : Guns技术文档最新版
             * goodAt : 打人啊啊啊啊
             * workTime : 2019-03-07
             * restDay : 星期二,星期三,星期六
             * createBy : 310461330307940352
             * createTime : 2019-03-07T09:02:29.000+0000
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
            public int status;
            public String technicianIdcardName;
            public String technicianIdcardNo;
            public String technicianIdcardFront;
            public String technicianIdcardReverse;
            public String introduce;
        }
    }
}
