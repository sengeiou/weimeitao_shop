package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class DictListBean extends BaseBean {

    /**
     * data : [{"id":242,"num":1,"pid":241,"name":"黄金焕肤","list":[]},{"id":243,"num":2,"pid":241,"name":"雪花焕肤","list":[]},{"id":244,"num":3,"pid":241,"name":"巧克力焕肤","list":[]},{"id":245,"num":4,"pid":241,"name":"灵芝焕肤","list":[]},{"id":246,"num":5,"pid":241,"name":"红酒焕肤","list":[]},{"id":247,"num":6,"pid":241,"name":"马卡龙焕肤","list":[]},{"id":248,"num":7,"pid":241,"name":"木乃伊焕肤","list":[]},{"id":249,"num":8,"pid":241,"name":"叶绿素焕肤","list":[]},{"id":250,"num":9,"pid":241,"name":"海藻焕肤","list":[]},{"id":251,"num":10,"pid":241,"name":"蜂蜜焕肤","list":[]}]
     * curson : null
     * erros : null
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 199
         * num : 1
         * pid : 198
         * name : 油性皮肤
         * list : []
         */

        public String id;
        public String num;
        public String pid;
        public String name;
        public List<?> list;
        public boolean isSel = false;
    }
}
