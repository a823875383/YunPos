package com.jsqix.yunpos.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.jsqix.yunpos.app.R;

/**
 * Created by dq on 2016/4/12.
 */
public class CustomeDialog extends Dialog {
    private Context mContext;
    private LayoutParams lp;

    public CustomeDialog(Context context) {
        super(context, R.style.BaseDialog);
        this.mContext = context;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public void setView(View view) {
        setContentView(view);
        // 设置window属性
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.alpha = 1.0f; // 设置本身透明度
        lp.dimAmount = 0.65f; // 设置黑暗度
        getWindow().setAttributes(lp);
    }

    public void setParas(float width, float height) {
        WindowManager m = ((Activity) mContext).getWindowManager();
        // 设置window属性
        lp = getWindow().getAttributes();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        if (width > 0 && width <= 1) {
            lp.width = (int) (width * d.getWidth());
        } else {
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        if (height > 0 && height <= 1)
            lp.height = (int) (height * d.getHeight());
        else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        getWindow().setAttributes(lp);
    }

}
