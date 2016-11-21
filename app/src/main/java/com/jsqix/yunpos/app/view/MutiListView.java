package com.jsqix.yunpos.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by dq on 2016/7/25.
 */
public class MutiListView extends ListView {


    public MutiListView(Context context) {
        super(context);
    }

    public MutiListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MutiListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
