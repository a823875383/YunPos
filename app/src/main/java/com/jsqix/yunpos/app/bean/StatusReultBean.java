package com.jsqix.yunpos.app.bean;

import java.io.Serializable;

/**
 * Created by dq on 2016/4/12.
 */
public class StatusReultBean extends BaseBean {

    /**
     * ID : e3aef03da0e6426fa39b23d1a2034691
     * CmdType : 1
     * CmdStatus : 等待交易标识
     * CmdUser : 齐顺测试账号
     * CmdPass : 72,0,24,0,27,0,26,0,29,0,28,0,31,0
     * CmdProgress : 10
     * CmdTimeCreate : 1460443837000
     * CmdTimeRun : 1460443838897
     * CmdTimeCompleted : null
     * OrderAmount : 0.radio_mid_rect
     * OrderNotes :
     * OrderPhone : 18662539527
     * OrderPassword : null
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity implements Serializable{
        private String ID;
        private int CmdType;
        private String CmdStatus;
        private String CmdUser;
        private String CmdPass;
        private int CmdProgress;
        private long CmdTimeCreate;
        private long CmdTimeRun;
        private Object CmdTimeCompleted;
        private double OrderAmount;
        private String OrderNotes;
        private String OrderPhone;
        private String OrderPassword;
        private String orderId;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getCmdType() {
            return CmdType;
        }

        public void setCmdType(int CmdType) {
            this.CmdType = CmdType;
        }

        public String getCmdStatus() {
            return CmdStatus;
        }

        public void setCmdStatus(String CmdStatus) {
            this.CmdStatus = CmdStatus;
        }

        public String getCmdUser() {
            return CmdUser;
        }

        public void setCmdUser(String CmdUser) {
            this.CmdUser = CmdUser;
        }

        public String getCmdPass() {
            return CmdPass;
        }

        public void setCmdPass(String CmdPass) {
            this.CmdPass = CmdPass;
        }

        public int getCmdProgress() {
            return CmdProgress;
        }

        public void setCmdProgress(int CmdProgress) {
            this.CmdProgress = CmdProgress;
        }

        public long getCmdTimeCreate() {
            return CmdTimeCreate;
        }

        public void setCmdTimeCreate(long CmdTimeCreate) {
            this.CmdTimeCreate = CmdTimeCreate;
        }

        public long getCmdTimeRun() {
            return CmdTimeRun;
        }

        public void setCmdTimeRun(long CmdTimeRun) {
            this.CmdTimeRun = CmdTimeRun;
        }

        public Object getCmdTimeCompleted() {
            return CmdTimeCompleted;
        }

        public void setCmdTimeCompleted(Object CmdTimeCompleted) {
            this.CmdTimeCompleted = CmdTimeCompleted;
        }

        public double getOrderAmount() {
            return OrderAmount;
        }

        public void setOrderAmount(double OrderAmount) {
            this.OrderAmount = OrderAmount;
        }

        public String getOrderNotes() {
            return OrderNotes;
        }

        public void setOrderNotes(String OrderNotes) {
            this.OrderNotes = OrderNotes;
        }

        public String getOrderPhone() {
            return OrderPhone;
        }

        public void setOrderPhone(String OrderPhone) {
            this.OrderPhone = OrderPhone;
        }

        public String getOrderPassword() {
            return OrderPassword;
        }

        public void setOrderPassword(String OrderPassword) {
            this.OrderPassword = OrderPassword;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
