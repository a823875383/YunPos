package com.jsqix.yunpos.app.bean;

import java.io.Serializable;

/**
 * Created by dq on 2016/4/21.
 */
public class UpdateBean extends BaseBean{


    private ObjEntity obj;  /**
     * app_type : 1001
     * down_url : sdfsdf
     * app_version : V1.0.0.0
     * forced : 1
     * addtime : 1461220407000
     * remark : 备注
     */


    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity implements Serializable{
        private String app_type;
        private String down_url;
        private String app_version;
        private int forced;
        private long addtime;
        private String remark;

        public String getApp_type() {
            return app_type;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public String getDown_url() {
            return down_url;
        }

        public void setDown_url(String down_url) {
            this.down_url = down_url;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public int getForced() {
            return forced;
        }

        public void setForced(int forced) {
            this.forced = forced;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
