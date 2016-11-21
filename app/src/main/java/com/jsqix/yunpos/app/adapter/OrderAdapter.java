package com.jsqix.yunpos.app.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsqix.utils.BaseActivity;
import com.jsqix.yunpos.app.OrderInforActivity;
import com.jsqix.yunpos.app.R;
import com.jsqix.yunpos.app.bean.ListBean;
import com.jsqix.yunpos.app.utils.CommUtils;
import com.jsqix.yunpos.app.utils.StatusOrder;

import java.util.List;

/**
 * Created by dq on 2016/4/12.
 */
public class OrderAdapter extends HolderBaseAdapter<ListBean.ObjEntity.OrderListEntity> {
    private BaseActivity mContext;

    public OrderAdapter(BaseActivity mContext, List<ListBean.ObjEntity.OrderListEntity> BeansList) {
        super();
        this.mContext = mContext;
        this.data = BeansList;
    }

    @Override
    public ViewHolder getViewHolder(View convertView, ViewGroup parent, int position) {
        ViewHolder mViewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_order);
        TextView phone = mViewHolder.findViewById(R.id.phone);
        TextView money = mViewHolder.findViewById(R.id.money);
        TextView status = mViewHolder.findViewById(R.id.status);
        TextView event = mViewHolder.findViewById(R.id.event);
        final ListBean.ObjEntity.OrderListEntity bean = data.get(position);
        phone.setText(bean.getOrderId());
        money.setText(CommUtils.toFormat(bean.getAmount()));
        status.setText(StatusOrder.Instance().getStatus().get(bean.getStatus()));
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderInforActivity.class);
                intent.putExtra("oid", bean.getOrderId());
                mContext.startActivity(intent);
            }
        });
        return mViewHolder;
    }
}
