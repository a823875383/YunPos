package com.jsqix.yunpos.app.utils;

import android.util.SparseArray;

/**
 * Created by dq on 2016/4/13.
 */
public class StatusOrder {
    SparseArray<String> status = new SparseArray<>();

    static StatusOrder order;

    public StatusOrder() {
        status.put(-1, "交易超时");
        status.put(0, "待支付");
        status.put(1, "部分支付");
        status.put(2, "交易完成");
        status.put(3, "交易失败");
        status.put(4, "待审核");
    }

    public static StatusOrder Instance() {
        if (order == null)
            order = new StatusOrder();
        return order;
    }

    public SparseArray<String> getStatus() {
        return status;
    }
}
