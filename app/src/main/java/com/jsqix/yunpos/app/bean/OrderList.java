package com.jsqix.yunpos.app.bean;

import java.util.List;

/**
 * Created by dq on 2016/4/12.
 */
public class OrderList extends BaseBean {


    /**
     * top_str:0.000
     * orderList : [{"amount":0.radio_mid_rect,"id":"1726","phone":"15370333297","cmdstatus":"错误:用户未申请动态支付密码","status":2,"remark":"","addtimestr":"2016-05-26 17:30:59"},{"amount":0.radio_mid_rect,"id":"1725","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 17:20:35"},{"amount":0.radio_mid_rect,"id":"1724","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 17:17:15"},{"amount":0.radio_mid_rect,"id":"1723","phone":"15800000130","cmdstatus":"短信验证码输入有误或已失效","status":2,"remark":"","addtimestr":"2016-05-26 17:13:25"},{"amount":0.1,"id":"1722","phone":"15800000130","cmdstatus":"短信验证码输入有误或已失效","status":2,"remark":"","addtimestr":"2016-05-26 17:09:15"},{"amount":0.1,"id":"1721","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 17:07:53"},{"amount":0.1,"id":"1720","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 17:05:14"},{"amount":0.1,"id":"1719","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 17:03:09"},{"amount":0.1,"id":"1718","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 16:55:35"},{"amount":0.1,"id":"1717","phone":"15800000130","cmdstatus":"0","status":0,"remark":"","addtimestr":"2016-05-26 16:53:16"},{"amount":0.11,"id":"1716","phone":"15800000130","cmdstatus":"0","status":0,"remark":"","addtimestr":"2016-05-26 16:51:11"},{"amount":0.1,"id":"1715","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 16:48:17"},{"amount":0.radio_mid_rect,"id":"1714","phone":"15800000130","cmdstatus":"手机号码提交成功","status":0,"remark":"","addtimestr":"2016-05-26 16:45:08"},{"amount":0.1,"id":"1713","phone":"15800000130","cmdstatus":"支付超时","status":-1,"remark":"","addtimestr":"2016-05-26 16:42:12"},{"amount":0.1,"id":"1712","phone":"15370333297","cmdstatus":"账户余额不足,支付失败","status":2,"remark":"","addtimestr":"2016-05-26 16:41:43"},{"amount":0.1,"id":"1711","phone":"15370333297","cmdstatus":"账户余额不足,支付失败","status":2,"remark":"","addtimestr":"2016-05-26 16:41:27"},{"amount":0.1,"id":"1710","phone":"15370333297","cmdstatus":"账户余额不足,支付失败","status":2,"remark":"","addtimestr":"2016-05-26 16:41:13"},{"amount":0.radio_mid_rect,"id":"1707","phone":"15800000130","cmdstatus":"支付超时","status":-1,"remark":"","addtimestr":"2016-05-26 14:52:34"},{"amount":0.radio_mid_rect,"id":"1706","phone":"15370333297","cmdstatus":"账户余额不足,支付失败","status":2,"remark":"","addtimestr":"2016-05-26 14:51:47"},{"amount":0.radio_mid_rect,"id":"1705","phone":"15800000130","cmdstatus":"支付超时","status":-1,"remark":"","addtimestr":"2016-05-26 14:06:52"}]
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
         * amount : 0.radio_mid_rect
         * id : 1726
         * phone : 15370333297
         * cmdstatus : 错误:用户未申请动态支付密码
         * status : 2
         * remark :
         * addtimestr : 2016-05-26 17:30:59
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
            private double amount;
            private String id;
            private String phone;
            private String cmdstatus;
            private int status;
            private String remark;
            private String addtimestr;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCmdstatus() {
                return cmdstatus;
            }

            public void setCmdstatus(String cmdstatus) {
                this.cmdstatus = cmdstatus;
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

            public String getAddtimestr() {
                return addtimestr;
            }

            public void setAddtimestr(String addtimestr) {
                this.addtimestr = addtimestr;
            }
        }
    }
}
