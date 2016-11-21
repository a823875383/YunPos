package com.jsqix.yunpos.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsqix.utils.BaseActivity;
import com.jsqix.yunpos.app.bean.CityBean;

import java.util.List;

/**
 * Created by dq on 2016/7/4.
 */
public class CityAdapter extends HolderBaseAdapter<CityBean.ObjEntity> {
    private BaseActivity mContext;

    public CityAdapter(BaseActivity mContext, List<CityBean.ObjEntity> BeansList) {
        super();
        this.mContext = mContext;
        this.data = BeansList;
    }

    @Override
    public ViewHolder getViewHolder(View convertView, ViewGroup parent, int position) {
        ViewHolder mViewHolder = ViewHolder.get(mContext, convertView, parent, android.R.layout.simple_list_item_1);
        TextView city = mViewHolder.findViewById(android.R.id.text1);
        city.setText(data.get(position).getName());
        return mViewHolder;
    }
}
