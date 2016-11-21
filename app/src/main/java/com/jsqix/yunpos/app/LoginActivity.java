package com.jsqix.yunpos.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.ApiClient;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.api.Md5;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.base.MyApplication;
import com.jsqix.yunpos.app.bean.LoginResult;
import com.jsqix.yunpos.app.bean.UpdateBean;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.DownApkUtils;
import com.jsqix.yunpos.app.utils.MapVaules;
import com.jsqix.yunpos.app.utils.UAD;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseAty {
    @ViewInject(R.id.editTextMblNo)
    private EditText editTextMblNo;
    @ViewInject(R.id.editTextPasswords)
    private EditText editTextPasswords;
    @ViewInject(R.id.scrollView)
    private ScrollView scrollView;
    @ViewInject(R.id.checkBoxAutoLogin)
    private CheckBox checkBoxAutoLogin;

    private String mblNo;
    private String passwords;
    /* 检查更新 */
    private UpdateBean updateBean;
    DownApkUtils aDownloadApk = new DownApkUtils(this);
    final static int REQUEST_LOGIN = 1, REQUEST_CHECK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        checkUpdate();
    }


    private void initView() {
        mblNo = aCache.getAsString("editTextMblNo");
        passwords = aCache.getAsString("editTextPasswords");
        if (!CommUtils.isEmpty(mblNo)) {
            editTextMblNo.setText(mblNo);
            editTextMblNo.setSelection(mblNo.length());
        }
        if (!CommUtils.isEmpty(passwords)) {
            editTextPasswords.setText(passwords);
            editTextPasswords.setSelection(passwords.length());
        }
//        if (!StringUtils.isEmpty(mblNo) && !StringUtils.isEmpty(passwords)) {
//            login();
//        }
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
        post.setResultCode(REQUEST_CHECK);
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

    @Event(value = {R.id.buttonLogin, R.id.editTextMblNo})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTextMblNo:
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 200);
                break;
            case R.id.buttonLogin:
                mblNo = editTextMblNo.getText().toString().trim();
                passwords = editTextPasswords.getText().toString().trim();
                if (CommUtils.isEmpty(mblNo)) {
                    Utils.makeToast(this, "请输入商户ID或手机号");
                } else if (CommUtils.isEmpty(passwords)) {
                    Utils.makeToast(this, "请输入密码");
                } else {
                    login();
                }
                break;
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
                loading.show();
            }
        };
        post.execute("userLogin");
        post.setResultCode(REQUEST_LOGIN);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return MyApplication.getInstance().closeAppByBack(keyCode, event);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            loading.dismiss();
            if (resultCode == REQUEST_CHECK) {
                updateBean = new Gson().fromJson(result, UpdateBean.class);
                if (updateBean.getCode().equals("000")) {
                    if (!updateBean.getObj().getApp_version().equalsIgnoreCase("V" + aDownloadApk.getCurrentVersion())) {
                        showUpdate();
                    }
                }
            } else if (resultCode == REQUEST_LOGIN) {
                LoginResult bean = new Gson().fromJson(result, LoginResult.class);
                Utils.makeToast(this, bean.getMsg());
                if (bean.getCode().equals("000")) {
                    if (checkBoxAutoLogin.isChecked()) {
                        aCache.put("editTextMblNo", editTextMblNo.getText().toString().trim());
                        aCache.put("editTextPasswords", editTextPasswords.getText().toString().trim());
                    }
                    aCache.put(UAD.UID, bean.getObj().getId() + "");
                    if (bean.getObj().getIsperfect() == 0) {
                        startActivity(new Intent(this, PerfectInforActivity.class).putExtra(UAD.RESULT, false));
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                    finish();
                }
            }
        } catch (Exception e) {
            if (CommUtils.isNull(result)) {
                Utils.makeToast(this, "链接服务器异常");
            }
        }
    }
}
