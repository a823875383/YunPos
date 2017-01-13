package com.jsqix.yunpos.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.HttpGet;
import com.jsqix.yunpos.app.api.hb.HBGet;
import com.jsqix.yunpos.app.api.hb.HBPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.CodeOrderBean;
import com.jsqix.yunpos.app.bean.hb.PlaceOrderErrorResult;
import com.jsqix.yunpos.app.bean.hb.PlaceOrderSuccessResult;
import com.jsqix.yunpos.app.bean.hb.SmsCodeResult;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.utils.hb.DataUtils;
import com.jsqix.yunpos.app.view.ConfirmDialog;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@ContentView(R.layout.activity_hb_pay)
public class HbPayActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.pro_name)
    private TextView proName;
    @ViewInject(R.id.order_num)
    private TextView orderNum;
    @ViewInject(R.id.mobile_phone)
    private TextView mobilePhone;
    @ViewInject(R.id.order_money)
    private TextView orderMoney;
    @ViewInject(R.id.sms_code)
    private EditText smsCode;
    @ViewInject(R.id.send_sms)
    private Button sendSms;
    @ViewInject(R.id.btnSubmit)
    private Button btnSubmit;
    @ViewInject(R.id.chronometer)
    private Chronometer chronometer;
    private int time = 60;

    final static int GET_SURE_ORDER = 0x0001, GET_PAY_URL = 0x0002, CODE_ORDER = 0x0003;
    final static int POST_MAKE_ORDER = 0xaaaa, POST_SMS_CODE = 0xaaab, POST_HB_PAY = 0xaaac;


    private String token = "";//支付token
    private String pay_no = "";//支付编号
    private String pay_sms = "";//支付短信验证码
    private String mobile_phone = "";//短信手机号
    private String hb_phone = "";//和包手机号
    private String order_money = "";//订单金额
    private String pro_name = "";// 商品名称
    private String show_sale_amt = "";// 优惠券金额
    private String cny_ac_bal = "";//电子券支付金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        placeOrder();
    }

    private void initView() {
        mobilePhone.setText(getIntent().getStringExtra("mobile_phone"));
        mobile_phone = CommUtils.textToString(mobilePhone);
        hb_phone = getIntent().getStringExtra("hb_phone");
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle("订单支付");
        titleBar.setLeftListener(new View.OnClickListener() {
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
                sendSms.setText(time + "s");

            }

        });
    }

    //开始倒计时
    private void startChronometerTick() {
        this.time = 60;
        chronometer.start();
        sendSms.setEnabled(false);
    }

    //停止倒计时
    private void stopChronometerTick() {
        chronometer.stop();
        sendSms.setEnabled(true);
        sendSms.setText("获取验证码");
    }

    @Event(value = {R.id.btnSubmit, R.id.send_sms})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.send_sms:
                double flag = StringUtils.toDouble(order_money) - Math.abs(StringUtils.toDouble(show_sale_amt)) - StringUtils.toDouble(cny_ac_bal);
                if (flag != 0) {
                    Utils.makeToast(this, "电子券余额不足");
                } else {
                    hbPaySMS();
                }
                break;
            case R.id.btnSubmit:
                pay_sms = CommUtils.textToString(smsCode);
                if (StringUtils.isEmpty(pay_sms)) {
                    Utils.makeToast(this, "请输入短信验证码");
                } else {
                    hbPay();
                }
                break;
        }
    }

    /**
     * 确认订单
     */
    private void sureOrder() {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("viewCode", "html");
        paras.put("PROD_NO", getIntent().getStringExtra("pro_no"));
        paras.put("DISCOUNT_FLG", getIntent().getStringExtra("discount_flg"));
        paras.put("DISCOUNT_PRICE", getIntent().getStringExtra("discount_price"));
        HBGet get = new HBGet(this, paras, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        get.execute("https://www.cmpay.com/mkmweb/wap_order_sure.xhtml");
        get.setResultCode(GET_SURE_ORDER);
    }

    /**
     * 下单 获取支付地址
     */
    private void placeOrder() {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("BUY_MBL_NO", mobile_phone);
        paras.put("PROD_NO", getIntent().getStringExtra("pro_no"));
        paras.put("DISCOUNT_FLG", getIntent().getStringExtra("discount_flg"));
        paras.put("DISCOUNT_PRICE", getIntent().getStringExtra("discount_price"));
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("https://www.cmpay.com/mkmweb/wap_order_pay.xhtml");
        post.setResultCode(POST_MAKE_ORDER);
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

            }
        };
        get.execute(url);
        get.setResultCode(GET_PAY_URL);
    }

    /**
     * 发送支付验证码
     */
    private void hbPaySMS() {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("token", token);
        paras.put("CASH_PAY", "0");
        paras.put("BON_PAY", "1");
        paras.put("AGR_NO", "");
        paras.put("OP", "1");
        paras.put("CAP_CRD_NO", "");
        paras.put("PWD", "");
        paras.put("VER_CD", "");
        paras.put("PAY_CAP_MOD", "1");
        paras.put("USR_NOPWD_FLG", "1");
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("https://ipos.10086.cn/wap/new_yezf_hqyzm.xhtml?viewCode=json");
        post.setResultCode(POST_SMS_CODE);
    }

    /**
     * 和包支付
     */
    private void hbPay() {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("token", token);
        paras.put("CASH_PAY", "0");
        paras.put("BON_PAY", "1");
        paras.put("AGR_NO", "");
        paras.put("OP", "1");
        paras.put("CAP_CRD_NO", "");
        paras.put("PWD", "");
        paras.put("VER_CD", pay_sms);
        paras.put("PAY_CAP_MOD", "1");
        paras.put("USR_NOPWD_FLG", "1");
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
        map.put("orderAmount", order_money);
        map.put("orderPhone", hb_phone);
        map.put("orderNotes", pro_name);
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
        if (resultCode == GET_SURE_ORDER) {
            loading.dismiss();
        } else if (resultCode == GET_PAY_URL) {//加载支付页面的结果
            analysisResult(result);
        } else if (resultCode == CODE_ORDER) {
            loading.dismiss();
            CodeOrderBean orderBean = new Gson().fromJson(result, CodeOrderBean.class);
            if ("000".equals(orderBean.getCode())) {
                Utils.makeToast(this, "支付成功");
                startActivity(new Intent(this, PayResultActivity.class).putExtra("orderId", orderBean.getObj().getOrderId()));
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

        token = DataUtils.getValue(result, "name=\"token\"", "value=\"", "\"");
//        token = "1484273411183";
        String orderMoneyStr = DataUtils.subStart(result, "<span>订单金额</span>");
        orderMoneyStr = DataUtils.subStart(orderMoneyStr, "<span class=\"fr\">");
        order_money = DataUtils.subEnd(orderMoneyStr, "元</span>");

        String showSaleAmt = DataUtils.subStart(result, "<span>优惠金额</span>");
        showSaleAmt = DataUtils.subStart(showSaleAmt, "<span class=\"fr c_orange\">");
        show_sale_amt = DataUtils.subEnd(showSaleAmt, "元</span>");

        String prodNameStr = DataUtils.subStart(result,
                "<div class=\"pdcom bglist\"><span>商品名称");
        prodNameStr = DataUtils.subStart(prodNameStr,
                "</span><span class=\"fr\">");
        pro_name = DataUtils.subEnd(prodNameStr, "</span>");

        String cnyAcBal = DataUtils.subStart(result, "<span>电子券</span>");
        cnyAcBal = DataUtils.subStart(cnyAcBal, "<div class=\"vl-ct-right mr90\"><span id=\"volume\">使用");
        cny_ac_bal = DataUtils.subEnd(cnyAcBal, "元</span></div>");


        orderMoney.setText(order_money + "元");
        proName.setText(pro_name);
        mobilePhone.setText(mobile_phone);
        orderNum.setText("1");

    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        if (resultCode == POST_MAKE_ORDER) {
            placeOrderResult(result);
        } else if (resultCode == POST_SMS_CODE) {//发送短信验证码的结果
            smsCodeResult(result);
        } else if (resultCode == POST_HB_PAY) {//订单支付的结果
            payResult(result);
        }
    }

    private void placeOrderResult(String result) {
        if (StringUtils.isEmpty(result)) {
            loading.dismiss();
            Utils.makeToast(this, "下单失败");
        } else {
            try {
                PlaceOrderSuccessResult successResult = new Gson().fromJson(result, PlaceOrderSuccessResult.class);
                if (!StringUtils.isEmpty(successResult.getPAY_URL())) {
                    analysisHtml(successResult.getPAY_URL());
                    btnSubmit.setEnabled(true);
                } else {
                    loading.dismiss();
                    PlaceOrderErrorResult errorResult = new Gson().fromJson(result, PlaceOrderErrorResult.class);
                    Utils.makeToast(this, errorResult.getGWA().getTMSG_INF());
                }
            } catch (Exception e) {
                loading.dismiss();
                e.printStackTrace();
                Utils.makeToast(this, "下单失败");
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
    public void onBackPressed() {
        showBackPressDialog();
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
