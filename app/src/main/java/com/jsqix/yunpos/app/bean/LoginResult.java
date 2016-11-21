package com.jsqix.yunpos.app.bean;

/**
 * Created by dq on 2016/4/12.
 */
public class LoginResult extends BaseBean {

    /**
     * id : 1
     * channel_id : 1
     * user_acct : test
     * user_pwd : 0dd9f668d0705883fb303d89bea4887c
     * user_type : 3
     * pid : null
     * addtime : 1460367709000
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
        private String channel_id;
        private String user_acct;
        private String user_pwd;
        private String user_type;
        private String pid;
        private int isperfect;
        private long addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getUser_acct() {
            return user_acct;
        }

        public void setUser_acct(String user_acct) {
            this.user_acct = user_acct;
        }

        public String getUser_pwd() {
            return user_pwd;
        }

        public void setUser_pwd(String user_pwd) {
            this.user_pwd = user_pwd;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getIsperfect() {
            return isperfect;
        }

        public void setIsperfect(int isperfect) {
            this.isperfect = isperfect;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }
    }
}
