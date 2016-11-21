package com.jsqix.yunpos.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.adapter.ResultAdapter;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.InforBean;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_pay_result)
public class PayResultActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
//    @ViewInject(R.id.pullToRefreshView)
//    private PullToRefreshView pullToRefreshView;
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.logo)
    private ImageView logo;
    @ViewInject(R.id.status)
    private TextView status;

    private ResultAdapter adapter;
    private List<InforBean.ObjEntity.DetailsEntity> data = new ArrayList<>();

    private String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        getOrderInfor();
    }

    private void initView() {
        orderId = getIntent().getExtras().getString("orderId", "");

        titleBar.showLeft(false).setTitle("支付结果");

//        pullToRefreshView.setCanPullUp(false);

    }

    private void initEvent() {
//        pullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
//
//            @Override
//            public void onHeaderRefresh(PullToRefreshView view) {
//                getOrderInfor();
//            }
//        });

    }

    private void getOrderInfor() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("getOrderDetails");
    }

    @Event(value = {R.id.btnView, R.id.btnNew})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnView:
                startActivity(new Intent(this, OrderInforActivity.class).putExtra("oid", orderId));
                break;
            case R.id.btnNew:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        try {
            loading.dismiss();
//            pullToRefreshView.onHeaderRefreshComplete();
            InforBean bean = new Gson().fromJson(result, InforBean.class);
            if ("000".equals(bean.getCode())) {
                if (bean.getObj().getStatus() == 2) {
                    logo.setImageResource(R.mipmap.ic_sucess);
                } else {
                    logo.setImageResource(R.mipmap.ic_fail);
                }
                status.setText(bean.getObj().getRemark1());
                data = bean.getObj().getDetails();
                adapter = new ResultAdapter(this, data);
                listView.setAdapter(adapter);
            } else {
                Utils.makeToast(this, bean.getMsg());
            }
        } catch (Exception e) {

        }
    }
}
