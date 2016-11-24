package com.jsqix.yunpos.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.HttpGet;
import com.jsqix.yunpos.app.api.hb.HBDownLoad;
import com.jsqix.yunpos.app.api.hb.HBGet;
import com.jsqix.yunpos.app.api.hb.HBPost;
import com.jsqix.yunpos.app.api.hb.HBUpload;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.CodeOrderBean;
import com.jsqix.yunpos.app.bean.hb.DamaResult;
import com.jsqix.yunpos.app.bean.hb.RandomResult;
import com.jsqix.yunpos.app.bean.hb.SmsCodeResult;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.utils.hb.DataUtils;
import com.jsqix.yunpos.app.utils.hb.ScriptUtil;
import com.jsqix.yunpos.app.view.ConfirmDialog;
import com.jsqix.yunpos.app.view.TitleBar;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.jsqix.yunpos.app.utils.hb.DataUtils.subStart;

@ContentView(R.layout.activity_coupon_pay)
public class CouponPayActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar title_bar;
    @ViewInject(R.id.order_money)
    private TextView order_money;
    @ViewInject(R.id.pro_name)
    private TextView pro_name;
    @ViewInject(R.id.hb_phone)
    private EditText hb_phone;
    @ViewInject(R.id.hb_psw)
    private EditText hb_psw;
    @ViewInject(R.id.hb_sms)
    private EditText hb_sms;
    @ViewInject(R.id.send_sms)
    private Button send_sms;
    // 计时器
    @ViewInject(R.id.chronometer)
    private Chronometer chronometer;
    private int time = 60;

    final static int GET_PAY_URL = 0x0001, PAY_IMAGE_CODE = 0x0002, GET_PAY_RANDOM = 0x0003,
            CODE_ORDER = 0x0004;
    final static int POST_PAY_CODE = 0xaaaa, POST_PAY_LOGIN = 0xaaab, POST_SMS_CODE = 0xaaac,
            POST_HB_PAY = 0xaaad;

    private String orderMoney = "";//订单金额
    private String merchName = "";// 商户名称
    private String prodName = "";// 商品名称
    private String merchOrder = "";// 订单编号

    private String hbPhone = "";//和包登录手机号
    private String hbPsw = "";//和包支付密码
    private String img_code = "";//图形验证码
    private String token = "";//支付token
    private String pay_no = "";//支付编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        analysisHtml(getIntent().getStringExtra(UAD.LOAD_URL));
    }

    private void initView() {
        title_bar.setLeftBackground(R.mipmap.ic_back).setTitle("订单支付");
        title_bar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBackPressDialog();
            }
        });
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (time <= 1) {
                    stopChronometerTick();
                    return;
                }

                time--;
                send_sms.setText(time + "s");

            }

        });
    }

    //开始倒计时
    private void startChronometerTick() {
        time = 60;
        chronometer.start();
        send_sms.setEnabled(false);
    }

    //停止倒计时
    private void stopChronometerTick() {
        chronometer.stop();
        send_sms.setEnabled(true);
        send_sms.setText("获取验证码");
    }

    @Event(value = {R.id.send_sms, R.id.btnSubmit})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.send_sms:
                hbPhone = CommUtils.textToString(hb_phone);
                hbPsw = CommUtils.textToString(hb_psw);
                if (StringUtils.isEmpty(hbPhone)) {
                    Utils.makeToast(this, "请输入和包登录手机账户");
                } else if (StringUtils.notPhone(hbPhone)) {
                    Utils.makeToast(this, "手机号码格式不正确");
                } else if (StringUtils.isEmpty(hbPsw)) {
                    Utils.makeToast(this, "请输入6位支付密码");
                } else {
                    getPayImageCode();
                }
                break;
            case R.id.btnSubmit:
                String hbSMS = CommUtils.textToString(hb_sms);
                if (StringUtils.isEmpty(token)) {
                    Utils.makeToast(this, "请先获取验证码");
                } else if (StringUtils.isEmpty(hbSMS)) {
                    Utils.makeToast(this, "请输入短信验证码");
                } else {
                    hbPay(token, hbSMS);
                }
                break;
        }
    }

    /**
     * 解析支付页面
     *
     * @param url
     */

    private void analysisHtml(String url) {
        HBGet get = new HBGet(this, null, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        get.execute(url);
        get.setResultCode(GET_PAY_URL);
    }

    /**
     * 获取支付图形验证码
     */
    private void getPayImageCode() {
        HBDownLoad down = new HBDownLoad(this, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        down.execute("https://ipos.10086.cn/wap/wy_supply_tp.xhtml?t=0.43733092114829186");
        down.setResultCode(PAY_IMAGE_CODE);
    }

    /**
     * 联众打码平台
     *
     * @param imagePath 图片路径
     */
    private void codelyDama(String imagePath) {
        Map<String, String> paras = new HashMap<>();
        paras.put("user_name", "a1965772028");
        paras.put("user_pw", "abc1234560");
        paras.put("yzm_minlen", "4");
        paras.put("yzm_maxlen", "4");
        paras.put("yzmtype_mark", "16");
        paras.put("zztool_token", "ce025df8a11b29def09091cb7257c88c");
        paras.put("upload", imagePath);
        HBUpload post = new HBUpload(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        post.execute("http://bbb4.hyslt.com/api.php?mod=php&act=upload");
        post.setResultCode(POST_PAY_CODE);
    }

    /**
     * 获取支付加密动态码
     */
    private void getPayPwdRandom() {
        HBGet get = new HBGet(this, null, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute("https://ipos.10086.cn/wap/ipospay_pwdrandom.xhtml?viewCode=json&type=pay&r=" + Math.random());
        get.setResultCode(GET_PAY_RANDOM);
    }

    /**
     * 支付登录请求
     */
    private void hbPayLogin(String hb_phone, String pswInfo, String img_code) {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("USR_ID", hb_phone);
        paras.put("pwd", pswInfo);
        paras.put("VER_CD", img_code);
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        post.execute("https://ipos.10086.cn/wap/new_xf_zhdl.xhtml");
        post.setResultCode(POST_PAY_LOGIN);
    }

    /**
     * 发送支付验证码
     *
     * @param token
     */
    private void hbPaySMS(String token) {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("token", token);
        paras.put("CASH_PAY", "0");
        paras.put("BON_PAY", "1");
        paras.put("AGR_NO", "");
        paras.put("OP", "0");
        paras.put("CAP_CRD_NO", "");
        paras.put("PWD", "");
        paras.put("VER_CD", "");
        paras.put("PAY_CAP_MOD", "1");
        paras.put("USR_NOPWD_FLG", "");
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        post.execute("https://ipos.10086.cn/wap/new_yezf_hqyzm.xhtml?viewCode=json");
        post.setResultCode(POST_SMS_CODE);
    }

    /**
     * 和包支付
     *
     * @param token
     * @param sms_code
     */
    private void hbPay(String token, String sms_code) {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("token", token);
        paras.put("CASH_PAY", "0");
        paras.put("BON_PAY", "1");
        paras.put("AGR_NO", "");
        paras.put("OP", "1");
        paras.put("CAP_CRD_NO", "");
        paras.put("PWD", "");
        paras.put("VER_CD", sms_code);
        paras.put("PAY_CAP_MOD", "1");
        paras.put("USR_NOPWD_FLG", "0");
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("https://ipos.10086.cn/wap/new_yezf_qrzf_1.xhtml");
        post.setResultCode(POST_HB_PAY);
    }

    //订单支付 下单
    private void codeOrder() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("orderId", pay_no);
        map.put("orderSource", UAD.ANDROID);
        map.put("orderAmount", orderMoney);
        map.put("orderPhone", hbPhone);
        map.put("orderNotes", prodName);
        if (!CommUtils.isEmpty(aCache.getAsString(UAD.LOCATE_LONGITUDE))) {
            map.put("longitude", aCache.getAsString(UAD.LOCATE_LONGITUDE));
        }
        if (!CommUtils.isEmpty(aCache.getAsString(UAD.LOCATE_LATITUDE))) {
            map.put("latitude", aCache.getAsString(UAD.LOCATE_LATITUDE));
        }
        if (!CommUtils.isEmpty(aCache.getAsString(UAD.LOCATE__ADDRESS))) {
            map.put("address", aCache.getAsString(UAD.LOCATE__ADDRESS));
        }
        HttpGet get = new HttpGet(this, map, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(CODE_ORDER);
        get.execute("preOrderDXT");
    }


    @Override
    public void getCallback(int resultCode, String result) {
        super.getCallback(resultCode, result);
        if (resultCode == GET_PAY_URL) {//加载支付页面的结果
            analysisResult(result);
        } else if (resultCode == PAY_IMAGE_CODE) {//请求图形验证码的结果
            codelyDama(result);
        } else if (resultCode == GET_PAY_RANDOM) {//获取加密动态验证码的结果
            payRandomResult(result);
        } else if (resultCode == CODE_ORDER) {
            loading.dismiss();
            CodeOrderBean orderBean = new Gson().fromJson(result, CodeOrderBean.class);
            if ("000".equals(orderBean.getCode())) {
                Utils.makeToast(this, "支付成功");
                startActivity(new Intent(CouponPayActivity.this, PayResultActivity.class).putExtra("orderId", orderBean.getObj().getOrderId()));
            } else {
                Utils.makeToast(this, orderBean.getMsg());
            }
        }
    }


    /**
     * 分析支付信息
     *
     * @param result
     */
    private void analysisResult(String result) {
        loading.dismiss();

        String orderMoneyStr = subStart(result,
                "<p class=\"fr f36 mr34\"><span class=\"c_orange f72\">");
        orderMoney = DataUtils.subEnd(orderMoneyStr, "</span>");
        String merchNameStr = subStart(result,
                "<span class=\"c_black\">商户名称：");
        merchNameStr = subStart(merchNameStr,
                "</span><span class=\"c_grey2\">");
        merchName = DataUtils.subEnd(merchNameStr, "</span>");

        String prodNameStr = subStart(result,
                "<span class=\"c_black\">商品名称：");
        prodNameStr = subStart(prodNameStr,
                "</span><span class=\"c_grey2\">");
        prodName = DataUtils.subEnd(prodNameStr, "</span>");

        String merchOrderStr = subStart(result,
                "<span class=\"c_black\">订单编号：");
        merchOrderStr = subStart(merchOrderStr,
                "</span><span class=\"c_grey2\">");
        merchOrder = DataUtils.subEnd(merchOrderStr, "</span>");

        order_money.setText(orderMoney + "元");
        pro_name.setText(prodName);

    }

    /**
     * 支付密码随机加密动态码结果
     *
     * @param result
     */
    private void payRandomResult(String result) {
        RandomResult randomResult = new Gson().fromJson(result, RandomResult.class);
        String pswInfo = ScriptUtil.makePayPwdByRhino(hbPsw, randomResult.getRSP_RANDOMS());
        hbPayLogin(hbPhone, pswInfo, img_code);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        if (resultCode == POST_PAY_CODE) {//打码平台返回的结果
            dama2Result(result);
        } else if (resultCode == POST_PAY_LOGIN) {//支付登录的结果
            analysisOrder(result);
        } else if (resultCode == POST_SMS_CODE) {//发送短信验证码的结果
            smsCodeResult(result);
        } else if (resultCode == POST_HB_PAY) {//订单支付的结果
            payResult(result);
        }
    }


    /**
     * 打码结果
     *
     * @param result
     */
    private void dama2Result(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result") == false) {
                Utils.makeToast(this, jsonObject.getString("data"));
                loading.dismiss();
            } else {
                DamaResult damaResult = new Gson().fromJson(result, DamaResult.class);
                if (damaResult.isResult() == true) {
                    img_code = damaResult.getData().getVal();
                    getPayPwdRandom();
                }
            }
        } catch (Exception e) {
            loading.dismiss();
            Utils.makeToast(this, "打码失败");
        }
    }

    /**
     * 支付登录成功，解析订单
     *
     * @param result
     */
    private void analysisOrder(String result) {
        if (StringUtils.isEmpty(result)) {
            Utils.makeToast(this, "登录失败");
            loading.dismiss();
        } else {
            token = DataUtils.getValue(result, "name=\"token\"", "value=\"", "\"");
            if (StringUtils.isEmpty(token)) {
                String errorStr = DataUtils.subStart(result, "<span id=\"errHint\">");
                errorStr = DataUtils.subEnd(errorStr, "</span>");
                if (errorStr.contains("验证码")) {
                    Utils.makeToast(this, "网络异常，请重新获取验证码");
                } else {
                    Utils.makeToast(this, errorStr);
                }
                loading.dismiss();
            } else {
                String TXN_AMT = DataUtils.getValue(result, "name=\"TXN_AMT\"", "value=\"", "\"");//订单金额  单位分
                String CNY_AC_BAL = DataUtils.getValue(result, "name=\"CNY_AC_BAL\"", "value=\"", "\"");//账户余额 单位分
                String BON_TOT_AMT = DataUtils.getValue(result, "name=\"BON_TOT_AMT\"", "value=\"", "\"");//可使用电子券金额 单位分
                String SHOW_SALE_AMT = DataUtils.getValue(result, "name=\"SHOW_SALE_AMT\"", "value=\"", "\"");//优惠金额
                if (DataUtils.I$(CNY_AC_BAL, 0) + DataUtils.I$(BON_TOT_AMT, 0) < DataUtils.I$(TXN_AMT, 0) - DataUtils.I$(SHOW_SALE_AMT, 0)) {
                    Utils.makeToast(this, "账户余额不足");
                    loading.dismiss();
                } else {
                    hbPaySMS(token);
                }
            }
        }
    }

    /**
     * 发送短信结果
     *
     * @param result
     */
    private void smsCodeResult(String result) {
        loading.dismiss();
        SmsCodeResult codeResult = new Gson().fromJson(result, SmsCodeResult.class);
        if (codeResult != null && codeResult.getRSP_FLG().equals("1")) {
            Utils.makeToast(this, "短信发送成功");
            startChronometerTick();
        } else {
            Utils.makeToast(this, "短信发送失败，请重新提交");
        }
    }

    /**
     * 支付结果
     *
     * @param result
     */
    private void payResult(String result) {
        if (StringUtils.isEmpty(result)) {
            Utils.makeToast(this, "支付失败，未获取到支付结果");
            loading.dismiss();
        } else if (result.contains("支付失败")) {
            String errT = DataUtils.getValue(result, "<span class=\"ml10_vm\">", "</span>");
            String errM = DataUtils.getValue(result, "<p class=\"pd15_b f32 c_em\">", "</p>");
            Utils.makeToast(this, errT + "," + errM);
            loading.dismiss();

        } else if (result.contains("支付成功") && result.contains("payNo")) {
            pay_no = DataUtils.getValue(result, "name=\"payNo\"", "value=\"", "\"");
            codeOrder();
        } else {
            Utils.makeToast(this, "支付失败,未知错误");
            loading.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showBackPressDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showBackPressDialog() {
        final ConfirmDialog dialog = new ConfirmDialog(this);
        dialog.setPositiveButton("确定");
        dialog.setNegativeButton("取消");
        dialog.setMessage("确定退出支付？");
        dialog.setPositiveClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.setNegativeClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
