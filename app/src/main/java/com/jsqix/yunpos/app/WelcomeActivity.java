package com.jsqix.yunpos.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jsqix.utils.LogWriter;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.ApiClient;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.api.Md5;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.LoginResult;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.JPushUtil;
import com.jsqix.yunpos.app.utils.MapVaules;
import com.jsqix.yunpos.app.utils.UAD;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends BaseAty {
    @ViewInject(R.id.layoutLogin)
    private LinearLayout layoutLogin;
    private String mblNo;
    private String passwords;

    private static final int MSG_SET_ALIAS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVeiw();
    }

    private void initVeiw() {
        mblNo = aCache.getAsString("editTextMblNo");
        passwords = aCache.getAsString("editTextPasswords");
        setAlias();
        if (!CommUtils.isEmpty(mblNo) && !CommUtils.isEmpty(passwords)) {
            login();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
            }, 2000);
        }
    }

    private void login() {
        MapVaules map = new MapVaules();
        map.put("acct", mblNo);
        map.put("pwd", Md5.getMD5(passwords + ApiClient.SECRET_KEY, ApiClient.UTF_8));
        map.put("log_source", UAD.ANDROID);
        map.put("phone_name", Build.MODEL);
        map.put("system_version", "android " + Build.VERSION.RELEASE);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        map.put("emei", tm.getDeviceId());
        map.put("operator", tm.getNetworkOperatorName());
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            if (ni.getTypeName().equals("MOBILE")) {
                map.put("network_standard", ni.getSubtypeName() + " " + ni.getExtraInfo());
            } else {
                map.put("network_standard", ni.getTypeName());
            }
        }
        map.put("longitude", aCache.getAsString(UAD.LOCATE_LONGITUDE));
        map.put("latitude", aCache.getAsString(UAD.LOCATE_LATITUDE));
        map.put("address", aCache.getAsString(UAD.LOCATE__ADDRESS));
        HttpPost post = new HttpPost(this, this, map.getMap()) {
            @Override
            public void onPreExecute() {
                layoutLogin.setVisibility(View.VISIBLE);
            }
        };
        post.execute("userLogin");
    }

    /**
     * 设置JPush别名
     */
    private void setAlias() {
        String alias = JPushUtil.getUUID(this, "");
        if (TextUtils.isEmpty(alias)) {
            LogWriter.i(JPushUtil.TAG, "alias empty");
            return;
        }
        if (!JPushUtil.isValidTagAndAlias(alias)) {
            LogWriter.i(JPushUtil.TAG, "alias not valid");
            return;
        }
        // 调用JPush API设置Alias
        Handler.sendMessage(Handler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final Handler Handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case MSG_SET_ALIAS:
                    LogWriter.d(JPushUtil.TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj, null, mAliasCallback);
                    break;

                default:
                    LogWriter.i(JPushUtil.TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    aCache.put(UAD.TOKEN, JPushUtil.getUUID(getApplicationContext(), ""));
                    LogWriter.i(JPushUtil.TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    LogWriter.i(JPushUtil.TAG, logs);
                    if (JPushUtil.isConnected(getApplicationContext())) {
                        Handler.sendMessageDelayed(
                                Handler.obtainMessage(MSG_SET_ALIAS, alias),
                                1000 * 60);
                    } else {
                        LogWriter.i(JPushUtil.TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    LogWriter.e(JPushUtil.TAG, logs);
            }
        }

    };

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            LoginResult bean = new Gson().fromJson(result, LoginResult.class);
            Utils.makeToast(this, bean.getMsg());
            if (bean.getCode().equals("000")) {
                aCache.put(UAD.UID, bean.getObj().getId() + "");
                if (bean.getObj().getIsperfect() == 0) {
                    startActivity(new Intent(this, PerfectInforActivity.class).putExtra(UAD.RESULT, false));
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        } catch (Exception e) {
            if (CommUtils.isNull(result)) {
                Utils.makeToast(this, "链接服务器异常");
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    ;

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}
