package com.jsqix.yunpos.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.adapter.CouponAdapter;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.api.hb.HBGet;
import com.jsqix.yunpos.app.api.hb.HBPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.CouponExchangeBean;
import com.jsqix.yunpos.app.bean.hb.PlaceOrderErrorResult;
import com.jsqix.yunpos.app.bean.hb.PlaceOrderSuccessResult;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.utils.hb.DataUtils;
import com.jsqix.yunpos.app.view.PullToRefreshView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_coupon_exchange)
public class CouponExchangeActivity extends BaseAty implements CouponAdapter.CouponOrderListener {
    @ViewInject(R.id.tv_left)
    private TextView backLeft;
    @ViewInject(R.id.tv_title)
    private TextView mTitle;
    @ViewInject(R.id.lin_search)
    private LinearLayout searchLayout;
    @ViewInject(R.id.et_search)
    private EditText searchEdit;
    @ViewInject(R.id.tv_right)
    private TextView searchRight;

    @ViewInject(R.id.pullToRefreshView)
    private PullToRefreshView pullToRefreshView;
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.emptyView)
    private TextView emptyView;
    private List<CouponExchangeBean.ObjBean.GoodsInfoListBean> data = new ArrayList<>();
    private CouponAdapter adapter;
    final private static int p_size = 20;
    private int p_num = 1;
    private String keyword;
    private String pro_no, sms_phone;

    final static int GET_DATA_LIST = 0x0001, GET_PRODUCT_INFO = 0x0002, POST_PLACE_ORDER = 0x0003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        getExchangeList();
    }

    private void initView() {
        adapter = new CouponAdapter(this, data);
        adapter.setListener(this);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
    }

    private void initEvent() {
        pullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                p_num = 1;
                getExchangeList();
            }
        });

        pullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                p_num++;
                getExchangeList();
            }
        });

    }

    @Event(R.id.tv_right)
    private void searchClick(View v) {
        if (searchLayout.getVisibility() == View.VISIBLE) {
            if (StringUtils.isEmpty(CommUtils.textToString(searchEdit))) {
                Utils.makeToast(this, "请输入搜索关键字");
            } else {
                keyword = CommUtils.textToString(searchEdit);
                getExchangeList();
            }
        } else {
            mTitle.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            searchRight.setText("搜索");
            searchRight.setCompoundDrawables(null, null, null, null);
        }
    }

    @Event(R.id.tv_left)
    private void backClick(View v) {
        if (searchLayout.getVisibility() == View.VISIBLE) {
            keyword = "";
            mTitle.setVisibility(View.VISIBLE);
            searchEdit.setText("");
            searchLayout.setVisibility(View.GONE);
            searchRight.setText("");
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_search_white);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            searchRight.setCompoundDrawables(null, null, drawable, null);
            getExchangeList();
        } else {
            finish();
        }
    }


    /**
     * 获取列表
     */

    private void getExchangeList() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", aCache.getAsString(UAD.UID));
        if (!CommUtils.isEmpty(keyword)) {
            map.put("keyword", keyword);
        }
        map.put("p_num", p_num);
        map.put("p_size", p_size);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("getGoodsInfoList");
        post.setResultCode(GET_DATA_LIST);
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
     * 下单 获取支付地址
     *
     * @param prod_no        商品编号
     * @param buy_mbl_no     移动手机号
     * @param discount_flg   是否打折
     * @param discount_price 打折价格
     * @param prod_price     商品价格
     */
    private void placeOrder(String prod_no, String buy_mbl_no, String discount_flg, String discount_price, String prod_price) {
        Map<String, String> paras = new LinkedHashMap<>();
        paras.put("PROD_NO", prod_no);
        paras.put("BUY_MBL_NO", buy_mbl_no);
        paras.put("DISCOUNT_FLG", discount_flg);
        paras.put("DISCOUNT_PRICE", discount_price);
        paras.put("PROD_PRICE", prod_price);
        HBPost post = new HBPost(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        post.execute("https://www.cmpay.com/mkmweb/wap_order_pay.xhtml");
        post.setResultCode(POST_PLACE_ORDER);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        if (resultCode == GET_DATA_LIST) {
            listResult(result);
        } else if (resultCode == POST_PLACE_ORDER) {
            placeOrderResult(result);
        }
    }

    @Override
    public void getCallback(int resultCode, String result) {
        super.getCallback(resultCode, result);
        if (resultCode == GET_PRODUCT_INFO) {
            productInfoResult(result);
        }
    }

    private void listResult(String result) {
        try {
            loading.dismiss();
            CouponExchangeBean bean = new Gson().fromJson(result, CouponExchangeBean.class);
            if (bean.getCode().equals("000")) {
                if (bean.getObj().getGoodsInfoList() != null && bean.getObj().getGoodsInfoList().size() > 0) {
                    if (p_num == 1) {
                        data.clear();
                        pullToRefreshView.onHeaderRefreshComplete();
                    } else {
                        pullToRefreshView.onFooterRefreshComplete();
                    }
                    data.addAll(bean.getObj().getGoodsInfoList());
                    adapter.setBase_url(bean.getUrl());

                } else {
                    if (p_num == 1) {
                        data.clear();
                        Utils.makeToast(this, "暂无数据");
                    } else {
                        p_num--;
                        Utils.makeToast(this, "已加载全部");
                        pullToRefreshView.onFooterRefreshComplete();
                    }
                }
                adapter.notifyDataSetChanged();
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

    /**
     * 商品详情分析
     *
     * @param result
     */
    private void productInfoResult(String result) {
        if (StringUtils.isEmpty(result)) {
            Utils.makeToast(this, "产品不存在或已下架");
        } else {
            String PROD_NM_str = DataUtils.subStart(result, "id=\"PROD_NM\"");
            PROD_NM_str = DataUtils.subStart(PROD_NM_str, "value=\"");
            String PROD_NM = DataUtils.subEnd(PROD_NM_str, "\"");// 商品名称
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
            if (prodPrice.length() == 0 || "1".equals(discountFlag)
                    && discountPrice.length() == 0) {
                Utils.makeToast(this, "商品详情解析失败");
                return;
            }
            placeOrder(pro_no, sms_phone, discountFlag, discountPrice, prodPrice);
        }
    }

    /**
     * 下单结果
     *
     * @param result
     */
    private void placeOrderResult(String result) {
        loading.dismiss();
        if (StringUtils.isEmpty(result)) {
            Utils.makeToast(this, "下单失败");
        } else {
            try {
                PlaceOrderSuccessResult successResult = new Gson().fromJson(result, PlaceOrderSuccessResult.class);
                if (!StringUtils.isEmpty(successResult.getPAY_URL())) {
                    adapter.getDialog().dismiss();
                    Intent intent = new Intent(this, CouponPayActivity.class);
                    intent.putExtra(UAD.LOAD_URL, successResult.getPAY_URL());
                    startActivity(intent);
                } else {
                    PlaceOrderErrorResult errorResult = new Gson().fromJson(result, PlaceOrderErrorResult.class);
                    Utils.makeToast(this, errorResult.getGWA().getTMSG_INF());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.makeToast(this, "下单失败");
            }
        }
    }

    @Override
    public void submitOrder(String pro_no, String sms_phone) {
        this.pro_no = pro_no;
        this.sms_phone = sms_phone;
        getProductInfo(pro_no);
    }
}
