package com.jsqix.yunpos.app;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.view.ConfirmDialog;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.app_version)
    private TextView appVersion;

    private ConfirmDialog callDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initCall();
    }

    private void initCall() {
        callDialog = new ConfirmDialog(this);
        callDialog.setTitle("拨打电话");
        callDialog.setMessage("180-1317-5337");
        callDialog.setPositiveButton("确定");
        callDialog.setPositiveClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + callDialog.getMessage().replace("-", "")));
                startActivity(intent);
            }
        });
        callDialog.setNegativeButton("取消");
        callDialog.setNegativeClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog.dismiss();
            }
        });
    }


    private void initView() {
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle("关于我们");
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (info != null) {
                appVersion.setText("V" + info.versionName);
            }
        } catch (Exception e) {

        }
    }

    @Event(value = {R.id.together, R.id.support})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.together:
                callDialog.setMessage("181-1255-7771");
                callDialog.show();
                break;
            case R.id.support:
                callDialog.setMessage("180-1317-5337");
                callDialog.show();
                break;
        }

    }
}
