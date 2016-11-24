package com.jsqix.yunpos.app;

import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.CompoundButton;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.api.face.InterfaceHttpPost;
import com.jsqix.yunpos.app.base.MyApplication;
import com.jsqix.yunpos.app.bean.UpdateBean;
import com.jsqix.yunpos.app.utils.DownApkUtils;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends TabActivity implements InterfaceHttpPost {
    public static final String TAB_HOME = "TAB_HOME";
    public static final String TAB_BALANCE = "TAB_BALANCE";
    public static final String TAB_ORDER = "TAB_ORDER";
    public static final String TAB_SET = "TAB_SET";
    private TabHost mTabHost;
    /* 检查更新 */
    private UpdateBean updateBean;
    DownApkUtils aDownloadApk = new DownApkUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        initView();
        checkUpdate();
    }

    private void initView() {
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME)
                .setContent(new Intent(this, CashActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_BALANCE)
                .setIndicator(TAB_BALANCE).setContent(new Intent(this, BalanceActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_ORDER)
                .setIndicator(TAB_ORDER).setContent(new Intent(this, OrderActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SET).setIndicator(TAB_SET)
                .setContent(new Intent(this, SetActivity.class)));
        mTabHost.setCurrentTabByTag(TAB_HOME);
    }

    @Event(value = {R.id.radio_button0, R.id.radio_button1, R.id.radio_button2, R.id.radio_button3}, type = CompoundButton.OnCheckedChangeListener.class)
    private void changeMenu(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_button0:
                    this.mTabHost.setCurrentTabByTag(TAB_HOME);
                    break;
                case R.id.radio_button1:
                    this.mTabHost.setCurrentTabByTag(TAB_BALANCE);
                    break;
                case R.id.radio_button2:
                    this.mTabHost.setCurrentTabByTag(TAB_ORDER);
                    break;
                case R.id.radio_button3:
                    this.mTabHost.setCurrentTabByTag(TAB_SET);
                    break;
            }
        }

    }

    private void showUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("软件版本更新");
        builder.setMessage("");
        builder.setPositiveButton("立即更新",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        aDownloadApk.showDownloadDialog(updateBean);

                    }
                });
        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (updateBean.getObj().getForced() == 1) {
                            MyApplication.exitApp();
                        }


                    }

                });
        builder.setCancelable(false);
        builder.show();
    }

    private void checkUpdate() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", 1001);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
            }
        };
        post.execute("checkUpdate");
    }

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            updateBean = new Gson().fromJson(result, UpdateBean.class);
            if (updateBean.getCode().equals("000")) {
                if (!updateBean.getObj().getApp_version().equalsIgnoreCase("V" + aDownloadApk.getCurrentVersion())) {
                    showUpdate();
                }
            }
        } catch (Exception e) {

        }
    }
}
