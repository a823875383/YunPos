package com.jsqix.yunpos.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.base.MyApplication;
import com.jsqix.yunpos.app.bean.AccountInformBean;
import com.jsqix.yunpos.app.bean.BaseBean;
import com.jsqix.yunpos.app.bean.CityBean;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_perfect_infor)
public class PerfectInforActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.editPayee)
    private EditText payee;
    @ViewInject(R.id.editAccount)
    private EditText account;
    @ViewInject(R.id.editBank)
    private EditText bank;

//    @ViewInject(R.id.textProvince)
//    private TextView province;
//    @ViewInject(R.id.textCity)
//    private TextView city;
//    @ViewInject(R.id.textArea)
//    private TextView area;

    @ViewInject(R.id.textRegion)
    private TextView region;
    @ViewInject(R.id.editAddress)
    private EditText address;
    @ViewInject(R.id.editTextUsrMblNo)
    private EditText mblNo;
    @ViewInject(R.id.editTextSmsVerCode)
    private EditText smsVerCode;
    @ViewInject(R.id.buttonSmsMblNo)
    private Button smsMblNo;
    // 计时器
    @ViewInject(R.id.chronometer)
    private Chronometer chronometer;
    private int time = 60;

    private boolean backHomemenu = false;
    private String title;
    private String pcode, ccdoe, acode, name;
    final static int REQUEST_SMS = 0, REQUEST_SUBT = 1, REQUEST_RES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        title = getIntent().getExtras().getString(UAD.TITLE, "完善资料");
        backHomemenu = getIntent().getExtras().getBoolean(UAD.RESULT, false);
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle(title).setRightContent("提交").setRightTextColor(Color.WHITE);
        titleBar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommUtils.isEmpty(CommUtils.textToString(payee)) || CommUtils.isEmpty(CommUtils.textToString(account)) || CommUtils.isEmpty(CommUtils.textToString(bank))
                        || /*CommUtils.isEmpty(CommUtils.textToString(province)) || CommUtils.isEmpty(CommUtils.textToString(city)) || CommUtils.isEmpty(CommUtils.textToString(area))*/
                        CommUtils.isEmpty(CommUtils.textToString(region))
                        || CommUtils.isEmpty(CommUtils.textToString(address)) || CommUtils.isEmpty(CommUtils.textToString(mblNo)) || CommUtils.isEmpty(CommUtils.textToString(smsVerCode))) {
                    Utils.makeToast(PerfectInforActivity.this, "请填写完整");
                } else {
                    buttonSubmit();
                }
            }
        });
        titleBar.showLeft(backHomemenu);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (time <= 1) {
                    stopChronometerTick();
                    return;
                }

                time--;
                smsMblNo.setText(time + "s");

            }

        });
        if (backHomemenu) {
            getData();
        } else {
            showError("请完善资料");
        }
    }

    @Event(value = {R.id.textRegion, R.id.buttonSmsMblNo})
    private void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.textRegion:
                bundle.putString(UAD.TITLE, "请选择省");
                bundle.putString(UAD.LEVEL, "1");
                bundle.putString(UAD.CID, "");
                startActivityForResult(new Intent(this, CitySelectorActivity.class).putExtras(bundle), 100);
                break;
//           case R.id.textProvince:
//                bundle.putString(UAD.TITLE, "请选择省");
//                bundle.putString(UAD.LEVEL, "1");
//                bundle.putString(UAD.CID, "");
//                startActivityForResult(new Intent(this, CitySelectorActivity.class).putExtras(bundle), 100);
//                break;
//            case R.id.textCity:
//                if (CommUtils.isEmpty(CommUtils.textToString(province))) {
//                    Utils.makeToast(this, "请先选择省");
//                } else {
//                    bundle.putString(UAD.TITLE, "请选择市");
//                    bundle.putString(UAD.LEVEL, "2");
//                    bundle.putString(UAD.CID, pcode);
//                    startActivityForResult(new Intent(this, CitySelectorActivity.class).putExtras(bundle), 101);
//                }
//                break;
//            case R.id.textArea:
//                if (CommUtils.isEmpty(CommUtils.textToString(province))) {
//                    Utils.makeToast(this, "请先选择省");
//                } else if (CommUtils.isEmpty(city.getText().toString())) {
//                    Utils.makeToast(this, "请先选择市");
//                } else {
//                    bundle.putString(UAD.TITLE, "请选择区");
//                    bundle.putString(UAD.LEVEL, "3");
//                    bundle.putString(UAD.CID, ccdoe);
//                    startActivityForResult(new Intent(this, CitySelectorActivity.class).putExtras(bundle), 102);
//                }
//                break;
            case R.id.buttonSmsMblNo:
                String phone = CommUtils.textToString(mblNo);
                if (CommUtils.isEmpty(phone)) {
                    Utils.makeToast(this, "请输入手机号");
                } else if (CommUtils.notPhone(phone)) {
                    Utils.makeToast(this, "手机号不正确");
                } else {
                    sendSmsCode(phone);
                }
                break;
            default:break;

        }
    }

    private void startChronometerTick() {
        time = 60;
        chronometer.start();
        smsMblNo.setEnabled(false);
    }

    private void stopChronometerTick() {
        chronometer.stop();
        smsMblNo.setEnabled(true);
        smsMblNo.setText("发送验证码");
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("getUserInfo");
        post.setResultCode(REQUEST_RES);
    }

    private void buttonSubmit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("user_name", CommUtils.textToString(payee));
        map.put("account_number", CommUtils.textToString(account));
        map.put("account_bank", CommUtils.textToString(bank));
        map.put("user_phone", CommUtils.textToString(mblNo));
        map.put("msgCode", CommUtils.textToString(smsVerCode));
        map.put("aid", pcode);
        map.put("bid", ccdoe);
        map.put("cid", acode);
        map.put("address", CommUtils.textToString(address));
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {

            }
        };
        post.execute("userPerfect");
        post.setResultCode(REQUEST_SUBT);
    }

    private void sendSmsCode(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("acct", phone);
        map.put("type", 1003);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("sendCode");
        post.setResultCode(REQUEST_SMS);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        try {
            loading.dismiss();
            if (resultCode == REQUEST_SMS) {
                BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                if (baseBean.getCode().equals("000")) {
                    showError("短信发送成功");
                    startChronometerTick();
                } else {
                    showError(baseBean.getMsg());
                }

            } else if (resultCode == REQUEST_SUBT) {
                BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                if (baseBean.getCode().equals("000")) {
                    if (!backHomemenu) {
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        Utils.makeToast(this, baseBean.getMsg());
                    }
                    finish();
                } else {
                    showError(baseBean.getMsg());
                }
            } else if (resultCode == REQUEST_RES) {
                AccountInformBean informBean = new Gson().fromJson(result, AccountInformBean.class);
                if (informBean.getCode().equals("000")) {
                    payee.setText(informBean.getObj().getUser_name());
                    account.setText(informBean.getObj().getAccount_number());
                    bank.setText(informBean.getObj().getAccount_bank());
//                    province.setText(informBean.getObj().getAname());
//                    city.setText(informBean.getObj().getBname());
//                    area.setText(informBean.getObj().getCname());
                    region.setText(informBean.getObj().getAname() + "-" + informBean.getObj().getBname() + "-" + informBean.getObj().getCname());
                    address.setText(informBean.getObj().getAddress());
                    mblNo.setText(informBean.getObj().getUser_phone());
                    payee.setSelection(CommUtils.textToString(payee).length());
                    pcode = informBean.getObj().getAid();
                    ccdoe = informBean.getObj().getBid();
                    acode = informBean.getObj().getCid();
                } else {
                    Utils.makeToast(this, informBean.getMsg());
                }
            }
        } catch (Exception e) {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            ArrayList<CityBean.ObjEntity> bean = null;
//            String code = "";
            if (extras != null) {
                bean = (ArrayList<CityBean.ObjEntity>) extras.getSerializable(UAD.RESULT);

            }
            if (requestCode == 100 && bean != null && bean.size() == 3) {
                CityBean.ObjEntity proviceBean = bean.get(0);
                CityBean.ObjEntity cityBean = bean.get(1);
                CityBean.ObjEntity areaBean = bean.get(2);
                pcode = proviceBean.getCode();
                ccdoe = cityBean.getCode();
                acode = areaBean.getCode();
                region.setText(proviceBean.getName() + "-" + cityBean.getName() + "-" + areaBean.getName());

            }
//            if (requestCode == 100) {
//                province.setText(name);
//                pcode = code;
//                city.setText("");
//                area.setText("");
//            } else if (requestCode == 101) {
//                city.setText(name);
//                area.setText("");
//                ccdoe = code;
//            } else if (requestCode == 102) {
//                area.setText(name);
//                acode = code;
//            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!backHomemenu) {
            return MyApplication.getInstance().closeAppByBack(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
