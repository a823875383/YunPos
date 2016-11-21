package com.jsqix.yunpos.app.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsqix.utils.BaseActivity;
import com.jsqix.utils.DensityUtil;
import com.jsqix.yunpos.app.R;
import com.jsqix.yunpos.app.utils.CommUtils;


public class TitleBar extends RelativeLayout {

    private Context context;
    private View view;
    private RelativeLayout layout;

    private LinearLayout leftBtn, rightBtn;
    private TextView leftTextView, title, rightTextView;//rightTag;


    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }


    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.title_bar, this, true);
        layout = (RelativeLayout) view.findViewById(R.id.layout);
        leftBtn = (LinearLayout) view.findViewById(R.id.titlebar_left_btn);
        rightBtn = (LinearLayout) view.findViewById(R.id.titlebar_right_btn);
        title = (TextView) view.findViewById(R.id.titlebar_title_text);
        rightTextView = (TextView) view.findViewById(R.id.titlebar_right_text);
        leftTextView = (TextView) view.findViewById(R.id.titlebar_left_text);
//		rightTag = (TextView) view.findViewById(R.id.tv_right_tag);
        setDefaultListner();
    }

    private void setDefaultListner() {
        leftBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (context instanceof BaseActivity) {
                    BaseActivity activity = (BaseActivity) context;
                    activity.finish();
                }
            }
        });

    }

    public TitleBar setViewBackColor(int resid) {
        layout.setBackgroundColor(resid);
        return this;
    }

    public TitleBar setViewBackRes(int resid) {
        layout.setBackgroundResource(resid);
        return this;
    }

    public TitleBar setViewBackRes(Drawable drawable) {
        layout.setBackgroundDrawable(drawable);
        return this;
    }

    public TitleBar setLayouHeight(float dip) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                DensityUtil.dip2px(context, dip));
        layout.setLayoutParams(lp);
        return this;

    }


    public TitleBar setTitle(String titleText) {
        if (CommUtils.isEmpty(titleText)) {
            title.setText("");
        } else {
            title.setText(titleText);
        }
        return this;
    }

    public TitleBar setTitleColor(int resid) {
        title.setTextColor(getResources().getColor(resid));
        return this;
    }

    public TitleBar setTitleSize(float size) {
        title.setTextSize(size);
        return this;
    }

    public TitleBar showLeft(boolean isVisiable) {
        if (isVisiable) {
            leftBtn.setVisibility(View.VISIBLE);
        } else {
            leftBtn.setVisibility(View.GONE);
        }
        return this;
    }

    public TitleBar showRight(boolean isVisiable) {
        if (isVisiable) {
            rightBtn.setVisibility(View.VISIBLE);
        } else {
            rightBtn.setVisibility(View.GONE);
        }
        return this;
    }

    public TitleBar setLeftContent(String content) {
        if (content != null) {
            leftTextView.setText(content);
        }
        return this;
    }

    public TitleBar setLeftTextColor(int resId) {
        if (resId != 0) {
            leftTextView.setTextColor(resId);
        }
        return this;
    }

    public TitleBar setLeftBackground(int resId) {
        leftTextView.setBackgroundResource(resId);
        return this;
    }

    public TitleBar setRightContent(String content) {
        if (content != null) {
            rightTextView.setText(content);
        }
        return this;
    }

    public TitleBar setRightBackground(int id) {
        if (id != 0) {
            rightTextView.setBackgroundResource(id);
        }
        return this;
    }

    public TitleBar setRightTextColor(int resId) {
        if (resId != 0) {
            rightTextView.setTextColor(resId);
        }
        return this;
    }

//	public TitleBar showRightTag(boolean isVisiable){
//		if(isVisiable){
//			rightTag.setVisibility(View.VISIBLE);
//		}else{
//			rightTag.setVisibility(View.GONE);
//		}
//
//		return this;
//	}
//
//	public TitleBar setRightTagContent(String content){
//		if(content!=null){
//			rightTag.setText(content);
//		}
//		return this;
//	}


    /**
     * 设置左边的事件
     */
    public TitleBar setLeftListener(OnClickListener listener) {
        if (leftBtn.getVisibility() == View.VISIBLE) {
            leftBtn.setOnClickListener(listener);
        }
        return this;
    }

    public TitleBar setRightListener(OnClickListener listener) {
        if (rightBtn.getVisibility() == View.VISIBLE) {
            rightBtn.setOnClickListener(listener);
        }
        return this;
    }
}
