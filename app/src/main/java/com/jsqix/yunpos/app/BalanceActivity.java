package com.jsqix.yunpos.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.HttpGet;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.BaseBean;
import com.jsqix.yunpos.app.bean.StatusReultBean;
import com.jsqix.yunpos.app.bean.SubResultBean;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_balance)
public class BalanceActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.editTextPhone)
    private EditText editTextPhone;
    //定时器
    private Timer timer;
    private TimerTask task;
    final static int TIME = 5 * 1000;

    //任务
    String taskId = "";

    final static int ORDER = 0, CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        titleBar.showLeft(false).setTitle("查询余额");
    }

    @Event(R.id.buttonSubmit)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSubmit:
                String phone = editTextPhone.getText().toString();
                if (CommUtils.isEmpty(phone)) {
                    Utils.makeToast(this, "请输入手机号");
                } else if (CommUtils.notPhone(phone)) {
                    Utils.makeToast(this, "手机号不正确");
                } else {
//                    SubmitCmd(phone);
                    queryBalance(phone);
                }
                break;
        }
    }

    private void queryBalance(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("phoneNo", phone);
        HttpGet get = new HttpGet(this, map, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        get.execute("getBalance");
    }

    private void SubmitCmd(String phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("cmdType", 0);//0余额查询，1收单
        map.put("order_source", UAD.ANDROID);
        if (!CommUtils.isEmpty(aCache.getAsString(UAD.LOCATE_LONGITUDE))) {
            map.put("longitude", aCache.getAsString(UAD.LOCATE_LONGITUDE));
        }
        if (!CommUtils.isEmpty(aCache.getAsString(UAD.LOCATE_LATITUDE))) {
            map.put("latitude", aCache.getAsString(UAD.LOCATE_LATITUDE));
        }
        if (!CommUtils.isEmpty(aCache.getAsString(UAD.LOCATE__ADDRESS))) {
            map.put("address", aCache.getAsString(UAD.LOCATE__ADDRESS));
        }
        map.put("orderAmount", "");
        map.put("orderNotes", "");
        map.put("orderPhone", phone);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("sCmd");
        post.setResultCode(ORDER);
    }

    // 开始
    private void start(final int what) {
        if (timer == null) {
            timer = new Timer();
        }
        task = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(what);
            }
        };
        if (timer != null && task != null) {
            timer.schedule(task, 0, TIME);
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", taskId);
            HttpPost post = new HttpPost(BalanceActivity.this, BalanceActivity.this, map) {
                @Override
                public void onPreExecute() {
                    loading.show();
                }
            };
            post.execute("qCmd");
            post.setResultCode(msg.what);
        }
    };

    // 停止
    private void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }

    }

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            switch (resultCode) {
                case ORDER:
                    SubResultBean bean = new Gson().fromJson(result, SubResultBean.class);
                    if (bean.getCode().equals("000")) {
                        taskId = bean.getObj().getID();
                        start(CODE);
                    } else {
                        Utils.makeToast(this, bean.getMsg());
                        loading.dismiss();
                    }
                    break;
                case CODE:
                    StatusReultBean stBean = new Gson().fromJson(result, StatusReultBean.class);
                    if (stBean.getCode().equals("000")) {
                        int status = CommUtils.toInt(stBean.getObj().getCmdProgress());
                        if (status >= 0) {
                            if (status == 100) {
                                loading.dismiss();
                                Utils.makeToast(this, "短息发送成功");
                                stop();
                            }
                        } else {
                            loading.dismiss();
                            Utils.makeToast(this, stBean.getObj().getCmdStatus());
                            stop();
                        }
                    } else {
                        Utils.makeToast(this, stBean.getMsg());
                        loading.dismiss();
                        stop();
                    }
                    break;
            }
        } catch (Exception e) {
            if (StringUtils.isNull(result)) {
                Utils.makeToast(this, "链接服务器异常");
            } else {
                Utils.makeToast(this, "json数据异常");
            }
            stop();
            loading.dismiss();
        }
    }

    @Override
    public void getCallback(int resultCode, String result) {
        super.getCallback(resultCode, result);
        try {
            loading.dismiss();
            BaseBean bean = new Gson().fromJson(result, BaseBean.class);
            showError(bean.getMsg());
        } catch (Exception e) {
            if (StringUtils.isNull(result)) {
                Utils.makeToast(this, "链接服务器异常");
            } else {
                Utils.makeToast(this, "json数据异常");
            }
        }
    }
}
