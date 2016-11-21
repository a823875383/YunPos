package com.jsqix.yunpos.app.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.jsqix.utils.BaseActivity;
import com.jsqix.utils.LogWriter;
import com.jsqix.yunpos.app.R;
import com.jsqix.yunpos.app.api.HttpGet;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.view.ConfirmDialog;
import com.jsqix.yunpos.app.view.CustomeDialog;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dq on 2016/3/15.
 */
public class BaseAty extends BaseActivity implements HttpPost.InterfaceHttpPost, HttpGet.InterfaceHttpGet {
    public CustomeDialog loading;
    public ConfirmDialog errorDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initDialog();
    }

    private void initDialog() {
        loading = new CustomeDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_loading, null);
        loading.setView(view);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        errorDailog = new ConfirmDialog(this);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        LogWriter.d(result);
    }

    public void showError(String msg) {
        errorDailog.setMessage(msg);
        errorDailog.setPositiveButton("知道了");
        errorDailog.setPositiveClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDailog.dismiss();
            }
        });
        errorDailog.setNegativeClick(null);
        errorDailog.show();
    }

    @Override
    public void getCallback(int resultCode, String result) {
        LogWriter.d(result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
