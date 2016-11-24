package com.jsqix.yunpos.app.bean;

/**
 * Created by dongqing on 2016/11/22.
 */

public class CodeOrderBean extends BaseBean{


    /**
     * obj : {"orderType":"1","orderId":"39295","detailId":"39296","status":"4","statusRemark":"待审核"}
     * url : null
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * orderType : 1
         * orderId : 39295
         * detailId : 39296
         * status : 4
         * statusRemark : 待审核
         */

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
