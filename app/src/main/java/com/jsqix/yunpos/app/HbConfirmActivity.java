package com.jsqix.yunpos.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.jsqix.yunpos.app.R.id.pro_name;

@ContentView(R.layout.activity_hb_confirm)
public class HbConfirmActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(pro_name)
    private TextView proName;
    @ViewInject(R.id.pro_price)
    private TextView proPrice;
    @ViewInject(R.id.order_num)
    private TextView orderNum;
    @ViewInject(R.id.mobile_phone)
    private EditText mobilePhone;
    @ViewInject(R.id.pay_money)
    private TextView payMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle("订单确认");
        proName.setText(getIntent().getStringExtra("pro_name"));
        proPrice.setText(getIntent().getStringExtra("pro_price") + "元");
        orderNum.setText("1");
        mobilePhone.setText(getIntent().getStringExtra("hb_phone"));
        mobilePhone.setSelection(CommUtils.textToString(mobilePhone).length());
        if (StringUtils.isEmpty(getIntent().getStringExtra("discount_price"))) {
            payMoney.setText(getIntent().getStringExtra("pro_price") + "元");
        } else {
            payMoney.setText(getIntent().getStringExtra("discount_price") + "元");
        }
    }


    @Event(R.id.btnSubmit)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                String mobile_phone = CommUtils.textToString(mobilePhone);
                if (StringUtils.isEmpty(mobile_phone)) {
                    Utils.makeToast(this, "请输入手机号");
                } else if (!CommUtils.isChinaMobile(mobile_phone)) {
                    Utils.makeToast(this, "请输入移动手机号");
                } else {
                    Intent intent = new Intent(this, HbPayActivity.class);
                    intent.putExtra("pro_no", getIntent().getStringExtra("pro_no"));
                    intent.putExtra("hb_phone", getIntent().getStringExtra("hb_phone"));
                    intent.putExtra("mobile_phone", mobile_phone);
                    intent.putExtra("discount_flg", getIntent().getStringExtra("discount_flg"));
                    intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                    startActivity(intent);
                }
                break;
        }

    }
}
