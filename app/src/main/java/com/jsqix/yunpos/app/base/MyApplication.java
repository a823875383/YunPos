package com.jsqix.yunpos.app.base;

import com.jsqix.utils.FrameApplication;
import com.jsqix.yunpos.app.utils.BaiDuLocationHelper;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dq on 2016/3/15.
 */
public class MyApplication extends FrameApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        JPushInterface.init(this);
        BaiDuLocationHelper.getInstance(this).startLocation();
    }


}
