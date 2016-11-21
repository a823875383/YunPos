package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/4/12.
 */
public class ChangeResult extends BaseBean {

    /**
     * code : 000
     * success : false
     * msg : 查询成功
     * obj : {"MSG":"修改成功","STS":"000"}
     * url : null
     */

    /**
     * MSG : 修改成功
     * STS : 000
     */

    private ObjEntity obj;


    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }


    public static class ObjEntity {
        private String MSG;
        private String STS;

        public String getMSG() {
            return MSG;
        }

        public void setMSG(String MSG) {
            this.MSG = MSG;
        }

        public String getSTS() {
            return STS;
        }

        public void setSTS(String STS) {
            this.STS = STS;
        }
    }
}
