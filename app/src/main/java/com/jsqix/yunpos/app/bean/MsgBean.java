package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/7/18.
 */
public class MsgBean extends BaseBean{

    /**
     * content : 我司将于2016-07-25至2016-07-30期间更新APP版本
     * title : 系统更新
     */

    private ObjEntity obj;

    public ObjEntity getObj() {
        return obj;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public static class ObjEntity {
        private String content;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
