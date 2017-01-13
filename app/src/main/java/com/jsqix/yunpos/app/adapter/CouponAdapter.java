package com.jsqix.yunpos.app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsqix.utils.BaseActivity;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.jsqix.yunpos.app.HbLoginActivity;
import com.jsqix.yunpos.app.R;
import com.jsqix.yunpos.app.bean.CouponExchangeBean;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.view.CustomeDialog;

import org.xutils.x;

import java.util.List;

/**
 * Created by dongqing on 2016/11/21.
 */

public class CouponAdapter extends HolderBaseAdapter<CouponExchangeBean.ObjBean.GoodsInfoListBean> {
    private BaseActivity mContext;
    private String base_url = "";
    private CustomeDialog dialog;
    private Button left, right;
    private TextView title;
    private EditText phone;

    private String pro_no;

    private CouponOrderListener listener;

    public CouponAdapter(BaseActivity mContext, List<CouponExchangeBean.ObjBean.GoodsInfoListBean> list) {
        super();
        this.mContext = mContext;
        this.data = list;
        initDialog();
    }

    public void setListener(CouponOrderListener listener) {
        this.listener = listener;
    }

    private void initDialog() {
        dialog = new CustomeDialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_sms, null);
        title = (TextView) view.findViewById(R.id.title);
        phone = (EditText) view.findViewById(R.id.phone);
        left = (Button) view.findViewById(R.id.btnSubmit);
        right = (Button) view.findViewById(R.id.btnCancel);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNull(CommUtils.textToString(phone))) {
                    Utils.makeToast(mContext, "请输入移动手机号");

                } else if (StringUtils.notPhone(CommUtils.textToString(phone))) {
                    Utils.makeToast(mContext, "手机号格式不正确");

                } else if (!CommUtils.isChinaMobile(CommUtils.textToString(phone))) {
                    Utils.makeToast(mContext, "请输入中国移动手机号");

                } else {
                    if (listener != null) {
                        listener.submitOrder(pro_no, CommUtils.textToString(phone));
                    }
                }
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.setParas(0, 0);
    }

    @Override
    public ViewHolder getViewHolder(View convertView, ViewGroup parent, int position) {
        ViewHolder mViewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_coupon);
        ImageView image = mViewHolder.findViewById(R.id.image);
        TextView name = mViewHolder.findViewById(R.id.coupon_name);
        final ImageView right = mViewHolder.findViewById(R.id.arrow);
        final LinearLayout child = mViewHolder.findViewById(R.id.list_item);
        final CouponExchangeBean.ObjBean.GoodsInfoListBean bean = data.get(position);
        x.image().bind(image, base_url + bean.getGoods_pic());
        name.setText(bean.getGoods_name());
        child.removeAllViewsInLayout();
        for (final CouponExchangeBean.ObjBean.GoodsInfoListBean.ProductListBean productListBean : bean.getProductList()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon_infor, null);
            TextView productName = (TextView) view.findViewById(R.id.coupons_item);
            productName.setText(productListBean.getProduct_name());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pro_no = productListBean.getProduct_code();
//                    title.setText(productListBean.getProduct_name());
//                    phone.setText("");
//                    dialog.show();
                    Intent intent = new Intent(mContext, HbLoginActivity.class);
                    intent.putExtra("pro_no", pro_no);
                    mContext.startActivity(intent);
                }
            });
            child.addView(view);
        }
        mViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child.getVisibility() == View.GONE) {
                    child.setVisibility(View.VISIBLE);
                    right.setImageResource(R.mipmap.arrow_down);
                } else {
                    child.setVisibility(View.GONE);
                    right.setImageResource(R.mipmap.arrow_right);
                }
            }
        });
        return mViewHolder;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public interface CouponOrderListener {
        void submitOrder(String pro_no, String sms_phone);
    }

    public CustomeDialog getDialog() {
        return dialog;
    }
}
