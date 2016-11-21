package com.jsqix.yunpos.app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.adapter.InforAdapter;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.InforBean;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.StatusOrder;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_order_infor)
public class OrderInforActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.order_num)
    private TextView order_num;
    @ViewInject(R.id.order_money)
    private TextView order_money;
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.order_time)
    private TextView order_time;
    @ViewInject(R.id.order_remark)
    private TextView order_remark;
    @ViewInject(R.id.order_status)
    private TextView order_status;
    @ViewInject(R.id.layoutCause)
    private LinearLayout layoutCause;
    @ViewInject(R.id.order_cause)
    private TextView order_cause;

    /**
     * 传递
     */
    private String oid;
    private InforAdapter adapter;
    private List<InforBean.ObjEntity.DetailsEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getData();
    }


    private void initView() {
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle("交易详情");

        oid = getIntent().getExtras().getString("oid");
    }


    public void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", oid);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("getOrderDetails");
    }

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            loading.dismiss();
            InforBean bean = new Gson().fromJson(result, InforBean.class);
            if (bean.getCode().equals("000")) {
                order_num.setText(bean.getObj().getOrderId());
                order_money.setText(CommUtils.toFormat(bean.getObj().getAmount()) + " 元");
                order_time.setText(bean.getObj().getAddtime());
                order_remark.setText(bean.getObj().getRemark());
                order_status.setText(StatusOrder.Instance().getStatus().get(bean.getObj().getStatus()));
                if (bean.getObj().getStatus() == 3) {
                    layoutCause.setVisibility(View.VISIBLE);
                    order_cause.setText(bean.getObj().getRemark1());
                }
                data = bean.getObj().getDetails();
                adapter = new InforAdapter(this, data);
                listView.setAdapter(adapter);
            } else {
                Utils.makeToast(this, bean.getMsg());
            }
        } catch (Exception e) {
            if (CommUtils.isNull(result)) {
                Utils.makeToast(this, "链接服务器异常");
            }
        }

    }
}
