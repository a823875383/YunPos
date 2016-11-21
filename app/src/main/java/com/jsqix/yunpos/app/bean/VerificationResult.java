package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/11/17.
 */

public class VerificationResult extends BaseBean{

    /**
     * orderType : 1
     * orderId : 39051
     * detailId : 39052
     * status : 1
     * statusRemark : 支付成功
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String orderType;
        private String orderId;
        private String detailId;
        private String status;
        private String statusRemark;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getDetailId() {
            return detailId;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusRemark() {
            return statusRemark;
        }

        public void setStatusRemark(String statusRemark) {
            this.statusRemark = statusRemark;
        }
    }
}
