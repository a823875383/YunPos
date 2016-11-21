package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/7/25.
 */
public class WxOrderStatus extends BaseBean{

    /**
     * orderId : 26892
     * detailId : 26893
     * status : 0
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String orderId;
        private String detailId;
        private int status;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
