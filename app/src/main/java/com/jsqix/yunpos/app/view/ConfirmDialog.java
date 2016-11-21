package com.jsqix.yunpos.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsqix.utils.StringUtils;
import com.jsqix.yunpos.app.R;

import static com.jsqix.yunpos.app.R.id.msg;

/**
 * Created by dq on 2016/4/26.
 */
public class ConfirmDialog {
    private Context mContext;
    private CustomeDialog errorDailog;
    private LinearLayout titleLayout;
    private TextView titleView;
    private TextView msgView;
    private Button submitView;
    private Button cancelView;
    private View.OnClickListener positiveClick;
    private View.OnClickListener negativeClick;

    public ConfirmDialog(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        errorDailog = new CustomeDialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_dialog, null);
        errorDailog.setView(view);
        errorDailog.setParas(0.8f, 0f);
        errorDailog.setCanceledOnTouchOutside(false);
        errorDailog.setCancelable(false);

        titleLayout = (LinearLayout) view.findViewById(R.id.title_lay);
        titleView = (TextView) view.findViewById(R.id.title);
        msgView = (TextView) view.findViewById(msg);
        submitView = (Button) view.findViewById(R.id.btnSubmit);
        cancelView = (Button) view.findViewById(R.id.btnCancel);
        setTitle("");
        setButton("知道了");
        setButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDailog.dismiss();
            }
        });
    }

    public void setTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            titleLayout.setVisibility(View.GONE);
        } else {
            titleLayout.setVisibility(View.VISIBLE);
            titleView.setText(title);
        }
    }

    public void setMessage(String msg) {
        msgView.setText(msg);
    }

    public String getMessage() {
        return msgView.getText().toString();
    }

    public void setPositiveClick(View.OnClickListener listener) {
        this.positiveClick = listener;
    }

    public void setNegativeClick(View.OnClickListener listener) {
        this.negativeClick = listener;
    }

    public void setPositiveButton(String text) {
        submitView.setText(text);
    }

    public void setNegativeButton(String text) {
        cancelView.setText(text);
    }

    public void setButtonClick(View.OnClickListener listener) {
        setPositiveClick(listener);
    }

    public void setButton(String text) {
        setPositiveButton(text);
    }

    public void show() {
        if (positiveClick != null) {
            submitView.setOnClickListener(positiveClick);
        }
        if (negativeClick != null) {
            cancelView.setOnClickListener(negativeClick);
            cancelView.setVisibility(View.VISIBLE);
        } else {
            cancelView.setVisibility(View.GONE);
        }
        errorDailog.show();
    }

    public void dismiss() {
        errorDailog.dismiss();
    }
}
