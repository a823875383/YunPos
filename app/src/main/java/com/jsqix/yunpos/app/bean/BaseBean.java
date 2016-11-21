package com.jsqix.yunpos.app.bean;

import java.io.Serializable;

/**
 * Created by dq on 2016/4/12.
 */
public class BaseBean implements Serializable{
    private String code;
    private boolean success;
    private String msg;
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
