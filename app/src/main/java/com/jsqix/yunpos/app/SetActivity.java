package com.jsqix.yunpos.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.jsqix.utils.FrameApplication;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_set)
public class SetActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        titleBar.showLeft(false).setTitle("设置");
    }

    @Event(value = {R.id.accountInformation, R.id.passWordChange, R.id.aboutUs, R.id.loginOut})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.accountInformation:
                startActivity(new Intent(this, PerfectInforActivity.class).putExtra(UAD.TITLE, "编辑资料").putExtra(UAD.RESULT, true));
                break;
            case R.id.passWordChange:
                startActivity(new Intent(this, ChangePswActivity.class));
                break;
            case R.id.aboutUs:
                startActivity(new Intent(this, AboutActivity.class));
//                Uri uri = Uri.parse("market://details?id="+"com.jsqx.dianwouser"/*getPackageName()*/);
//                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                break;
            case R.id.loginOut:
                aCache.put("editTextPasswords", "");
                startActivity(new Intent(this, LoginActivity.class));
                UAD.NOT_NEW=false;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return FrameApplication.getInstance().closeAppByBack(KeyEvent.KEYCODE_BACK, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
