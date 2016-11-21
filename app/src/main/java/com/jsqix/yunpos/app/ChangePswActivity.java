package com.jsqix.yunpos.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.ApiClient;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.api.Md5;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.ChangeResult;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_change_psw)
public class ChangePswActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.oldPsw)
    private EditText oldPsw;
    @ViewInject(R.id.newPsw)
    private EditText newPsw;
    @ViewInject(R.id.comPsw)
    private EditText comPsw;

    private String oldPswTxt, newPswTxt, comPswTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle("修改密码");
    }

    @Event(R.id.btnSubmit)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                oldPswTxt = oldPsw.getText().toString().trim();
                newPswTxt = newPsw.getText().toString().trim();
                comPswTxt = comPsw.getText().toString().trim();
                if (CommUtils.isEmpty(oldPswTxt)) {
                    Utils.makeToast(this, "请输入原密码");
                } else if (CommUtils.isEmpty(newPswTxt)) {
                    Utils.makeToast(this, "请输入新密码");
                } else if (CommUtils.isEmpty(comPswTxt)) {
                    Utils.makeToast(this, "请再次输入密码");
                } else if (!newPswTxt.equals(comPswTxt)) {
                    Utils.makeToast(this, "两次密码不一致");
                } else {
                    changePsw();
                }
                break;
        }
    }

    private void changePsw() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("l_pwd", Md5.getMD5(oldPswTxt + ApiClient.SECRET_KEY, ApiClient.UTF_8));
        map.put("p_pwd", Md5.getMD5(newPswTxt + ApiClient.SECRET_KEY, ApiClient.UTF_8));
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("udtLoginPwd");

    }

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            loading.dismiss();
            ChangeResult bean = new Gson().fromJson(result, ChangeResult.class);
            Utils.makeToast(this, bean.getMsg());
            if (bean.getCode().equals("000")) {
                aCache.put("editTextPasswords", "");
                Utils.makeToast(this, "请重新登录");
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }
        } catch (Exception e) {
            if (CommUtils.isNull(result)) {
                Utils.makeToast(this, "链接服务器异常");
            }
        }
    }
}