package com.jsqix.yunpos.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.hb.HBDownLoad;
import com.jsqix.yunpos.app.api.hb.HBGet;
import com.jsqix.yunpos.app.api.hb.HBPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.hb.HBLoginResult;
import com.jsqix.yunpos.app.bean.hb.LoginSmsCodeResult;
import com.jsqix.yunpos.app.utils.AllLowTransformationMethod;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.hb.DataUtils;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.LinkedHashMap;
import java.util.Map;


@ContentView(R.layout.activity_hb_login)
public class HbLoginActivity extends BaseAty {

    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.mobile_phone)
    private EditText mobilePhone;
    @ViewInject(R.id.image_code)
    private EditText imageCode;
    @ViewInject(R.id.iv_image_code)
    private ImageView ivImageCode;
    @ViewInject(R.id.sms_code)
    private EditText smsCode;
    @ViewInject(R.id.send_sms)
    private Button sendSms;
    @ViewInject(R.id.btnSubmit)
    private Button btnSubmit;
    // 计时器
    @ViewInject(R.id.chronometer)
    private Chronometer chronometer;
    private int time = 60;

    final static int GET_PRODUCT_INFO = 0x0001, LOGIN_IMAGE_CODE = 0x0002;

    final static int POST_SMS_CODE = 0xaaaa, POST_HB_LOGIN = 0xaaab;

    private String pro_no;//商品编号
    private String pro_name;//商品名称
    private String discount_flg;//商品是否打折
    private String discount_price;//商品打折后金额
    private String pro_price;//商品金额
    private String hb_phone;//和包登录手机号
    private String img_code = "";//图形验证码
    private String sms_code = "";//短信验证码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        pro_no = getIntent().getStringExtra("pro_no");
//        pro_no="20161115180812";
        getProductInfo(pro_no);
    }

    private void initView() {
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle("登录和包");
        imageCode.setTransformationMethod(new AllLowTransformationMethod());

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

    @Event(value = {R.id.send_sms, R.id.iv_image_code, R.id.btnSubmit})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.iv_image_code:
                loading.show();
                getLoginImageCode();
                stopChronometerTick();
                break;
            case R.id.send_sms:
                hb_phone = CommUtils.textToString(mobilePhone);
                img_code = CommUtils.textToString(imageCode);
                if (StringUtils.isEmpty(hb_phone)) {
                    Utils.makeToast(this, "请输入手机号");
                } else if (CommUtils.notPhone(hb_phone)) {
                    Utils.makeToast(this, "手机号格式不正确");
                } else if (StringUtils.isEmpty(img_code)) {
                    Utils.makeToast(this, "请输入图形验证码");
                } else {
                    hbLoginSMS();
                }
                break;
            case R.id.btnSubmit:
//                startActivity(new Intent(this,HbPayActivity.class));
                hb_phone = CommUtils.textToString(mobilePhone);
                img_code = CommUtils.textToString(imageCode);
                sms_code = CommUtils.textToString(smsCode);
                if (StringUtils.isEmpty(hb_phone)) {
                    Utils.makeToast(this, "请输入手机号");
                } else if (CommUtils.notPhone(hb_phone)) {
                    Utils.makeToast(this, "手机号格式不正确");
                } else if (StringUtils.isEmpty(img_code)) {
                    Utils.makeToast(this, "请输入图形验证码");
                } else if (StringUtils.isEmpty(sms_code)) {
                    Utils.makeToast(this, "请输入短信验证码");
                } else {
                    hbLogin();
                }
                break;
        }
    }


    /**
     * 获取商品详情
     *
     * @param prodId 商品编号
     */
    private void getProductInfo(String prodId) {
        HBGet get = new HBGet(this, null, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        get.execute("https://www.cmpay.com/mkmweb/wap_produce_detail.xhtml?viewCode=html&PROD_TYPE=&PROD_NO=" + prodId);
        get.setResultCode(GET_PRODUCT_INFO);
    }

    /**
     * 获取登录图形验证码
     */
    private void getLoginImageCode() {
        HBDownLoad down = new HBDownLoad(this, this) {
            @Override
            public void onPreExecute() {

            }
        };
        down.execute("https://www.cmpay.com/service/image.xhtml");
        down.setResultCode(LOGIN_IMAGE_CODE);
    }

    /**
     * 登录短信验证码
     */
    private void hbLoginSMS() {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("MBL_NO", hb_phone);
        paras.put("VER_CD", img_code);
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("https://www.cmpay.com/user/service/sendchekno.xhtml?viewCode=json");
        post.setResultCode(POST_SMS_CODE);
    }

    /**
     * 登录和包账户
     */
    private void hbLogin() {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("MBL_NO", hb_phone);
        paras.put("CHK_NO", sms_code);
        paras.put("ETA_FLAG", "LMF");
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("https://www.cmpay.com/user/service/mblmessagelog.xhtml?viewCode=json");
        post.setResultCode(POST_HB_LOGIN);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        super.getCallback(resultCode, result);
        if (resultCode == GET_PRODUCT_INFO) {//获取商品详情
            productInfoResult(result);
        } else if (resultCode == LOGIN_IMAGE_CODE) {//获取图形验证码
            displayImageCode(result);
        }
    }

    /**
     * 商品详情分析
     *
     * @param result
     */
    private void productInfoResult(String result) {
        if (StringUtils.isEmpty(result)) {
            loading.dismiss();
            Utils.makeToast(this, "产品不存在或已下架");
            finish();
        } else {
            String PROD_NM_str = DataUtils.subStart(result, "id=\"PROD_NM\"");
            PROD_NM_str = DataUtils.subStart(PROD_NM_str, "value=\"");
            pro_name = DataUtils.subEnd(PROD_NM_str, "\"");// 商品名称
            String discountFlagStr = DataUtils.subStart(result,
                    "id=\"discountFlag\"");
            discountFlagStr = DataUtils.subStart(discountFlagStr, "value=\"");
            String discountFlag = DataUtils.subEnd(discountFlagStr, "\"");// 折扣标识 折扣标识为1才有折扣价
            String discountPrice = "";// 折扣价
            if ("1".equals(discountFlag)) {
                String discountPriceStr = DataUtils.subStart(result,
                        "id=\"discountPrice\"");
                discountPriceStr = DataUtils.subStart(discountPriceStr,
                        "value=\"");
                discountPrice = DataUtils.subEnd(discountPriceStr, "\"");
            }
            String prodPriceStr = DataUtils.subStart(result, "id=\"prodPrice\"");
            prodPriceStr = DataUtils.subStart(prodPriceStr, "value=\"");
            String prodPrice = DataUtils.subEnd(prodPriceStr, "\"");// 原价
            this.discount_flg = discountFlag;
            this.discount_price = discountPrice;
            this.pro_price = prodPrice;
            if (prodPrice.length() == 0 || "1".equals(discountFlag)
                    && discountPrice.length() == 0) {
                loading.dismiss();
                Utils.makeToast(this, "商品详情解析失败");
                return;
            }
            getLoginImageCode();
        }
    }

    /**
     * 显示图形验证码
     *
     * @param result
     */
    private void displayImageCode(String result) {
        loading.dismiss();
        BitmapFactory.Options ops = new BitmapFactory.Options();
//        ops.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(result, ops);
//        int height = ops.outHeight * DensityUtil.dip2px(this,100) / ops.outWidth;
//        ops.outWidth = DensityUtil.dip2px(this,100);
//        ops.outHeight = DensityUtil.dip2px(this,30);
//        /* 这样才能真正的返回一个Bitmap给你 */
//        ops.inJustDecodeBounds = false;
//        bmp = BitmapFactory.decodeFile(result, ops);
        ivImageCode.setImageBitmap(bmp);
        imageCode.setText("");
    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        if (resultCode == POST_SMS_CODE) {//发送短信验证码的结果
            smsCodeResult(result);
        } else if (resultCode == POST_HB_LOGIN) {//发送登录和包的结果
            loginResult(result);
        }
    }


    /**
     * 发送短信的解析
     *
     * @param result
     */
    private void smsCodeResult(String result) {
        loading.dismiss();
        LoginSmsCodeResult smsCodeResult = new Gson().fromJson(result, LoginSmsCodeResult.class);
        if (!StringUtils.isEmpty(smsCodeResult.getTCA_FLG()) && !StringUtils.isEmpty(smsCodeResult.getRSP_EX_TIME())) {
            Utils.makeToast(this, "短信发送成功");
            startChronometerTick();
        } else {
            Utils.makeToast(this, smsCodeResult.getGWA().getMSG_INF());
        }
    }

    /**
     * 登录解析
     *
     * @param result
     */
    private void loginResult(String result) {
        loading.dismiss();
        HBLoginResult loginResult = new Gson().fromJson(result, HBLoginResult.class);
        if (!StringUtils.isEmpty(loginResult.getUSR_NO())) {
            Intent intent = new Intent(this, HbConfirmActivity.class);
            intent.putExtra("pro_no", pro_no);
            intent.putExtra("pro_name", pro_name);
            intent.putExtra("pro_price", pro_price);
            intent.putExtra("hb_phone", hb_phone);
            intent.putExtra("discount_flg", discount_flg);
            intent.putExtra("discount_price", discount_price);
            startActivity(intent);
            finish();
        } else {
            Utils.makeToast(this, loginResult.getGWA().getMSG_INF());
        }
    }

}
