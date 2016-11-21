package com.jsqix.yunpos.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.DateUtil;
import com.jsqix.utils.DensityUtil;
import com.jsqix.utils.FrameApplication;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.api.HttpGet;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.BaseBean;
import com.jsqix.yunpos.app.bean.MsgBean;
import com.jsqix.yunpos.app.bean.PlaceOrder;
import com.jsqix.yunpos.app.bean.StatusReultBean;
import com.jsqix.yunpos.app.bean.SubResultBean;
import com.jsqix.yunpos.app.bean.UpdateResultBean;
import com.jsqix.yunpos.app.bean.VerificationResult;
import com.jsqix.yunpos.app.bean.WxOrderStatus;
import com.jsqix.yunpos.app.receiver.MyReceiver;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.SoftKeyBoardListener;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.view.CustomeDialog;
import com.jsqix.yunpos.app.view.TitleBar;
import com.zxing.encode.EncodingHandler;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_cash)
public class CashActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    //移动电子券
    @ViewInject(R.id.top_radio_left)
    private RadioButton electronicCouponsRadio;
    @ViewInject(R.id.electronic_coupons)
    private LinearLayout electronicCoupons;
    //券码核销
    @ViewInject(R.id.top_radio_right)
    private RadioButton verificationCouponRadio;
    @ViewInject(R.id.verification_coupon)
    private LinearLayout verificationCoupon;
    @ViewInject(R.id.input_coupon_phone)
    private EditText verificationPhone;
    @ViewInject(R.id.input_coupon_no)
    private EditText verificationNo;
    //支付金额
    @ViewInject(R.id.editTextAmt)
    private EditText editTextAmt;
    @ViewInject(R.id.editTextNotice)
    private EditText editTextNotice;
    //电子券支付
    @ViewInject(R.id.select_coupons_pay)
    private RadioButton couponsPay;
    @ViewInject(R.id.layout_coupons_pay)
    private LinearLayout couponsLayout;
    @ViewInject(R.id.input_coupons_money)
    private EditText couponsMoney;
    @ViewInject(R.id.input_coupons_phone)
    private EditText couponsPhone;
    @ViewInject(R.id.input_coupons_code)
    private EditText couponsCode;
    @ViewInject(R.id.send_coupons_code)
    private Button sendCode;
    //微信支付
    @ViewInject(R.id.select_wechat_pay)
    private RadioButton wechatPay;
    //    @ViewInject(R.id.wechat_tips)
//    privae TextView webchatTips;
    //券码支付
    @ViewInject(R.id.layout_code_pay)
    private LinearLayout codeLayput;
    @ViewInject(R.id.select_code_pay)
    private RadioButton codePay;
    @ViewInject(R.id.input_code_no)
    private EditText editCodeNo;
    @ViewInject(R.id.input_code_phone)
    private EditText editCodePhone;
    //键盘滚动
    @ViewInject(R.id.scrollView)
    private ScrollView scrollView;
    @ViewInject(R.id.content)
    private LinearLayout content;


    // 计时器
    @ViewInject(R.id.chronometer)
    private Chronometer chronometer;
    private int time = 60;
    //定时器
    private Timer timer;
    private TimerTask task;
    static int TIME = 5 * 1000;

    //任务
    String taskId = "";
    //订单编号
    String orderId = "";
    //渠道
    String channelType = "";

    final static int ORDER = 0, CODE = 1, UPDATE = 2, PAY = 3, MSG = 4;

    final static int PLACE = 0x00010, CONFIRM = 0x00011, QUERY = 0x00100;

    final static int CODE_ORDER = 0xAAAA;

    final static int VERIFICATION_COUPON = 0xBBBB;

    //支付确认
    private CustomeDialog payDailog;
    private LinearLayout dialogClose;
    private TextView amount, phone;
    private Button submit;
    //微信扫描支付
    private CustomeDialog wxPayDailog;
    private ImageView wxQrcode;
    private Button cancel;
    //消息对话框
    private CustomeDialog msgDailog;
    private TextView msgTitle;
    private TextView msgContent;
    private Button msgButton;

    public static boolean isForeground = false;
    private PowerManager.WakeLock mWakeLock = null;
    private static final String POWER_LOCK = "CashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);
        registerMessageReceiver();
        initView();
        initEvent();
    }

    private void initView() {
        titleBar.showLeft(false).setTitle("积分付收银台");

        editTextAmt.setSelection(CommUtils.textToString(editTextAmt).length());
        couponsMoney.setText(CommUtils.textToString(editTextAmt));
        editCodeNo.setText(DateUtil.dateToString(new Date(), "yyyyMMdd"));
        editCodeNo.setSelection(CommUtils.textToString(editCodeNo).length());
//        tips = getString(R.string.cash_tips);
        initPayDailog();
        initWxQrcode();
        initMsgDailog();

        getMessage();
    }

    private void initMsgDailog() {
        msgDailog = new CustomeDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_msg, null);
        msgDailog.setView(view);
        msgDailog.setParas(0.8f, 0f);
        msgDailog.setCanceledOnTouchOutside(false);
        msgDailog.setCancelable(false);
        msgTitle = (TextView) view.findViewById(R.id.msgTitle);
        msgContent = (TextView) view.findViewById(R.id.msgContent);
        msgButton = (Button) view.findViewById(R.id.btnCancel);
    }

    private void initPayDailog() {
        payDailog = new CustomeDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_pay, null);
        payDailog.setView(view);
        payDailog.setParas(0.8f, 0f);
        payDailog.setCanceledOnTouchOutside(false);
        payDailog.setCancelable(false);
        dialogClose = (LinearLayout) view.findViewById(R.id.dialog_close);
        amount = (TextView) view.findViewById(R.id.amount);
        phone = (TextView) view.findViewById(R.id.phone);
        submit = (Button) view.findViewById(R.id.btnSubmit);

    }

    private void initWxQrcode() {
        wxPayDailog = new CustomeDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_wx_qrcode, null);
        wxPayDailog.setView(view);
        wxPayDailog.setParas(0.8f, 0f);
        wxPayDailog.setCanceledOnTouchOutside(false);
        wxPayDailog.setCancelable(false);
        wxQrcode = (ImageView) view.findViewById(R.id.image_wx_qrcode);
        cancel = (Button) view.findViewById(R.id.btnCancel);
    }


    private void initEvent() {
        final View view = new View(this);

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                if (couponsPay.isChecked()) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(CashActivity.this, 120));
                    content.addView(view, lp);
                    //滚动到底部
                    scrollView.smoothScrollTo(0, DensityUtil.dip2px(CashActivity.this, 1000));
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                        }
//                    });
                }
            }

            @Override
            public void keyBoardHide(int height) {
                content.removeView(view);
            }
        });
        editTextAmt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {//保留两位小数
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editTextAmt.setText(s);
                        editTextAmt.setSelection(s.length());
                    }
                    if (s.toString().trim().substring(0, s.toString().trim().indexOf(".")).length() > 5) {//整数位最多5位
                        s = s.toString().subSequence(0, 5);
                        editTextAmt.setText(s);
                        editTextAmt.setSelection(s.length());
                    }
                } else {
                    if (s.toString().trim().length() > 5) {
                        s = s.toString().subSequence(0, 5);
                        editTextAmt.setText(s);
                        editTextAmt.setSelection(s.length());
                    }
                }

                if (s.toString().trim().substring(0).equals(".")) {//自动添加0
                    s = "0" + s;
                    editTextAmt.setText(s);
                    editTextAmt.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {//0开头
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editTextAmt.setText(s.subSequence(1, 2));
                        editTextAmt.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (couponsPay.isChecked()) {
                    couponsMoney.setText(CommUtils.textToString(editTextAmt));
                }
//                if (couponsPay.isChecked()) {
//                    couponsMoney.setText("");
//                    wechatPay.setChecked(false);
//
//                } else if (wechatPay.isChecked()) {
//                    if (CommUtils.toFloat(CommUtils.textToString(editTextAmt)) != 0)
//                        webchatTips.setText("(需支付" + CommUtils.toFormat(CommUtils.textToString(editTextAmt)) + "元)");
//                }
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
                sendCode.setText(time + "s");

            }

        });
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDailog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = couponsCode.getText().toString();
                if ("1".equals(channelType)) {
                    updateCmd(code, CommUtils.textToString(amount));
                } else {
                    confirmOrder(code, CommUtils.textToString(amount));
                }
            }
        });

//        editTextAmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    editTextAmt.setText(CommUtils.toFormat(CommUtils.textToString(editTextAmt)));
//                }
//            }
//        });
//        couponsMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    if (couponsPay.isChecked()) {
//                        if (CommUtils.toFloat(CommUtils.textToString(couponsMoney)) == 0) {
//                            couponsMoney.setText(CommUtils.toFormat(CommUtils.textToString(editTextAmt)));
//                        } else {
//                            couponsMoney.setText(CommUtils.toFormat(CommUtils.textToString(couponsMoney)));
//                        }
//                    }
//
//                }
//            }
//        });
        couponsMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {//保留两位小数
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        couponsMoney.setText(s);
                        couponsMoney.setSelection(s.length());
                    }

                }
                if (s.toString().trim().substring(0).equals(".")) {//自动添加0
                    s = "0" + s;
                    couponsMoney.setText(s);
                    couponsMoney.setSelection(2);
                }

                if (CommUtils.toFloat(s) > CommUtils.toFloat(CommUtils.textToString(editTextAmt))) {
                    s = CommUtils.textToString(editTextAmt);
                    couponsMoney.setText(s);
                    couponsMoney.setSelection(s.length());
                    return;
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {//0开头
                    if (!s.toString().substring(1, 2).equals(".")) {
                        couponsMoney.setText(s.subSequence(1, 2));
                        couponsMoney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (CommUtils.toFloat(CommUtils.textToString(couponsMoney)) == CommUtils.toFloat(CommUtils.textToString(editTextAmt))) {
//                    wechatPay.setChecked(false);
//                } else {
//                    if (!CommUtils.isEmpty(CommUtils.textToString(couponsMoney))) {
//                        float total = CommUtils.toFloat(CommUtils.textToString(editTextAmt));
//                        float coupons = CommUtils.toFloat(CommUtils.textToString(couponsMoney));
//                        if (total - coupons > 0)
//                            webchatTips.setText("(需支付" + CommUtils.toFormat(total - coupons) + "元)");
//                        wechatPay.setChecked(true);
//                    }
//                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                wxPayDailog.dismiss();
            }
        });
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDailog.dismiss();
            }
        });
    }

    @Event(value = {R.id.buttonSubmit, R.id.send_coupons_code})
    private void click(View v) {
        String amt = CommUtils.textToString(editTextAmt);
        String coupons = CommUtils.textToString(couponsMoney);
        String phone = CommUtils.textToString(couponsPhone);
        String code = CommUtils.textToString(couponsCode);
        String orderNotes = CommUtils.textToString(editTextNotice);
        String codeNo = CommUtils.textToString(editCodeNo);
        switch (v.getId()) {
            case R.id.buttonSubmit:
                if (electronicCouponsRadio.isChecked()) {
                    if (couponsPay.isChecked()) {
                        if (CommUtils.isEmpty(amt) || CommUtils.toFloat(amt) == 0) {
                            Utils.makeToast(this, "请输入支付金额");
                        } else if (CommUtils.isEmpty(coupons) || CommUtils.toFloat(coupons) == 0) {
                            Utils.makeToast(this, "请输入电子券金额");
                        } else if (CommUtils.isEmpty(phone)) {
                            Utils.makeToast(this, "请输入手机号");
                        } else if (CommUtils.isEmpty(taskId)) {
                            Utils.makeToast(this, "请获取验证码");
                        } else if (CommUtils.isEmpty(code)) {
                            Utils.makeToast(this, "请输入验证码");
                        } else {
//                        if (CommUtils.toFloat(amt) > CommUtils.toFloat(coupons)) {
//                            wechatPay.setChecked(true);
//                        }
                            amount.setText(CommUtils.toFormat(coupons));
                            CashActivity.this.phone.setText(phone);
                            payDailog.show();
                        }
                    } else if (wechatPay.isChecked()) {
                        if (CommUtils.isEmpty(amt) || CommUtils.toFloat(amt) == 0) {
                            Utils.makeToast(this, "请输入支付金额");
                        } else {
                            placeOrder(UAD.TYPE_WX, "", "", amt, orderNotes);
                        }
                    } else if (codePay.isChecked()) {
                        if (CommUtils.isEmpty(amt) || CommUtils.toFloat(amt) == 0) {
                            Utils.makeToast(this, "请输入支付金额");
                        } else if (CommUtils.isEmpty(CommUtils.textToString(editCodePhone))) {
                            Utils.makeToast(this, "请输入手机号");
                        } else if (CommUtils.notPhone(CommUtils.textToString(editCodePhone))) {
                            Utils.makeToast(this, "手机号不正确");
                        } else if (CommUtils.isEmpty(codeNo)) {
                            Utils.makeToast(this, "请输入订单编号");
                        } else {
                            codeOrder();
                        }
                    } else {
                        Utils.makeToast(this, "请选择支付方式");
                    }
                } else if (verificationCouponRadio.isChecked()) {
                    String verification_phone = CommUtils.textToString(verificationPhone);
                    String verification_no = CommUtils.textToString(verificationNo);
                    if (CommUtils.isEmpty(verification_phone)) {
                        Utils.makeToast(this, "请输入手机号");
                    } else if (CommUtils.notPhone(verification_phone)) {
                        Utils.makeToast(this, "手机号不正确");
                    } else if (CommUtils.isEmpty(verification_no)) {
                        Utils.makeToast(this, "请输入券码编号");
                    } else {
                        verificationCoupon(verification_phone, verification_no);
                    }
                }
                break;
            //发送验证码
            case R.id.send_coupons_code:
                if (CommUtils.isEmpty(amt) || CommUtils.toFloat(amt) == 0) {
                    Utils.makeToast(this, "请输入支付金额");
                } else if (CommUtils.isEmpty(coupons) || CommUtils.toFloat(coupons) == 0) {
                    Utils.makeToast(this, "请输入电子券金额");
                } else if (CommUtils.isEmpty(phone)) {
                    Utils.makeToast(this, "请输入手机号");
                } else if (CommUtils.notPhone(phone)) {
                    Utils.makeToast(this, "手机号不正确");
                } else {
//                    if (CommUtils.toFloat(amt) > CommUtils.toFloat(coupons)) {
//                        wechatPay.setChecked(true);
//                    }
                    placeOrder(UAD.TYPE_DZQ, "", phone, coupons, orderNotes);
                }
                break;
            default:
                break;
        }
    }

    //    @Event(value = {R.id.select_coupons_pay, R.id.select_wechat_pay}, type = CompoundButton.OnCheckedChangeListener.class)
//    private void selectPayWay(CompoundButton buttonView, boolean isChecked) {
//        switch (buttonView.getId()) {
//            case R.id.select_coupons_pay:
//                if (isChecked) {
//                    couponsLayout.setVisibility(View.VISIBLE);
//                } else {
//                    couponsLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.select_wechat_pay:
//                float total = CommUtils.toFloat(CommUtils.textToString(editTextAmt));
//                float coupons = CommUtils.toFloat(CommUtils.textToString(couponsMoney));
//                if (isChecked && couponsPay.isChecked()) {
//                    if (total - coupons > 0)
//                        webchatTips.setText("(需支付" + CommUtils.toFormat(total - coupons) + "元)");
//                } else {
//                    webchatTips.setText("");
//                }
//                break;
//        }
//    }
    @Event(value = R.id.main_radio, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.select_coupons_pay:
                couponsLayout.setVisibility(View.VISIBLE);
                codeLayput.setVisibility(View.GONE);
                couponsMoney.setText(CommUtils.textToString(editTextAmt));
                break;
            case R.id.select_wechat_pay:
                couponsLayout.setVisibility(View.GONE);
                codeLayput.setVisibility(View.GONE);
                break;
            case R.id.select_code_pay:
                couponsLayout.setVisibility(View.GONE);
                codeLayput.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Event(value = R.id.top_radio, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCouponsChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.top_radio_left:
                electronicCoupons.setVisibility(View.VISIBLE);
                verificationCoupon.setVisibility(View.GONE);
                break;
            case R.id.top_radio_right:
                electronicCoupons.setVisibility(View.GONE);
                verificationCoupon.setVisibility(View.VISIBLE);
                break;

        }
    }


    //开始倒计时
    private void startChronometerTick() {
        time = 60;
        chronometer.start();
        sendCode.setEnabled(false);
        couponsMoney.setEnabled(false);
        editTextAmt.setEnabled(false);
    }

    //停止倒计时
    private void stopChronometerTick() {
        chronometer.stop();
        sendCode.setEnabled(true);
        sendCode.setText("获取验证码");
        couponsMoney.setEnabled(true);
        editTextAmt.setEnabled(true);
    }


    //更新任务
    private void updateCmd(String code, String amount) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", taskId);
        map.put("orderPassword", code);
        map.put("orderAmount", amount);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("uCmd");
        post.setResultCode(UPDATE);
    }

    //任务下单
    private void SubmitCmd(String amt, String phone, String orderNotes) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", aCache.getAsString(UAD.UID));
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
        map.put("cmdType", 1);//0余额查询，1收单
        map.put("orderAmount", amt);
        map.put("orderNotes", orderNotes);
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

    //查询任务状态
    private void QueryCmd(int code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", taskId);
        HttpPost post = new HttpPost(CashActivity.this, CashActivity.this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("qCmd");
        post.setResultCode(code);
    }

    //获取消息
    private void getMessage() {
        if (UAD.NOT_NEW == true) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sjc", System.currentTimeMillis());
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("getSysNoticeNew");
        post.setResultCode(MSG);
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
            if (msg.what == QUERY) {
                queryOrder(msg.what);
            } else {
                QueryCmd(msg.what);
            }
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

    //核销券码
    private void verificationCoupon(String orderPhone, String coupon) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("orderSource", UAD.ANDROID);
        map.put("orderPhone", orderPhone);
        map.put("coupon", coupon);
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
                loading.show();
            }
        };
        get.execute("verificationCoupon");
        get.setResultCode(VERIFICATION_COUPON);

    }

    //下单
    private void placeOrder(String orderType, String orderId, String orderPhone, String payAmount, String orderNotes) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", aCache.getAsString(UAD.UID));
        if (!CommUtils.isEmpty(orderId)) {
            map.put("orderId", orderId);
        }
        map.put("orderType", orderType);//1
        map.put("orderSource", UAD.ANDROID);
        if (!CommUtils.isEmpty(orderPhone)) {
            map.put("orderPhone", orderPhone);
        }
        map.put("orderAmount", CommUtils.textToString(editTextAmt));
        map.put("payAmount", payAmount);
        if (!CommUtils.isEmpty(orderNotes)) {
            map.put("orderNotes", orderNotes);
        }
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
                loading.show();
            }
        };
        get.execute("preOrder");
        get.setResultCode(PLACE);
    }

    //券码下单
    private void codeOrder() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("orderId", CommUtils.textToString(editCodeNo));
        map.put("orderSource", UAD.ANDROID);
        map.put("orderAmount", CommUtils.textToString(editTextAmt));
        map.put("orderPhone", CommUtils.textToString(editCodePhone));
        HttpGet get = new HttpGet(this, map, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(CODE_ORDER);
        get.execute("preOrderDXT");
    }

    //更新订单
    private void confirmOrder(String code, String amount) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("detailId", taskId);
        map.put("orderPassword", code);
        map.put("orderAmount", amount);
        HttpGet get = new HttpGet(this, map, this) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        get.execute("confirmOrder");
        get.setResultCode(CONFIRM);
    }

    //查询订单
    private void queryOrder(int code) {
        Map<String, Object> map = new HashMap<>();
        map.put("detailId", taskId);
        HttpGet get = new HttpGet(this, map, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute("checkOrderDetailStatus");
        get.setResultCode(code);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        try {
            switch (resultCode) {
                case PLACE:
                    loading.dismiss();
                    PlaceOrder order = new Gson().fromJson(result, PlaceOrder.class);
                    if ("000".equals(order.getCode())) {
                        orderId = order.getObj().getOrderId();
                        taskId = order.getObj().getDetailId();
                        if (UAD.TYPE_WX.equals(order.getObj().getOrderType())) {
                            Bitmap bmp = EncodingHandler.createQRCode(order.getObj().getWxStr(), Utils.dip2px(this, 180));
                            wxQrcode.setImageBitmap(bmp);
                            wxPayDailog.show();
                            TIME = 10 * 1000;
                            start(QUERY);
                        } else {
                            channelType = order.getObj().getChannelType();
                            if ("1".equals(channelType)) {
                                TIME = 5 * 1000;
                                start(CODE);
                            } else {
                                Utils.makeToast(CashActivity.this, order.getObj().getStatusRemark());
                                if ("0".equals(order.getObj().getStatus())) {
                                    startChronometerTick();
                                }
                            }
                        }
                    } else {
                        Utils.makeToast(CashActivity.this, order.getMsg());
                    }
                    break;
                case CONFIRM:
                    loading.dismiss();
                    PlaceOrder placeOrder = new Gson().fromJson(result, PlaceOrder.class);
                    if ("000".equals(placeOrder.getCode())) {
                        payDailog.dismiss();
                        if ("1".equals(placeOrder.getObj().getStatus())) {
                            float total = CommUtils.toFloat(CommUtils.textToString(editTextAmt));
                            float coupons = CommUtils.toFloat(CommUtils.textToString(amount));
                            if (/*wechatPay.isChecked() &&*/ total - coupons > 0) {
                                placeOrder(UAD.TYPE_WX, orderId, "", CommUtils.toFormat(total - coupons), CommUtils.textToString(editTextNotice));
                            } else {
                                startActivity(new Intent(CashActivity.this, PayResultActivity.class).putExtra("orderId", orderId));
                            }
                        } else {
                            startActivity(new Intent(CashActivity.this, PayResultActivity.class).putExtra("orderId", orderId));
                        }
                    } else {
                        Utils.makeToast(CashActivity.this, placeOrder.getMsg());
                    }
                    break;
                case QUERY:
                    WxOrderStatus status = new Gson().fromJson(result, WxOrderStatus.class);
                    if ("000".equals(status.getCode())) {
                        if (status.getObj().getStatus() != 0) {
                            stop();
                            wxPayDailog.dismiss();
                            startActivity(new Intent(CashActivity.this, PayResultActivity.class).putExtra("orderId", status.getObj().getOrderId()));
                        }
                    }
                    break;
                case CODE_ORDER:
                    BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                    if ("000".equals(baseBean.getCode())) {
                        showError("保存成功");
                        editCodeNo.setText(DateUtil.dateToString(new Date(), "yyyyMMdd"));
                        editCodeNo.setSelection(CommUtils.textToString(editCodeNo).length());
                        editCodePhone.setText("");
                    } else {
                        showError(baseBean.getMsg());
                    }
                    break;
                case VERIFICATION_COUPON:
                    loading.dismiss();
                    baseBean = new Gson().fromJson(result, BaseBean.class);
                    if ("000".equals(baseBean.getCode())) {
                        VerificationResult verificationResult = new Gson().fromJson(result, VerificationResult.class);
                        startActivity(new Intent(CashActivity.this, PayResultActivity.class).putExtra("orderId", verificationResult.getObj().getOrderId()));
                        Utils.makeToast(this, verificationResult.getMsg());
                    } else {
                        Utils.makeToast(this, baseBean.getMsg());
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            if (CommUtils.isNull(result)) {
                Utils.makeToast(this, "~网络超时，请稍候重试~");
            } else {
                Utils.makeToast(this, "json数据异常");
            }
        }
    }

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            switch (resultCode) {
                case ORDER:
                    SubResultBean subBean = new Gson().fromJson(result, SubResultBean.class);
                    if (subBean.getCode().equals("000")) {
                        taskId = subBean.getObj().getID();
                        TIME = 5 * 1000;
                        start(CODE);
                    } else {
                        showError(subBean.getMsg());
                        loading.dismiss();
                    }
                    break;
                case CODE:
                    StatusReultBean stBean = new Gson().fromJson(result, StatusReultBean.class);
                    if (stBean.getCode().equals("000")) {
                        int status = CommUtils.toInt(stBean.getObj().getCmdProgress());
                        if (status >= 0) {
                            if (status == 10) {
                                showError("短信发送成功");
                                loading.dismiss();
                                stop();
                                startChronometerTick();
                            }
                        } else {
                            showError(stBean.getObj().getCmdStatus());
                            loading.dismiss();
                            stop();
                        }
                    } else {
                        showError(stBean.getMsg());
                        loading.dismiss();
                        stop();
                    }
                    break;
                case PAY:
                    StatusReultBean bean = new Gson().fromJson(result, StatusReultBean.class);
                    if (bean.getCode().equals("000")) {
                        int status = CommUtils.toInt(bean.getObj().getCmdProgress());
                        if (status >= 0) {
                            if (status == 100) {
                                couponsCode.setText("");
                                editTextAmt.setText("0.radio_mid_rect");
                                loading.dismiss();
                                stop();
                                float total = CommUtils.toFloat(CommUtils.textToString(editTextAmt));
                                float coupons = CommUtils.toFloat(CommUtils.textToString(amount));
                                if (/*wechatPay.isChecked() &&*/ total - coupons > 0) {
                                    placeOrder(UAD.TYPE_WX, orderId, "", CommUtils.toFormat(total - coupons), CommUtils.textToString(editTextNotice));
                                } else {
                                    startActivity(new Intent(CashActivity.this, PayResultActivity.class).putExtra("orderId", orderId));
                                }
                            }
                        } else {
                            startActivity(new Intent(CashActivity.this, PayResultActivity.class).putExtra("orderId", orderId));
                            loading.dismiss();
                            stop();
                        }
                    } else {
                        showError(bean.getMsg());
                        loading.dismiss();
                        stop();
                    }
                    break;
                case UPDATE:
                    UpdateResultBean upBean = new Gson().fromJson(result, UpdateResultBean.class);
                    if (upBean.getCode().equals("000")) {
                        TIME = 5 * 1000;
                        start(PAY);
                    } else {
                        showError(upBean.getMsg());
                        loading.dismiss();
                    }
                    break;
                case MSG:
                    MsgBean msg = new Gson().fromJson(result, MsgBean.class);
                    if ("000".equals(msg.getCode())) {
                        msgTitle.setText(msg.getObj().getTitle());
                        msgContent.setText(msg.getObj().getContent());
                        msgDailog.show();
                    }
                    loading.dismiss();
                    UAD.NOT_NEW = true;
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            if (CommUtils.isNull(result)) {
                Utils.makeToast(this, "~网络超时，请稍候重试~");
            } else {
                Utils.makeToast(this, "json数据异常");
            }
            stop();
            loading.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return FrameApplication.getInstance().closeAppByBack(KeyEvent.KEYCODE_BACK, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        if (null != mWakeLock && (!mWakeLock.isHeld())) {
            mWakeLock.acquire();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }


    @Override
    protected void onDestroy() {
        isForeground = false;
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MyReceiver.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MyReceiver.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(MyReceiver.KEY_MESSAGE);
//                String extras = intent.getStringExtra(MyReceiver.KEY_EXTRAS);
                if (wxPayDailog.isShowing()) {
                    stop();
                    wxPayDailog.dismiss();
                    try {
                        JSONObject object = new JSONObject(messge);
                        String oid = object.getString("oid");
                        if (orderId.equals(oid)) {
                            startActivity(new Intent(CashActivity.this, PayResultActivity.class).putExtra("orderId", oid));
                        }
                    } catch (Exception e) {
                        Utils.makeToast(CashActivity.this, "json数据异常");
                    }
                }
            }
        }
    }
}
