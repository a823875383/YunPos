package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/4/12.
 */
public class SubResultBean extends BaseBean{

    /**
     * ID : 14f5db685ffe46f09f0ce93f5f6f057e
     * CmdType : 1
     * CmdTimeCreate : 2016-04-12 14:17:29
     * OrderAmount : 0.radio_mid_rect
     * OrderNotes :
     * OrderPhone : 18662539527
     * cmdStatus : 0
     * CmdProgress : 0
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String ID;
        private String CmdType;
        private String CmdTimeCreate;
        private String OrderAmount;
        private String OrderNotes;
        private String OrderPhone;
        private String cmdStatus;
        private int CmdProgress;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCmdType() {
            return CmdType;
        }

        public void setCmdType(String CmdType) {
            this.CmdType = CmdType;
        }

        public String getCmdTimeCreate() {
            return CmdTimeCreate;
        }

        public void setCmdTimeCreate(String CmdTimeCreate) {
            this.CmdTimeCreate = CmdTimeCreate;
        }

        public String getOrderAmount() {
            return OrderAmount;
        }

        public void setOrderAmount(String OrderAmount) {
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

        public String getCmdStatus() {
            return cmdStatus;
        }

        public void setCmdStatus(String cmdStatus) {
            this.cmdStatus = cmdStatus;
        }

        public int getCmdProgress() {
            return CmdProgress;
        }

        public void setCmdProgress(int CmdProgress) {
            this.CmdProgress = CmdProgress;
        }
    }
}
