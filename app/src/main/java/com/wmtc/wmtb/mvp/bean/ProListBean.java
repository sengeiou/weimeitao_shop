package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProListBean extends BaseBean {

    /**
     * data : [{"projectName":"源自韩国皮肤管理界的皮肤深层","projectTypeName":"黄金焕肤","effect":"排除铅汞等重金属和化妆品残留、提亮肤色","status":"1","projectPrice":"1.0000"},{"projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectTypeName":"黄金焕肤","effect":"排除铅汞等重金属和化妆品残留、提亮肤色","status":"1","projectPrice":"1.0000"}]
     * curson : null
     * erros : null
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * projectName : 源自韩国皮肤管理界的皮肤深层
         * projectTypeName : 黄金焕肤
         * effect : 排除铅汞等重金属和化妆品残留、提亮肤色
         * status : 1
         * projectPrice : 1.0000
         */

        public String projectId;
        public String projectName;
        public String projectTypeName;
        public String effect;
        public String status;
        public String projectPrice;
    }
}
