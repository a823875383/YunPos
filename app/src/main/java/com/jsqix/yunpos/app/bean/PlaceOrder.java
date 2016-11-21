package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/7/25.
 */
public class PlaceOrder extends BaseBean{

    /**
     * orderType : 2
     * channelType :
     * orderId : 26632
     * detailId : 26633
     * status : 0
     * wxStr : weixin://wxpay/bizpayurl?pr=yBAZ52g
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
        private String channelType;
        private String orderId;
        private String detailId;
        private String status;
        private String statusRemark;
        private String wxStr;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
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

        public String getWxStr() {
            return wxStr;
        }

        public void setWxStr(String wxStr) {
            this.wxStr = wxStr;
        }
    }
}
