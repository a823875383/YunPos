package com.jsqix.yunpos.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jsqix.utils.DateUtil;
import com.jsqix.utils.FrameApplication;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;

    @ViewInject(R.id.phone)
    private EditText phone;
    @ViewInject(R.id.startMoney)
    private EditText startMoney;
    @ViewInject(R.id.endMpney)
    private EditText endMpney;
    @ViewInject(R.id.startDate)
    private TextView startDate;
    @ViewInject(R.id.endDate)
    private TextView endDate;

    private TimePickerView timePickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        titleBar.showLeft(false).setTitle("交易查询");

        startDate.setText(DateUtil.getCurrentDateString());
        endDate.setText(DateUtil.getCurrentDateString());
        timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        timePickerView.setCancelable(true);
    }

    private void initEvent() {
        startMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        startMoney.setText(s);
                        startMoney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    startMoney.setText(s);
                    startMoney.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        startMoney.setText(s.subSequence(1, 2));
                        startMoney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        endMpney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        endMpney.setText(s);
                        endMpney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    endMpney.setText(s);
                    endMpney.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        endMpney.setText(s.subSequence(1, 2));
                        endMpney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Event(value = {R.id.startDateLayout, R.id.endDateLayout, R.id.buttonSubmit})
    private void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (v.getId()) {
            case R.id.startDateLayout:
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                timePickerView.setTime(DateUtil.getStringToDate(startDate.getText().toString(), DateUtil.DATE_FORMAT_DEFAUlt));
                timePickerView.show();
                timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        if (DateUtil.getStringToLongDate(endDate.getText().toString().trim(), DateUtil.DATE_FORMAT_DEFAUlt) < date.getTime()) {
                            endDate.setText(DateUtil.getCurrentDateAddByDay(date, 0));
                        }
                        startDate.setText(DateUtil.dateToString(date, DateUtil.DATE_FORMAT_DEFAUlt));
                    }
                });
                break;
            case R.id.endDateLayout:
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                timePickerView.setTime(DateUtil.getStringToDate(endDate.getText().toString(), DateUtil.DATE_FORMAT_DEFAUlt));
                timePickerView.show();
                timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        if (DateUtil.getStringToLongDate(startDate.getText().toString().trim(), DateUtil.DATE_FORMAT_DEFAUlt) > date.getTime()) {
                            startDate.setText(DateUtil.getCurrentDateAddByDay(date, 0));
                        }
                        endDate.setText(DateUtil.dateToString(date, DateUtil.DATE_FORMAT_DEFAUlt));
                    }
                });
                break;
            case R.id.buttonSubmit:
                Intent intent = new Intent(this, OrderListActivity.class);
                String phone = this.phone.getText().toString().trim();
                String k_amount = startMoney.getText().toString().trim();
                String j_amount = endMpney.getText().toString().trim();
                if (!CommUtils.isEmpty(phone) && CommUtils.notPhone(phone)) {
                    Utils.makeToast(this, "请输入正确的手机号");
                    return;
                }
                if (CommUtils.toFloat(k_amount) > CommUtils.toFloat(j_amount)) {
                    String temp = k_amount;
                    k_amount = CommUtils.isEmpty(j_amount) ? "0" : j_amount;
                    j_amount = temp;
                    startMoney.setText(k_amount);
                    endMpney.setText(j_amount);
                    startMoney.setSelection(k_amount.length());
                    endMpney.setSelection(j_amount.length());
                } else if (CommUtils.toFloat(k_amount) == CommUtils.toFloat(j_amount)) {
                    if (!CommUtils.isEmpty(k_amount)) {
                        k_amount = "0";
                        startMoney.setText(k_amount);
                        startMoney.setSelection(k_amount.length());
                    }
                } else {
                    if (CommUtils.isEmpty(k_amount)) {
                        k_amount = "0";
                        startMoney.setText(k_amount);
                        startMoney.setSelection(k_amount.length());
                    }
                }
                String k_time = startDate.getText().toString().trim();
                String j_time = endDate.getText().toString().trim();
                intent.putExtra("phone", phone);
                intent.putExtra("k_amount", k_amount);
                intent.putExtra("j_amount", j_amount);
                intent.putExtra("k_time", k_time);
                intent.putExtra("j_time", j_time);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (timePickerView.isShowing()) {
                timePickerView.dismiss();
                return true;
            }
            return FrameApplication.getInstance().closeAppByBack(KeyEvent.KEYCODE_BACK, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
