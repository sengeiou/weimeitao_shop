package com.wmtc.wmtb.mvp.pojo;

public class RefundPojo {
    public String id;
    public String userId;
    public String orderId;
    public String cancelOrderDescription;
    public String cancelOrderReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
