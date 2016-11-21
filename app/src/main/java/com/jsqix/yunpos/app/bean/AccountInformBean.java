package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/7/5.
 */
public class AccountInformBean extends BaseBean{

    /**
     * account_number : 6228484653494346
     * deductible : 1000.0
     * account_bank : 农行
     * user_status : 1
     * aid : 320000
     * cid : 320506
     * aname : 江苏省
     * id : 102
     * bname : 苏州市
     * user_name : 刘杰
     * rate : 5
     * address : 月亮湾支行
     * user_phone : 18662539527
     * user_acct : test_gd
     * isperfect : 1
     * bid : 320500
     * settlement_cycl : 3
     * cname : 吴中区
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String account_number;
        private double deductible;
        private String account_bank;
        private int user_status;
        private String aid;
        private String cid;
        private String aname;
        private String id;
        private String bname;
        private String user_name;
        private String rate;
        private String address;
        private String user_phone;
        private String user_acct;
        private int isperfect;
        private String bid;
        private int settlement_cycl;
        private String cname;

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public double getDeductible() {
            return deductible;
        }

        public void setDeductible(double deductible) {
            this.deductible = deductible;
        }

        public String getAccount_bank() {
            return account_bank;
        }

        public void setAccount_bank(String account_bank) {
            this.account_bank = account_bank;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getAname() {
            return aname;
        }

        public void setAname(String aname) {
            this.aname = aname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_acct() {
            return user_acct;
        }

        public void setUser_acct(String user_acct) {
            this.user_acct = user_acct;
        }

        public int getIsperfect() {
            return isperfect;
        }

        public void setIsperfect(int isperfect) {
            this.isperfect = isperfect;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public int getSettlement_cycl() {
            return settlement_cycl;
        }

        public void setSettlement_cycl(int settlement_cycl) {
            this.settlement_cycl = settlement_cycl;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }
    }
}
