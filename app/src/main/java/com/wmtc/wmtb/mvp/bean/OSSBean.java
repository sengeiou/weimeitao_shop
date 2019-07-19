package com.wmtc.wmtb.mvp.bean;

import com.wmtc.wmtb.base.BaseBean;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OSSBean extends BaseBean {

    /**
     * data : {"StatusCode":"200","AccessKeyId":"STS.NJveMmGTXXtHgB4L3ujdFq7ih","AccessKeySecret":"6Bp8kkEvpTaBzkk4WNLbQtK1VCRu68vpEatiD5mSRufc","SecurityToken":"CAISkwJ1q6Ft5B2yfSjIr4nDLvfZqot577ajZWSFqDMgZutqnvLCijz2IH9OdHFhAe4ds/4wlGhW6fkYlqB6T55OSAmcNZIoKn7sCvXkMeT7oMWQweEuuv/MQBquaXPS2MvVfJ+OLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/m6/Ngdc9FHHP7D1x8CcxROxFppeIDKHLVLozNCBPxhXfKB0ca3WgZgGhku6Ok2Z/euFiMzn+Ck7RP9tSgecL8Ppg8YsogD+3YhrImKvDztwdL8AVP+atMi6hJxCzKpNn1ASMKvE/Wa7uMrIYydlApPvZlRPRe3/H4lOxlvOvIjJjwyBtLMuxTXj7WWIe62szAFfNP8kXz4249URqAAXJ/XLv14/YTmXaeEr9T+dnWZNaMtqIG/Jjvk12LRwnqMZR1qEuR+86z5STZBa3Te9hdGt1je/SNlmmszbR84f3B9VLhx6/KwRH4NEwMN4Y8B4R3tRw27E8fhqywwbtBOeHBagE793uqKUb37YGXBiYt3z4p0WapyPgFecJGOPvh","Expiration":"2019-04-03T09:08:16Z"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * StatusCode : 200
         * AccessKeyId : STS.NJveMmGTXXtHgB4L3ujdFq7ih
         * AccessKeySecret : 6Bp8kkEvpTaBzkk4WNLbQtK1VCRu68vpEatiD5mSRufc
         * SecurityToken : CAISkwJ1q6Ft5B2yfSjIr4nDLvfZqot577ajZWSFqDMgZutqnvLCijz2IH9OdHFhAe4ds/4wlGhW6fkYlqB6T55OSAmcNZIoKn7sCvXkMeT7oMWQweEuuv/MQBquaXPS2MvVfJ+OLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/m6/Ngdc9FHHP7D1x8CcxROxFppeIDKHLVLozNCBPxhXfKB0ca3WgZgGhku6Ok2Z/euFiMzn+Ck7RP9tSgecL8Ppg8YsogD+3YhrImKvDztwdL8AVP+atMi6hJxCzKpNn1ASMKvE/Wa7uMrIYydlApPvZlRPRe3/H4lOxlvOvIjJjwyBtLMuxTXj7WWIe62szAFfNP8kXz4249URqAAXJ/XLv14/YTmXaeEr9T+dnWZNaMtqIG/Jjvk12LRwnqMZR1qEuR+86z5STZBa3Te9hdGt1je/SNlmmszbR84f3B9VLhx6/KwRH4NEwMN4Y8B4R3tRw27E8fhqywwbtBOeHBagE793uqKUb37YGXBiYt3z4p0WapyPgFecJGOPvh
         * Expiration : 2019-04-03T09:08:16Z
         */

        public String StatusCode;
        public String AccessKeyId;
        public String AccessKeySecret;
        public String SecurityToken;
        public String Expiration;
    }
}
