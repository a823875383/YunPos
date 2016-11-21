package com.jsqix.yunpos.app.bean;

import java.util.List;

/**
 * Created by dq on 2016/7/25.
 */
public class InforBean extends BaseBean{

    /**
     * amount : 0.radio_mid_fill
     * addtime : 1469419123000
     * details : [{"amount":0.radio_mid_fill,"addtime":1469419123000,"phone":"15800000130","status":2,"remark":"短信验证码输入有误或已失效","upttime":1469419129000,"remark1":"","orderId":"26770","order_type":1}]
     * remark :
     * status : 3
     * upttime :
     * remark1 : 短信验证码输入有误或已失效
     * orderId : 26770
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private double amount;
        private String addtime;
        private String remark;
        private int status;
        private String upttime;
        private String remark1;
        private String orderId;
        /**
         * amount : 0.radio_mid_fill
         * addtime : 1469419123000
         * phone : 15800000130
         * status : 2
         * remark : 短信验证码输入有误或已失效
         * upttime : 1469419129000
         * remark1 :
         * orderId : 26770
         * order_type : 1
         */

        private List<DetailsEntity> details;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpttime() {
            return upttime;
        }

        public void setUpttime(String upttime) {
            this.upttime = upttime;
        }

        public String getRemark1() {
            return remark1;
        }

        public void setRemark1(String remark1) {
            this.remark1 = remark1;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public List<DetailsEntity> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsEntity> details) {
            this.details = details;
        }

        public static class DetailsEntity {
            private String amount;
            private String addtime;
            private String phone;
            private int status;
            private String remark;
            private String upttime;
            private String remark1;
            private String orderId;
            private int order_type;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUpttime() {
                return upttime;
            }

            public void setUpttime(String upttime) {
                this.upttime = upttime;
            }

            public String getRemark1() {
                return remark1;
            }

            public void setRemark1(String remark1) {
                this.remark1 = remark1;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getOrder_type() {
                return order_type;
            }

            public void setOrder_type(int order_type) {
                this.order_type = order_type;
            }
        }
    }
}
