package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/4/12.
 */
public class OrderInfor extends BaseBean{

    /**
     * id : 39f08ac4b7bf419aaf775b9e02878889
     * user_id : 1
     * phone : 18662539527
     * amount : 0.radio_mid_rect
     * remark :
     * remark1 : null
     * status : 0
     * ext_orde_no : e788e34a6952481e8b113cf669f07693
     * cmdprogress : 10
     * cmdstatus : 等待交易标识
     * addtime : 1460447035000
     * upttime : null
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String id;
        private int user_id;
        private String phone;
        private double amount;
        private String remark;
        private Object remark1;
        private int status;
        private String ext_orde_no;
        private String cmdprogress;
        private String cmdstatus;
        private long addtime;
        private Object upttime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getRemark1() {
            return remark1;
        }

        public void setRemark1(Object remark1) {
            this.remark1 = remark1;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getExt_orde_no() {
            return ext_orde_no;
        }

        public void setExt_orde_no(String ext_orde_no) {
            this.ext_orde_no = ext_orde_no;
        }

        public String getCmdprogress() {
            return cmdprogress;
        }

        public void setCmdprogress(String cmdprogress) {
            this.cmdprogress = cmdprogress;
        }

        public String getCmdstatus() {
            return cmdstatus;
        }

        public void setCmdstatus(String cmdstatus) {
            this.cmdstatus = cmdstatus;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public Object getUpttime() {
            return upttime;
        }

        public void setUpttime(Object upttime) {
            this.upttime = upttime;
        }
    }
}
