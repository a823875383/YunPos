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
public class ResultAdapter extends HolderBaseAdapter<InforBean.ObjEntity.DetailsEntity> {
    private BaseActivity mContext;

    public ResultAdapter(BaseActivity mContext, List<InforBean.ObjEntity.DetailsEntity> BeansList) {
        super();
        this.mContext = mContext;
        this.data = BeansList;
    }

    @Override
    public ViewHolder getViewHolder(View convertView, ViewGroup parent, int position) {
        ViewHolder mViewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_result);
        TextView amount = mViewHolder.findViewById(R.id.amount);
        TextView phone = mViewHolder.findViewById(R.id.phone);
        LinearLayout phoneLay = mViewHolder.findViewById(R.id.phoneLay);
        TextView type = mViewHolder.findViewById(R.id.type);
        TextView status = mViewHolder.findViewById(R.id.status);
        final InforBean.ObjEntity.DetailsEntity bean = data.get(position);
        amount.setText(CommUtils.toFormat(bean.getAmount()) + " 元");
        if (bean.getOrder_type() == 2) {
            phoneLay.setVisibility(View.GONE);
        } else {
            phoneLay.setVisibility(View.VISIBLE);
        }
        phone.setText(bean.getPhone());
        if (bean.getOrder_type() == 1) {
            type.setText("电子券");
        } else if (bean.getOrder_type() == 2) {
            type.setText("微信");
        }
        status.setText(bean.getRemark());

        return mViewHolder;
    }
}
