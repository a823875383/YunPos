package com.jsqix.yunpos.app;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.adapter.OrderAdapter;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.ListBean;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.view.PullToRefreshView;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_order_list)
public class OrderListActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.pullToRefreshView)
    private PullToRefreshView pullToRefreshView;
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.emptyView)
    private TextView emptyView;
    @ViewInject(R.id.deal_amount)
    private TextView dealAmount;
    /**
     *
     */
    private String phone;
    private String k_amount;
    private String j_amount;
    private String k_time;
    private String j_time;
    final private static int p_size = 20;
    private int p_num = 1;

    private List<ListBean.ObjEntity.OrderListEntity> data = new ArrayList<>();
    private OrderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        getData();
    }


    private void initView() {
        phone = getIntent().getExtras().getString("phone");
        k_amount = getIntent().getExtras().getString("k_amount");
        j_amount = getIntent().getExtras().getString("j_amount");
        k_time = getIntent().getExtras().getString("k_time");
        j_time = getIntent().getExtras().getString("j_time");

        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle("交易列表");
        adapter = new OrderAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);

    }

    private void initEvent() {
        pullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                p_num = 1;
                getData();
            }
        });

        pullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                p_num++;
                getData();
            }
        });

    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        map.put("phone", phone);
        map.put("k_amount", k_amount);
        map.put("j_amount", j_amount);
        map.put("k_time", k_time);
        map.put("j_time", j_time);
        map.put("p_num", p_num);
        map.put("p_size", p_size);

        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("getOrderList");
    }


    @Override
    public void postCallback(int resultCode, String result) {
        try {
            loading.dismiss();
            ListBean bean = new Gson().fromJson(result, ListBean.class);
            if (bean.getCode().equals("000")) {
                dealAmount.setText(bean.getObj().getTop_str());
                if (bean.getObj().getOrderList() != null && bean.getObj().getOrderList().size() > 0) {
                    if (p_num == 1) {
                        data.clear();
                        pullToRefreshView.onHeaderRefreshComplete();
                    } else {
                        pullToRefreshView.onFooterRefreshComplete();
                    }
                    data.addAll(bean.getObj().getOrderList());
                    adapter.notifyDataSetChanged();
                } else {
                    if (p_num == 1) {
                        Utils.makeToast(this, "暂无数据");
                    } else {
                        p_num--;
                        Utils.makeToast(this, "已加载全部");
                        pullToRefreshView.onFooterRefreshComplete();
                    }
                }
            } else {
                Utils.makeToast(this, bean.getMsg());
            }

        } catch (Exception e) {
            if (CommUtils.isNull(result)) {
                Utils.makeToast(this, "链接服务器异常");
            }
            if (p_num == 1) {
                pullToRefreshView.onHeaderRefreshComplete();
            } else {
                p_num--;
                pullToRefreshView.onFooterRefreshComplete();
            }
        }
    }
}
