package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/4/12.
 */
public class UpdateResultBean extends BaseBean{

    /**
     * OrderPassword : 123456
     * ID : bb8569c82c10471dbba31a4dc792569d
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String OrderPassword;
        private String ID;

        public String getOrderPassword() {
            return OrderPassword;
        }

        public void setOrderPassword(String OrderPassword) {
            this.OrderPassword = OrderPassword;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
    }
}
