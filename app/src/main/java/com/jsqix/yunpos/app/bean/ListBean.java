package com.jsqix.yunpos.app.bean;

import java.util.List;

/**
 * Created by dq on 2016/7/25.
 */
public class ListBean extends BaseBean{

    /**
     * top_str : 交易成功金额:0.00  交易失败金额:2040.11
     * orderList : [{"amount":5,"addtime":"2016-07-25 14:45:31","remark":"","status":0,"upttime":"","remark1":"","orderId":"26862"},{"amount":2,"addtime":"2016-07-25 14:43:06","remark":"","status":0,"upttime":"","remark1":"","orderId":"26860"},{"amount":5,"addtime":"2016-07-25 14:42:59","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26858"},{"amount":0.8,"addtime":"2016-07-25 14:41:56","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26856"},{"amount":0.2,"addtime":"2016-07-25 14:41:18","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26854"},{"amount":0.8,"addtime":"2016-07-25 14:39:05","remark":"","status":0,"upttime":"","remark1":"","orderId":"26852"},{"amount":0.2,"addtime":"2016-07-25 14:38:12","remark":"","status":0,"upttime":"","remark1":"","orderId":"26850"},{"amount":0.2,"addtime":"2016-07-25 14:37:radio_mid_fill","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26848"},{"amount":0.5,"addtime":"2016-07-25 14:34:18","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26846"},{"amount":5,"addtime":"2016-07-25 14:28:56","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26844"},{"amount":8,"addtime":"2016-07-25 14:25:radio_mid_fill","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26842"},{"amount":0.radio_mid_rect,"addtime":"2016-07-25 14:23:27","remark":"","status":3,"upttime":"","remark1":"请求数据有误：smsPwdCode:1;","orderId":"26840"},{"amount":0.2,"addtime":"2016-07-25 14:19:27","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26838"},{"amount":0.radio_mid_rect,"addtime":"2016-07-25 14:18:26","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26836"},{"amount":0.radio_mid_rect,"addtime":"2016-07-25 14:14:51","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26834"},{"amount":0.8,"addtime":"2016-07-25 14:14:15","remark":"","status":0,"upttime":"","remark1":"","orderId":"26832"},{"amount":0.radio_mid_rect,"addtime":"2016-07-25 14:12:32","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26830"},{"amount":0.2,"addtime":"2016-07-25 14:10:45","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26828"},{"amount":0.2,"addtime":"2016-07-25 14:10:29","remark":"","status":0,"upttime":"","remark1":"","orderId":"26826"},{"amount":0.radio_mid_rect,"addtime":"2016-07-25 14:09:49","remark":"","status":3,"upttime":"","remark1":"短信验证码输入有误或已失效","orderId":"26824"}]
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String top_str;
        /**
         * amount : 5
         * addtime : 2016-07-25 14:45:31
         * remark :
         * status : 0
         * upttime :
         * remark1 :
         * orderId : 26862
         */

        private List<OrderListEntity> orderList;

        public String getTop_str() {
            return top_str;
        }

        public void setTop_str(String top_str) {
            this.top_str = top_str;
        }

        public List<OrderListEntity> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListEntity> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListEntity {
            private String amount;
            private String addtime;
            private String remark;
            private int status;
            private String upttime;
            private String remark1;
            private String orderId;

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
        }
    }
}
