package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProjectSelListBean extends BaseBean {

    /**
     * data : {"total":0,"size":10,"current":1,"records":[{"projectName":"黄金焕肤","projectFunction":"排除铅汞等重金属和化妆品残留、提亮肤色","rate":"20%"},{"projectName":"雪花焕肤","projectFunction":"急速补水保湿美白、补充胶原蛋白","rate":""},{"projectName":"巧克力焕肤","projectFunction":"美白滋养、去黄气","rate":""},{"projectName":"灵芝焕肤","projectFunction":"抑菌调理炎症、修复再生、深层清洁除螨、控油祛痘","rate":""},{"projectName":"红酒焕肤","projectFunction":"深层保湿、淡化细纹、提亮肤色、紧致滋养","rate":""},{"projectName":"马卡龙焕肤","projectFunction":"补水保湿、提亮肤色、控油平衡、淡化细纹、补充胶原蛋白","rate":""},{"projectName":"木乃伊焕肤","projectFunction":"去水肿、塑造小V脸、淡化细纹、补水滋养","rate":""},{"projectName":"叶绿素焕肤","projectFunction":"抑制油脂分泌、疏通毛孔、驱逐痤疮丙酸杆菌、改善痤疮炎症","rate":""},{"projectName":"海藻焕肤","projectFunction":"调理痘痘肌肤、改善皮肤粗糙和痘坑、淡化痘印","rate":""},{"projectName":"童颜焕肤","projectFunction":"调理角质、淡化细纹、提亮肤色","rate":""},{"projectName":"花瓣焕肤","projectFunction":"改善微循环、刺激荷尔蒙、补充水分、提亮肤色","rate":""},{"projectName":"MTS微针管理","projectFunction":"淡化痘坑痘疤、淡化皱纹、收缩毛孔","rate":""},{"projectName":"小气泡清洁","projectFunction":"去黑头、清洁毛孔、去角质、补水、平衡油脂","rate":""},{"projectName":"蜂蜜焕肤","projectFunction":"水光提亮、淡化细纹、修复皮肤屏障","rate":""},{"projectName":"干细胞焕肤","projectFunction":"去痘印痘坑、提亮肤色、淡化细纹、收细毛孔","rate":""},{"projectName":"水光焕肤","projectFunction":"深层补水、保湿滋养、调节水油平衡","rate":""},{"projectName":"新氧焕肤","projectFunction":"温和补水、提亮肤色","rate":""},{"projectName":"黑磁焕肤","projectFunction":"去角质、清洁毛孔、调节新陈代谢、改善肤色","rate":""},{"projectName":"白金焕肤","projectFunction":"排除铅汞等重金属和化妆品残留、提亮肤色、增加皮肤含水量","rate":""},{"projectName":"牛奶焕肤","projectFunction":"去角质、美白肌肤、淡化细纹","rate":""},{"projectName":"天鹅绒焕肤","projectFunction":"补充胶原蛋白、深层补水、改善暗沉肤色不均、淡化细纹","rate":""},{"projectName":"樱花焕肤","projectFunction":"改善色斑和肤色不均、补充水分、舒缓刺激","rate":""},{"projectName":"深海胶原蛋白焕肤","projectFunction":"补水滋养、调节皮肤水油平衡、修复皮肤保湿系统、改善干燥泛红敏感、暗沉","rate":""},{"projectName":"南瓜焕肤","projectFunction":"去角质、淡化皱纹、美白皮肤","rate":""}],"pages":0}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * total : 0
         * size : 10
         * current : 1
         * records : [{"projectName":"黄金焕肤","projectFunction":"排除铅汞等重金属和化妆品残留、提亮肤色","rate":"20%"},{"projectName":"雪花焕肤","projectFunction":"急速补水保湿美白、补充胶原蛋白","rate":""},{"projectName":"巧克力焕肤","projectFunction":"美白滋养、去黄气","rate":""},{"projectName":"灵芝焕肤","projectFunction":"抑菌调理炎症、修复再生、深层清洁除螨、控油祛痘","rate":""},{"projectName":"红酒焕肤","projectFunction":"深层保湿、淡化细纹、提亮肤色、紧致滋养","rate":""},{"projectName":"马卡龙焕肤","projectFunction":"补水保湿、提亮肤色、控油平衡、淡化细纹、补充胶原蛋白","rate":""},{"projectName":"木乃伊焕肤","projectFunction":"去水肿、塑造小V脸、淡化细纹、补水滋养","rate":""},{"projectName":"叶绿素焕肤","projectFunction":"抑制油脂分泌、疏通毛孔、驱逐痤疮丙酸杆菌、改善痤疮炎症","rate":""},{"projectName":"海藻焕肤","projectFunction":"调理痘痘肌肤、改善皮肤粗糙和痘坑、淡化痘印","rate":""},{"projectName":"童颜焕肤","projectFunction":"调理角质、淡化细纹、提亮肤色","rate":""},{"projectName":"花瓣焕肤","projectFunction":"改善微循环、刺激荷尔蒙、补充水分、提亮肤色","rate":""},{"projectName":"MTS微针管理","projectFunction":"淡化痘坑痘疤、淡化皱纹、收缩毛孔","rate":""},{"projectName":"小气泡清洁","projectFunction":"去黑头、清洁毛孔、去角质、补水、平衡油脂","rate":""},{"projectName":"蜂蜜焕肤","projectFunction":"水光提亮、淡化细纹、修复皮肤屏障","rate":""},{"projectName":"干细胞焕肤","projectFunction":"去痘印痘坑、提亮肤色、淡化细纹、收细毛孔","rate":""},{"projectName":"水光焕肤","projectFunction":"深层补水、保湿滋养、调节水油平衡","rate":""},{"projectName":"新氧焕肤","projectFunction":"温和补水、提亮肤色","rate":""},{"projectName":"黑磁焕肤","projectFunction":"去角质、清洁毛孔、调节新陈代谢、改善肤色","rate":""},{"projectName":"白金焕肤","projectFunction":"排除铅汞等重金属和化妆品残留、提亮肤色、增加皮肤含水量","rate":""},{"projectName":"牛奶焕肤","projectFunction":"去角质、美白肌肤、淡化细纹","rate":""},{"projectName":"天鹅绒焕肤","projectFunction":"补充胶原蛋白、深层补水、改善暗沉肤色不均、淡化细纹","rate":""},{"projectName":"樱花焕肤","projectFunction":"改善色斑和肤色不均、补充水分、舒缓刺激","rate":""},{"projectName":"深海胶原蛋白焕肤","projectFunction":"补水滋养、调节皮肤水油平衡、修复皮肤保湿系统、改善干燥泛红敏感、暗沉","rate":""},{"projectName":"南瓜焕肤","projectFunction":"去角质、淡化皱纹、美白皮肤","rate":""}]
         * pages : 0
         */

        public int total;
        public int size;
        public int current;
        public int pages;
        public List<RecordsBean> records;

        public static class RecordsBean {
            /**
             * projectName : 黄金焕肤
             * projectFunction : 排除铅汞等重金属和化妆品残留、提亮肤色
             * rate : 20%
             */

            public String projectName;
            public String projectId;
            public String projectFunction;
            public String rate;
            public String ppropTime;
        }
    }
}
