package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/4/29.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumListBean extends BaseBean {

    /**
     * data : {"total":33,"size":10,"current":1,"records":[{"forumId":"328940698155876352","forumType":"1","subitemId":"328940695060480000","zanStatus":"-1","picture":"xinde/12209a81387a092d8aaedc18fbea4344.png","content":"啦啦啦啦啦啦","userId":"323475763305119744","createUser":"梦幻公主","createUserAvatar":"avatar/2019041242673cde-75a0-4f63-992a-176173e0bb7b-photo.jpg","dianzanNum":"1"},{"forumId":"324505891883712512","forumType":"1","subitemId":"324505888880590848","zanStatus":"-1","picture":"xinde/9de4669c5ae1b9f5d703aa005622a189.png","content":"清洁是护肤的第一步哦","userId":"323475763305119744","createUser":"梦幻公主","createUserAvatar":"avatar/2019041242673cde-75a0-4f63-992a-176173e0bb7b-photo.jpg","dianzanNum":"2"},{"forumId":"324513292598902784","forumType":"1","subitemId":"324513286785597440","zanStatus":"-1","picture":"xinde/107cbff96700171bebc6dd7488ce65c3.png","content":"导入仪会有用嘛","userId":"322791411726417920","createUser":"萌萌哒","createUserAvatar":"avatar/20190410c76036a7-5730-4587-9f33-0b468c1a6281-photo.jpg","dianzanNum":"2"},{"forumId":"324560734522441728","forumType":"2","subitemId":"324560733847158784","zanStatus":"-1","picture":"heji/c962c566d55eb82d3ae5b53a5efb9d92.png","content":"我们要努力","userId":"322791411726417920","createUser":"萌萌哒","createUserAvatar":"avatar/20190410c76036a7-5730-4587-9f33-0b468c1a6281-photo.jpg","dianzanNum":"2"},{"forumId":"324510279306051584","forumType":"1","subitemId":"324510274801369088","zanStatus":"-1","picture":"xinde/c27dde71f59527790094e9e731dd92df.png","content":"按摩皮肤真的有效哦","userId":"323471596649971712","createUser":"淡淡的额","createUserAvatar":"avatar/201904120f4d4a6d-f36a-46f9-8e80-b5c7ba4b75e0-photo.jpg","dianzanNum":"4"},{"forumId":"323887778918039552","forumType":"1","subitemId":"323887776850247680","zanStatus":"-1","picture":"xinde/f309f9f6b92cf0b6ac496bc810df732d.png","content":"合计111","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"2"},{"forumId":"326317025175535616","forumType":"1","subitemId":"326317023158075392","zanStatus":"-1","picture":"xinde/47f5b4eb658a222c42005651bde84934.png","content":"测试","userId":"316274435650748416","createUser":"流浪猫","createUserAvatar":"avatar/20190418c966dd79-6250-4375-bc2b-66c97a18df31-photo.jpg","dianzanNum":"1"},{"forumId":"320223822936539136","forumType":"2","subitemId":"320223822009597952","zanStatus":"-1","picture":"banner/308547882288414720/2.png","content":"0412-3","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"12"},{"forumId":"324602949915377664","forumType":"2","subitemId":"324602949135237120","zanStatus":"-1","picture":"banner/308547882288414720/2.png","content":"合辑标题0403","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"1"},{"forumId":"320222428519202816","forumType":"1","subitemId":"320222420600356864","zanStatus":"-1","picture":"banner/308547882288414720/2.png","content":"0412-4","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"28"},{"forumId":"326324839235190784","forumType":"1","subitemId":"326324837318393856","zanStatus":"-1","picture":"xinde/0c56d3ae28470056390d4eb27cf7589c.png","content":"苏大强来了","userId":"319142090766286848","createUser":"哈哈哈哈哈","createUserAvatar":"avatar/20190331eb25b148-c629-4a52-b2ed-d148069f9190-photo.jpg","dianzanNum":"1"}],"pages":4}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * total : 33
         * size : 10
         * current : 1
         * records : [{"forumId":"328940698155876352","forumType":"1","subitemId":"328940695060480000","zanStatus":"-1","picture":"xinde/12209a81387a092d8aaedc18fbea4344.png","content":"啦啦啦啦啦啦","userId":"323475763305119744","createUser":"梦幻公主","createUserAvatar":"avatar/2019041242673cde-75a0-4f63-992a-176173e0bb7b-photo.jpg","dianzanNum":"1"},{"forumId":"324505891883712512","forumType":"1","subitemId":"324505888880590848","zanStatus":"-1","picture":"xinde/9de4669c5ae1b9f5d703aa005622a189.png","content":"清洁是护肤的第一步哦","userId":"323475763305119744","createUser":"梦幻公主","createUserAvatar":"avatar/2019041242673cde-75a0-4f63-992a-176173e0bb7b-photo.jpg","dianzanNum":"2"},{"forumId":"324513292598902784","forumType":"1","subitemId":"324513286785597440","zanStatus":"-1","picture":"xinde/107cbff96700171bebc6dd7488ce65c3.png","content":"导入仪会有用嘛","userId":"322791411726417920","createUser":"萌萌哒","createUserAvatar":"avatar/20190410c76036a7-5730-4587-9f33-0b468c1a6281-photo.jpg","dianzanNum":"2"},{"forumId":"324560734522441728","forumType":"2","subitemId":"324560733847158784","zanStatus":"-1","picture":"heji/c962c566d55eb82d3ae5b53a5efb9d92.png","content":"我们要努力","userId":"322791411726417920","createUser":"萌萌哒","createUserAvatar":"avatar/20190410c76036a7-5730-4587-9f33-0b468c1a6281-photo.jpg","dianzanNum":"2"},{"forumId":"324510279306051584","forumType":"1","subitemId":"324510274801369088","zanStatus":"-1","picture":"xinde/c27dde71f59527790094e9e731dd92df.png","content":"按摩皮肤真的有效哦","userId":"323471596649971712","createUser":"淡淡的额","createUserAvatar":"avatar/201904120f4d4a6d-f36a-46f9-8e80-b5c7ba4b75e0-photo.jpg","dianzanNum":"4"},{"forumId":"323887778918039552","forumType":"1","subitemId":"323887776850247680","zanStatus":"-1","picture":"xinde/f309f9f6b92cf0b6ac496bc810df732d.png","content":"合计111","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"2"},{"forumId":"326317025175535616","forumType":"1","subitemId":"326317023158075392","zanStatus":"-1","picture":"xinde/47f5b4eb658a222c42005651bde84934.png","content":"测试","userId":"316274435650748416","createUser":"流浪猫","createUserAvatar":"avatar/20190418c966dd79-6250-4375-bc2b-66c97a18df31-photo.jpg","dianzanNum":"1"},{"forumId":"320223822936539136","forumType":"2","subitemId":"320223822009597952","zanStatus":"-1","picture":"banner/308547882288414720/2.png","content":"0412-3","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"12"},{"forumId":"324602949915377664","forumType":"2","subitemId":"324602949135237120","zanStatus":"-1","picture":"banner/308547882288414720/2.png","content":"合辑标题0403","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"1"},{"forumId":"320222428519202816","forumType":"1","subitemId":"320222420600356864","zanStatus":"-1","picture":"banner/308547882288414720/2.png","content":"0412-4","userId":"316891592067645440","createUser":"蔡超测试（勿动）","createUserAvatar":"avatar/20190415082bc447-14a0-4b32-a09a-ab90e49d919f-photo.jpg","dianzanNum":"28"},{"forumId":"326324839235190784","forumType":"1","subitemId":"326324837318393856","zanStatus":"-1","picture":"xinde/0c56d3ae28470056390d4eb27cf7589c.png","content":"苏大强来了","userId":"319142090766286848","createUser":"哈哈哈哈哈","createUserAvatar":"avatar/20190331eb25b148-c629-4a52-b2ed-d148069f9190-photo.jpg","dianzanNum":"1"}]
         * pages : 4
         */

        public int total;
        public int size;
        public int current;
        public int pages;
        public List<RecordsBean> records;

        public static class RecordsBean {
            /**
             * forumId : 328940698155876352
             * forumType : 1
             * subitemId : 328940695060480000
             * zanStatus : -1
             * picture : xinde/12209a81387a092d8aaedc18fbea4344.png
             * content : 啦啦啦啦啦啦
             * userId : 323475763305119744
             * createUser : 梦幻公主
             * createUserAvatar : avatar/2019041242673cde-75a0-4f63-992a-176173e0bb7b-photo.jpg
             * dianzanNum : 1
             */

            public String forumId;
            public String forumType;
            public String subitemId;
            public String zanStatus;
            public String picture;
            public String content;
            public String userId;
            public String createUser;
            public String createUserAvatar;
            public String dianzanNum;
        }
    }
}
