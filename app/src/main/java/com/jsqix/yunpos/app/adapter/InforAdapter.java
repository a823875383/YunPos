package com.jsqix.yunpos.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsqix.utils.BaseActivity;
import com.jsqix.yunpos.app.R;
import com.jsqix.yunpos.app.bean.InforBean;
import com.jsqix.yunpos.app.utils.CommUtils;

import java.util.List;

/**
 * Created by dq on 2016/7/25.
 */
public class InforAdapter extends HolderBaseAdapter<InforBean.ObjEntity.DetailsEntity> {
    private BaseActivity mContext;

    public InforAdapter(BaseActivity mContext, List<InforBean.ObjEntity.DetailsEntity> BeansList) {
        super();
        this.mContext = mContext;
        this.data = BeansList;
    }

    @Override
    public ViewHolder getViewHolder(View convertView, ViewGroup parent, int position) {
        ViewHolder mViewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_infor);
        TextView pay_money = mViewHolder.findViewById(R.id.pay_money);
        TextView pay_type = mViewHolder.findViewById(R.id.pay_type);
        TextView pay_phone = mViewHolder.findViewById(R.id.pay_phone);
        TextView pay_status = mViewHolder.findViewById(R.id.pay_status);
        LinearLayout phoneLay = mViewHolder.findViewById(R.id.phoneLay);
        final InforBean.ObjEntity.DetailsEntity bean = data.get(position);
        pay_money.setText(CommUtils.toFormat(bean.getAmount())+" 元");
        pay_status.setText(bean.getRemark());
        if (bean.getOrder_type() == 1) {
            pay_type.setText("电子券支付");
            phoneLay.setVisibility(View.VISIBLE);
            pay_phone.setText(bean.getPhone());
        } else {
            pay_type.setText("微信支付");
            phoneLay.setVisibility(View.GONE);
        }
        return mViewHolder;
    }
}
