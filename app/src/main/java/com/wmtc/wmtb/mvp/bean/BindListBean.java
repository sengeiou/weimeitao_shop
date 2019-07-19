package com.wmtc.wmtb.mvp.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_0;
import static com.wmtc.wmtb.mvp.bean.TixianBean.TYPE_LEVEL_1;

/**
 * Created by Obl on 2019/4/19.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class BindListBean extends BaseBean {


    /**
     * data : {"totalNum":"3","list":[{"date":"2019-04-12","num":1,"list":[{"projectPriceEndPaidTime":"09:24:14","projectName":"1"}]},{"date":"2019-04-10","num":2,"list":[{"projectPriceEndPaidTime":"16:28:43","projectName":"测试专用"},{"projectPriceEndPaidTime":"15:49:42","projectName":"妞妞"}]}]}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * totalNum : 3
         * list : [{"date":"2019-04-12","num":1,"list":[{"projectPriceEndPaidTime":"09:24:14","projectName":"1"}]},{"date":"2019-04-10","num":2,"list":[{"projectPriceEndPaidTime":"16:28:43","projectName":"测试专用"},{"projectPriceEndPaidTime":"15:49:42","projectName":"妞妞"}]}]
         */

        public String totalNum;
        public List<ListBeanX> list;

        public static class ListBeanX extends AbstractExpandableItem<ListBeanX.ListBean> implements MultiItemEntity {
            /**
             * date : 2019-04-12
             * num : 1
             * list : [{"projectPriceEndPaidTime":"09:24:14","projectName":"1"}]
             */

            public String date;
            public int num;
            public String totalNum;
            public boolean isAdd = false;
            public List<ListBean> list;

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getItemType() {
                return TYPE_LEVEL_0;
            }

            public static class ListBean implements MultiItemEntity {
                /**
                 * projectPriceEndPaidTime : 09:24:14
                 * projectName : 1
                 */

                public String projectPriceEndPaidTime;
                public String projectName;


                @Override
                public int getItemType() {
                    return TYPE_LEVEL_1;
                }
            }
        }
    }
}
